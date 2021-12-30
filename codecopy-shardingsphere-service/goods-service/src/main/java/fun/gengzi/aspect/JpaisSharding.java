package fun.gengzi.aspect;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.*;

/**
 * <h1>是否开启新库旧库切换</h1>
 *
 * @author gengzi
 * @date 2021年12月30日15:58:04
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JpaisSharding {

    /**
     * 是否开启新库旧库切换
     * <p>
     * N 只执行旧库
     * <p>
     * Y 新库旧库都执行
     */
    Type value() default Type.Y;


    @Getter
    @AllArgsConstructor
    enum Type {
        Y("Y"),
        N("N");

        String code;
    }


}
