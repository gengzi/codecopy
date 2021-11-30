package fun.gengzi.codecopy.aop;

import java.lang.annotation.*;

/**
 * <h1>接口鉴权注解</h1>
 * 用于标识那些controller 接口需要鉴权才能调用
 *
 * @author gengzi
 * @date 2020年6月4日16:43:30
 * <p>
 * 对于接口鉴权，也可以使用 拦截器，过滤器，指定那些路径进行拦截。
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessAuthentication {


    /**
     * 调用次数限定 默认 -1 不限制次数
     * @return
     */
    int callNumber() default -1;

//    /**
//     * ip 限定，限定指定范围的ip地址，访问该接口
//     *
//     * @return
//     */
//    String[] IPLimit() default {};




}
