package fun.gengzi.codecopy.aop;


import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 *  接口权限校验 aop
 */
@Aspect
@Configuration
public class BusinessAuthenticationAspect {

    //切入点
    @Pointcut("@annotation(fun.gengzi.codecopy.aop.BusinessAuthentication)")
    public void BusinessAuthenticationAspect() {

    }

    @Around("BusinessAuthenticationAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        // 根据controller 配置的注解，执行该方法
        // 获取请求信息中的 token 校验，存在，执行 token 校验，不存在，阻断
        // 校验 token ，失败，阻断
        // 获取该注解配置的字段信息
        // 校验该用户是否还有调用次数 ， 无 ，阻断
        // 校验该用户是否在允许的ip 范围， 无，阻断
        // 放行


        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        BusinessAuthentication businessAuthentication = method.getAnnotation(BusinessAuthentication.class);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 校验token 是否有效
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 获取controller 方法名称
        String name = method.getName();
        // 如果没有 token，进行记录并抛出异常，响应前台
        if(StringUtils.isNoneBlank(token)){
            throw new RrException("无权限", RspCodeEnum.NOTOKEN.getCode());
        }

        // 调用鉴权服务，进行 token 校验，并返回该用户信息

        int callNumber = businessAuthentication.callNumber();
        String[] ipLimit = businessAuthentication.IPLimit();



        Object obj;
        try {
            // 非阻塞的获取令牌 可以实时返回结果
            Boolean flag = true;
            if(flag){
                obj = joinPoint.proceed();
            }else{
                throw new RrException("无权限");
            }
        } catch (Throwable e) {
            throw new RrException("无权限");
        }
        return obj;
    }

}
