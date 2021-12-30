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
import org.springframework.core.annotation.Order;

import javax.persistence.EntityManager;

/**
 * <h1>jpa自定义分片切面</h1>
 * <p>
 * <p>
 * 通过不同参数，指定执行旧库，还是新库
 *
 * @author gengzi
 * @date 2021年12月16日16:41:32
 */
@Aspect
@Configuration
@Slf4j
@Order(5)
public class JpAcustomShardingAspect {

    @Autowired
    private EntityManager entityManager;


    //切入点
    @Pointcut("execution(* fun.gengzi.dao.GoodsJPA.*(..))")
    public void jpAcustomShardingAspect() {
    }


    @Around("jpAcustomShardingAspect()")
    public Object aroundNew(ProceedingJoinPoint joinPoint) {
        log.info("acustomsharding start");

        if (ShardingDataSourceType.TYPE_OLD.getType().equals(JpaThreadLocalManager.getDatabaseShardingValues().stream().findFirst().get())) {
            return oldDataSource(joinPoint);
        }

        if (ShardingDataSourceType.TYPE_NEW.getType().equals(JpaThreadLocalManager.getDatabaseShardingValues().stream().findFirst().get())) {
            return newDataSource(joinPoint);
        }

        throw new UnsupportedOperationException();
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
     * 对新库的处理
     *
     * @param joinPoint 切入点
     * @return
     */
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
