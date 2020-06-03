package fun.gengzi.codecopy.java8.lamdba;

import org.apache.kafka.common.protocol.types.Field;
import org.junit.Test;

import javax.naming.PartialResultException;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

    List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    /**
     * 查询出 热量高于350 的菜肴名称,从大到小排序,只获取前三个
     */
    @Test
    public void fun01() {

        List<String> menuNames = menu.stream()
                .filter(menuinfo -> menuinfo.getCalories() > 350)
                .sorted(Comparator.comparing(menuinfo -> menuinfo.getCalories()))
                .limit(3)   // 该方法会返回一个不超过给定长度的流
                .map(menuinfo -> menuinfo.getName())
                .collect(Collectors.toList());


        menuNames.forEach(menuName -> {
            System.out.println(menuName);
        });

    }

    @Test
    public void fun07() {
//        menu.stream().map(menuinfo -> menuinfo.getName())
//                .filter(s -> s.startsWith("p"));

//        List<Dish> collect = menu.stream()
//                .map(info -> {
//                    System.out.println("map:" + info.getName());
//                    return new Dish(info.getName(), true, info.getCalories(), info.getType());
//
//                })
//                .filter(info -> {
//                    System.out.println("filter:" + info.getName());
//                    return info.getType().equals(Dish.Type.MEAT);
//                })
//                .sorted(Comparator.comparing(Dish::getCalories))
//                .collect(Collectors.toList());


        List<Dish> collect1 = menu.stream()
                .filter(info -> {
                    System.out.println("filter:" + info.getName());
                    return info.getType().equals(Dish.Type.MEAT);
                })
                .map(info -> {
                    System.out.println("map:" + info.getName());
                    return new Dish(info.getName(), true, info.getCalories(), info.getType());

                })
                .sorted(Comparator.comparing(Dish::getCalories))
                .collect(Collectors.toList());

        collect1.forEach(info -> System.out.println(info.getName()));


    }


    @Test
    public void fun08() {

        List<Dish> collect1 = menu.stream()
                .filter(info -> {
                    System.out.println("filter:" + info.getName());
//                    menu.remove(0);
                    menu.set(5,new Dish("添加一个",true,22,Dish.Type.FISH));
//                    menu.add(new Dish("添加一个",true,22,Dish.Type.FISH));
                    return info.getType().equals(Dish.Type.MEAT);
                })
                .map(info -> {
                    System.out.println("map:" + info.getName());
                    return new Dish(info.getName(), true, info.getCalories(), info.getType());
                })
                .sorted(Comparator.comparing(Dish::getCalories))
                .collect(Collectors.toList());

        menu.forEach(info -> System.out.print(info.getName()));

        collect1.forEach(info -> System.out.println(info.getName()));
    }


    /**
     * 筛选出 不重复的菜单信息
     */
    @Test
    public void fun02() {

        List<String> menuNames = menu.stream()
                .map(Dish::getName)
                .distinct()
                .collect(Collectors.toList());


        menuNames.forEach(menuName -> {
            System.out.println(menuName);
        });

    }

    /**
     * 查询出 热量高于350 的菜肴名称,从大到小排序,只获取第三个
     */
    @Test
    public void fun03() {

        List<String> menuNames = menu.stream()
                .filter(menuinfo -> menuinfo.getCalories() > 350)
                .sorted(Comparator.comparing(menuinfo -> menuinfo.getCalories()))
                .limit(3)
                .skip(2)  // 返回一个扔掉了前 n 个元素的流  如果流中元素不足 n 个，则返回一 个空流
                .map(menuinfo -> menuinfo.getName())  // 映射某一列
                .collect(Collectors.toList());


        menuNames.forEach(menuName -> {
            System.out.println(menuName);
        });
    }

    /**
     * 查询出菜单名中，包含的所有字符，去重
     */
    @Test
    public void fun04() {

        List<String[]> collect = menu.stream()
                .map(menuinfo -> menuinfo.getName().split(""))  // 映射某一列
                .distinct()
                .collect(Collectors.toList());

        List<String> collect1 = collect.stream()
                .flatMap(arr -> Arrays.stream(arr))  // 生成的单个流都被合并起来，即扁平化为一个流
                .distinct().collect(Collectors.toList());

        collect1.forEach(s -> System.out.println(s));


//        menuNames.forEach(menuName -> {
//            System.out.println(menuName);
//        });
    }


    @Test
    public void fun05() {

        // 返回顺序Stream包含的单个元素
        List<String> strings = Arrays.asList("是", "否");
        Set<List<String>> collect1 = Stream.of(strings).collect(Collectors.toSet());

        //返回顺序排列流，其元素为指定的值
        List<String> collect = Stream.of("是", "否").collect(Collectors.toList());

        Arrays.stream(new String[]{"aaaa", "b", "cc"}).sorted().limit(2).collect(Collectors.toList());


        IntStream.range(1, 4).forEach(
            num -> System.out.println(num)
        );



    }


}
