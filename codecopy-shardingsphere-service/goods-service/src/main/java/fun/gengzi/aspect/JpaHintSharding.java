package fun.gengzi.aspect;


import java.lang.annotation.*;


/**
 * TODO 暂时弃用，研究下spring 底层注解实现，再尝试
 */
@Target({ElementType.PACKAGE, ElementType.TYPE,ElementType.METHOD,ElementType.TYPE_PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JpaHintSharding {

    /**
     * 类型
     */
    String dataSourceType() default "old";
}
