package fun.gengzi.aspect;

import fun.gengzi.enums.ShardingDataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 *  <h1>声明Jpahint 模式下分片策略</h1>
 *
 *
 *  特别注意： Spring的AOP只能支持到方法级别的切入。换句话说，切入点只能是某个方法。
 *
 *
 */
@Aspect
@Configuration
@Slf4j
public class JpaHintShardingAspect {

//    //切入点
//    @Pointcut("execution(* fun.gengzi.dao.GoodsShardingJPA.*(..))")
//    public void jpaHintShardingAspectOld() {
//    }


    //切入点
    @Pointcut("execution(* fun.gengzi.dao.GoodsShardingJPA.*(..))")
    public void jpaHintShardingAspectNew() {
    }


    @Around("jpaHintShardingAspectNew()")
    public Object aroundNew(ProceedingJoinPoint joinPoint) {
        log.info("hintsharding start...");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 先清除当前存放的分片值
        HintManager.clear();
        HintManager hintManager = HintManager.getInstance();
        // 添加分片值
        hintManager.setDatabaseShardingValue(ShardingDataSourceType.TYPE_NEW.getType());
        Object obj;
        try {
            obj = joinPoint.proceed();
            HintManager.clear();
            // 添加分片值
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HintManager hintManager = HintManager.getInstance();
                        hintManager.setDatabaseShardingValue(ShardingDataSourceType.TYPE_OLD.getType());
                        Object obj = joinPoint.proceed();
                        // 再次清除当前存放的分片值
                        HintManager.clear();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }).start();

        } catch (Throwable e) {
            log.error("error: {} ", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            // 再次清除当前存放的分片值
            HintManager.clear();
        }
        return obj;
    }


//    @Around("jpaHintShardingAspectOld()")
//    public Object aroundOld(ProceedingJoinPoint joinPoint) {
//        log.info("hintsharding start...");
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        HintManager hintManager = HintManager.getInstance();
//        // 先清除当前存放的分片值
//        hintManager.clearShardingValues();
//        // 添加分片值
//        hintManager.setDatabaseShardingValue(ShardingDataSourceType.TYPE_OLD.getType());
//        Object obj;
//        try {
//            obj = joinPoint.proceed();
//            hintManager.clearShardingValues();
//        } catch (Throwable e) {
//            log.error("error: {} ", e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        } finally {
//            // 再次清除当前存放的分片值
//            hintManager.clearShardingValues();
//        }
//        return obj;
//    }

}
