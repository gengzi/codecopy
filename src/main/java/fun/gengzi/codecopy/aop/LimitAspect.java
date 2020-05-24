package fun.gengzi.codecopy.aop;

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
public class LimitAspect {

	//根据IP分不同的令牌桶, 每天自动清理缓存
	private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
			/*设置缓存容器的最大容量大小为1000*/
			.maximumSize(1000)
			/*设置过期时间为1天*/
			.expireAfterWrite(1, TimeUnit.DAYS)
			// RateLimiter 令牌桶算法的工具类,时间窗口能且仅能为 1s
			.build(new CacheLoader<String, RateLimiter>() {
				@Override
				public RateLimiter load(String key) {
					// 新的IP初始化 每秒只发出5个令牌
					return RateLimiter.create(5);
				}
			});
	
	//Service层切点  限流
	@Pointcut("@annotation(fun.gengzi.codecopy.aop.ServiceLimit)")
	public void ServiceAspect() {
		
	}
	
    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		// 获取方法修饰的 @ServiceLimit 注解
		ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
		// 获取注解中的limitType参数
		ServiceLimit.LimitType limitType = limitAnnotation.limitType();
		// 获取注解中的key参数
		String key = limitAnnotation.key();
		Object obj;
		try {
			if(limitType.equals(ServiceLimit.LimitType.IP)){
				// 获取真实ip地址
				key = IPUtils.getIpAddr();
			}
			// 返回一个键在这个高速缓存中，首先装载如果需要该值相关联的值。
			RateLimiter rateLimiter = caches.get(key);
			// 非阻塞的获取令牌 可以实时返回结果
			Boolean flag = rateLimiter.tryAcquire();
			if(flag){
				obj = joinPoint.proceed();
			}else{
				throw new RrException("小同志，你访问的太频繁了");
			}
		} catch (Throwable e) {
			throw new RrException("小同志，你访问的太频繁了");
		}
		return obj;
    } 
}
