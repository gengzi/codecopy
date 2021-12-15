package fun.gengzi.aspect;

import fun.gengzi.codecopy.vo.ReturnData;
import fun.gengzi.enums.RspCodeEnum;
import fun.gengzi.enums.ShardingDataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>接口鉴权校验 aop</h1>
 *
 * @author gengzi
 * @date 2020年6月5日13:49:03
 */
@Aspect
@Configuration
@Slf4j
public class JpaHintShardingAspect {



    //切入点
    @Pointcut("execution(* fun.gengzi.dao.GoodsShardingJPA.*(..))")
    public void jpaHintShardingAspect() {
    }


    @Around("jpaHintShardingAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        log.info("hintsharding start...");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        JpaHintSharding jpaHintSharding = method.getAnnotation(JpaHintSharding.class);
        HintManager hintManager = HintManager.getInstance();
        hintManager.clearShardingValues();
        hintManager.setDatabaseShardingValue(jpaHintSharding.dataSourceType());
        Object obj;
        try {
            obj = joinPoint.proceed();
            hintManager.clearShardingValues();
        } catch (Throwable e) {
            log.error("error: {} ", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            hintManager.clearShardingValues();
        }
        return obj;
    }


}
