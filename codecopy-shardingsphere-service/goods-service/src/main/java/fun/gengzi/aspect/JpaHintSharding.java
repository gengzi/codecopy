package fun.gengzi.aspect;


import fun.gengzi.enums.ShardingDataSourceType;

import java.lang.annotation.*;

@Target({ElementType.PACKAGE, ElementType.TYPE,ElementType.METHOD,ElementType.TYPE_PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JpaHintSharding {

    /**
     * 类型
     */
    String dataSourceType() default "old";
}
