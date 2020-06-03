package fun.gengzi.codecopy.java8.lamdba.utils;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class MyComparator {

    @Test
    public void fun01() {
        List<String> strings = Arrays.asList("aaaa", "a", "aaa");

        // 参数是一个 TOIntFunction 接口  接口实现可以使用匿名内部类的形式，也可以使用 lamdba 表达式 展示
        Comparator<String> stringComparator = Comparator.comparingInt((String s) -> s.length());
        strings.sort(stringComparator);
        strings.forEach(s -> System.out.println(s));

        // 使用treemap 进行排序，排序规则就是 stringComparator
        ConcurrentMap<String, Object> objectObjectConcurrentMap = Maps.newConcurrentMap();
        objectObjectConcurrentMap.put("13232", "a");
        objectObjectConcurrentMap.put("22", "a1");
        objectObjectConcurrentMap.put("3", "a2");
        objectObjectConcurrentMap.put("456", "a3");
        TreeMap<String, Object> stringObjectTreeMap = Maps.newTreeMap(stringComparator);
        stringObjectTreeMap.putAll(objectObjectConcurrentMap);
        // 打印
        stringObjectTreeMap.forEach((s, object) -> {
            System.out.println(s);
        });
    }


}
