package fun.gengzi.codecopy.java8.lamdba.stream;

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


}
