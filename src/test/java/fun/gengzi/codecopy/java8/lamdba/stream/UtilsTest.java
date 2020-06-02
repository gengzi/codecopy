package fun.gengzi.codecopy.java8.lamdba.stream;

import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.java8.lamdba.utils.Person;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * stream 流
 */
public class UtilsTest {





    @Test
    public void fun() {
        Arrays.asList("1", "2")
                .stream()
                .findFirst()
                .ifPresent(s -> System.out.println(s));
        // 使用 stream 创建一个流
        Stream.of("1", "22", "33")
                .findFirst()
                .ifPresent(s -> System.out.println(s));

        // IntStream
//        LongStream
        IntStream.range(1, 4)
                .forEach(s -> System.out.print(s));

    }


    /**
     * collect 是非常有用的终止操作，将流中的元素存放在不同类型的结果中，例如 List 、 Set 或者 Map 。
     *
     * Map 中提供了一个 getOrDefault 方法，如果没有返回值，就返回一个默认的值
     *
     *
     *
     *
     *
     *
     *
     */

    @Test
     public void fun01(){
        // https://mp.weixin.qq.com/s/G5CP7ljaK75uT2Nu4zE82w
        // 会出现异常 Caused by: java.lang.IllegalStateException: Duplicate key failure  提示 key 重复
        // 解决方法，设置key 重复时，值怎么变化
        String s = RspCodeEnum.fromCodeToDesc(RspCodeEnum.FAILURE.getCode());
        System.out.println(s);

    }

}
