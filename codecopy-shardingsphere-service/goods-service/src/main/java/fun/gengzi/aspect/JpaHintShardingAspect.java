package fun.gengzi.aspect;

import fun.gengzi.enums.ShardingDataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.Method;

/**
 * <h1>声明Jpahint 模式下分片策略</h1>
 * <p>
 * <p>
 * 特别注意： Spring的AOP只能支持到方法级别的切入。换句话说，切入点只能是某个方法。
 * <p>
 * 主要实现，不修改原有业务代码的情况下，支持原有库数据，和新分库分表数据更新
 * 注意：执行 saveFlush 会报错，还没有排查出来是问题
 * <p>
 * <p>
 * 代码实现，执行两个sql执行，第二个不使用新线程，不会执行。 暂不清楚原因，猜测跟 sharding jdbc实现有关系
 *
 * @author gengzi
 * @date 2021年12月16日16:41:32
 */
@Aspect
@Configuration
@Slf4j
public class JpaHintShardingAspect {

    // 用于解决内部类调用  @Async 失效的方法
    @Autowired
    @Lazy
    private JpaHintShardingAspect jpaHintShardingAspect;

    //切入点
    @Pointcut("execution(* fun.gengzi.dao.GoodsJPA.*(..))")
    public void jpaHintShardingAspectNew() {
    }


    @Around("jpaHintShardingAspectNew()")
    public Object aroundNew(ProceedingJoinPoint joinPoint) {
        log.info("hintsharding start");
        Object obj = oldDataSource(joinPoint);
        jpaHintShardingAspect.newDataSource(joinPoint);
        return obj;
    }

    private Object oldDataSource(ProceedingJoinPoint joinPoint) {
        log.info("旧库-执行sql开始,执行方法[{}]", joinPoint.getSignature().getName());
        // 先清除当前存放的分片值
        HintManager.clear();
        HintManager hintManager = HintManager.getInstance();
        // 添加分片值
        hintManager.setDatabaseShardingValue(ShardingDataSourceType.TYPE_OLD.getType());
        log.info("hint分片键值[{}]", HintManager.getDatabaseShardingValues());
        Object obj;
        try {
            obj = joinPoint.proceed();
            HintManager.clear();
        } catch (Throwable e) {
            log.error("error: {} ", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            // 再次清除当前存放的分片值，避免内存泄漏
            HintManager.clear();
        }
        log.info("旧库-执行sql结束,执行结果[{}]", obj);
        return obj;
    }

    /**
     * 开启新线程，启动对新库的处理
     *
     * @param joinPoint 切入点
     * @return
     */
    @Async("asyncOneThreadPool")
    public Object newDataSource(ProceedingJoinPoint joinPoint) {
        log.info("新库-执行sql开始,执行方法[{}]", joinPoint.getSignature().getName());
        HintManager.clear();
        // 新线程，新建HintManager
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue(ShardingDataSourceType.TYPE_NEW.getType());
        log.info("hint分片键值[{}]", HintManager.getDatabaseShardingValues());
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            HintManager.clear();
        } catch (Throwable throwable) {
            // 对于新库报错，不予阻断报错,只记录
            log.error("error: {} ", throwable.getMessage());
        } finally {
            // 再次清除当前存放的分片值
            HintManager.clear();
        }
        log.info("新库-执行sql结束,执行结果[{}]", obj);
        return obj;
    }

}
