package fun.gengzi.codecopy.business.luckdraw.aop;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawEnum;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>锁-aop</h1>
 *
 * @author gengzi
 * @date 2020年9月17日10:59:15
 * <p>
 * <p>
 * * 尝试获取锁，最多等待3秒，上锁以后20秒自动解锁（实际项目中推荐这种，以防出现死锁）、这里根据预估秒杀人数，设定自动释放锁时间.
 * * 看过博客的朋友可能会知道(Lcok锁与事物冲突的问题)：https://blog.52itstyle.com/archives/2952/
 * * 分布式锁的使用和Lock锁的实现方式是一样的，但是测试了多次分布式锁就是没有问题，当时就留了个坑
 * * 闲来咨询了《静儿1986》，推荐下博客：https://www.cnblogs.com/xiexj/p/9119017.html
 * * 先说明下之前的配置情况：Mysql在本地，而Redis是在外网。
 * * 回复是这样的：
 * * 这是因为分布式锁的开销是很大的。要和锁的服务器进行通信，它虽然是先发起了锁释放命令，涉及网络IO，延时肯定会远远大于方法结束后的事务提交。
 * * ==========================================================================================
 * * 分布式锁内部都是Runtime.exe命令调用外部，肯定是异步的。分布式锁的释放只是发了一个锁释放命令就算完活了。真正其作用的是下次获取锁的时候，要确保上次是释放了的。
 * * 就是说获取锁的时候耗时比较长，那时候事务肯定提交了
 * * ==========================================================================================
 * * 周末测试了一下，把redis配置在了本地，果然出现了超卖的情况；或者还是使用外网并发数增加在10000+也是会有问题的，之前自己没有细测，我的锅。
 * * 所以这钟实现也是错误的，事物和锁会有冲突，建议AOP实现。
 * <p>
 * <p>
 * 基于上面的情况，起目的都是 锁的上移，要包裹整个事务  ，可以在controller 加锁，或者使用aop的方式
 */
@Aspect
@Configuration
public class LuckdrawLockAspect {
    private static Logger logger = LoggerFactory.getLogger(LuckdrawLockAspect.class);
    // 重入锁
    private Lock reentrantLock = new ReentrantLock();
    // redis 分布式锁
    @Autowired
    private RedissonClient redissonClient;

    //Service层切点
    @Pointcut("@annotation(fun.gengzi.codecopy.business.luckdraw.aop.LuckdrawLock)")
    public void lockAspect() {

    }

    @Around("lockAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        return execRedisLock(joinPoint);
    }


    /**
     * 执行jvm 加锁，解锁操作
     *
     * @param joinPoint
     * @return
     */
    private Object execRedisLock(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        // 默认方法名称作为 redis 的key
        String lockkey = method.getName();
        // 判断参数列表是否有 LuckdrawLockKey 注解的参数字段，把该字段作为 lockkey使用
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            Annotation[] annotations = param.getAnnotations();
            boolean contains = Arrays.asList(annotations).contains(LuckdrawLockKey.class);
            if (contains) {
                Class<?> type = param.getType();
                if (String.class.equals(type)) {
                    Object obj = args[i];
                    lockkey = obj.toString();
                    logger.info("解析得到的lockkey：{}", lockkey);
                }
            }
        }
        logger.info("最终的lockkey：{}", lockkey);

        Object obj = null;
        boolean lockFlag = false;
        RLock lock = redissonClient.getLock(lockkey);
        // 最长等待锁时间 100 秒，上锁后自动解锁时间 10 秒
        try {
            logger.info("redis加锁");
            lockFlag = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // 获取锁异常
            logger.info("获取redis锁异常：{}", e.getMessage());
            e.printStackTrace();
        }
        if (lockFlag) {
            try {
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                // 释放锁
                logger.info("redis解锁");
                lock.unlock();
            }
        }
        return obj;
    }

    /**
     * 执行jvm 加锁，解锁操作
     *
     * @param joinPoint
     * @return
     */
    private Object execJvmLock(ProceedingJoinPoint joinPoint) {
        // 默认jvm 锁
        Object obj = null;
        // 获取锁
        // 不要将获取锁的过程写在try块中，因为如果在获取锁（自定义锁的实现）时发生了异常，
        //异常抛出的同时，也会导致锁无故释放。
        reentrantLock.lock();
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            // 释放锁
            reentrantLock.unlock();
        }
        return obj;
    }
}
