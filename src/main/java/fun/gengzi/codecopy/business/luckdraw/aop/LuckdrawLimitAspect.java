package fun.gengzi.codecopy.business.luckdraw.aop;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 限流 AOP
 * 创建者	张志朋
 * 创建时间	2015年6月3日
 */
@Aspect
@Configuration
public class LuckdrawLimitAspect {
    private static Logger logger = LoggerFactory.getLogger(LuckdrawLimitAspect.class);
    //根据IP分不同的令牌桶, 每天自动清理缓存
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            /*设置缓存容器的最大容量大小为10万*/
            .maximumSize(10000)
            /*设置过期时间为1天*/
            .expireAfterWrite(1, TimeUnit.DAYS)
            // RateLimiter 令牌桶算法的工具类,时间窗口能且仅能为 1s
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) {
                    logger.info("key init : {}" + key);
                    // 新的IP初始化 每秒钟发放 0.5个令牌，限定两秒钟访问一次
                    return RateLimiter.create(0.5);
                }
            });

    //Service层切点  限流
    @Pointcut("@annotation(fun.gengzi.codecopy.business.luckdraw.aop.LuckdrawServiceLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取方法修饰的 @ServiceLimit 注解
        LuckdrawServiceLimit limitAnnotation = method.getAnnotation(LuckdrawServiceLimit.class);
        // 获取注解中的limitType参数
        LuckdrawServiceLimit.LimitType limitType = limitAnnotation.limitType();
        // 获取注解中的key参数
        String key = limitAnnotation.key();
        Object obj;
        try {
            if (limitType.equals(LuckdrawServiceLimit.LimitType.IP)) {
                // 获取真实ip地址
                key = IPUtils.getIpAddr();
            }
            // 返回一个键在这个高速缓存中，首先装载如果需要该值相关联的值。
            RateLimiter rateLimiter = caches.get(key);
            // 非阻塞的获取令牌 可以实时返回结果
            Boolean flag = rateLimiter.tryAcquire();
            if (flag) {
                obj = joinPoint.proceed();
            } else {
                throw new RrException("请稍等，正在为您抽奖中哦！");
            }
        } catch (Throwable e) {
            throw new RrException("请稍等，正在为您抽奖中哦！");
        }
        return obj;
    }
}
