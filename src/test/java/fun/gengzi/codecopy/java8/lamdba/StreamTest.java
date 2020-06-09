package fun.gengzi.codecopy.java8.lamdba;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fun.gengzi.codecopy.java8.lamdba.utils.Person;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.Test;

import javax.naming.PartialResultException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
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

        Stream<Dish> dishStream = menu.stream()
                .filter(n -> n.getCalories() > 350);

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
                .sorted(Comparator.comparing(Dish::getCalories).reversed())
                .collect(Collectors.toList());

        collect1.forEach(info -> System.err.println(info.getName()+":"+info.getCalories()));


    }


    @Test
    public void fun08() {

        List<Dish> collect1 = menu.stream()
                .filter(info -> {
                    System.out.println("filter:" + info.getName());
//                    menu.remove(0);
                    menu.set(5, new Dish("添加一个", true, 22, Dish.Type.FISH));
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

    @Test
    public void fun09() {
        Stream<Object> objectStream = Stream.of(null);
        ArrayList<String> arrayList = Lists.newArrayList();
        Stream<String> stream = arrayList.stream();

        Stream<String> stream1 = Arrays.stream(new String[]{"1", "2"});
        ConcurrentMap<String, String> objectObjectConcurrentMap = Maps.newConcurrentMap();
        Stream<String> stream2 = objectObjectConcurrentMap.values().stream();

        Set<String> strings = objectObjectConcurrentMap.keySet();
        Stream<Map.Entry<String, String>> stream3 = objectObjectConcurrentMap.entrySet().stream();
    }


    /**
     * 分组
     */
    @Test
    public void fun10() {
        List<Person> persons =
                Arrays.asList(
                        new Person(18, "Max"),
                        new Person(23, "Peter"),
                        new Person(23, "Pamela"),
                        new Person(12, "Pamela"));

        // 根据名称 进行分组
        Map<String, List<Person>> collect = persons.stream()
                .collect(Collectors.groupingBy(person -> person.getName()));


        collect.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(person -> System.out.println(person.toString()));
        });


        // 计算平均数
        Double collect1 = persons.stream()
                .collect(Collectors.averagingInt(person -> person.getId()));
        System.out.println(collect1);


        // 内置的概要统计对象
        IntSummaryStatistics collect2 = persons.stream()
                .collect(Collectors.summarizingInt(person -> person.getId()));
        System.out.println(collect2.getSum());
        System.out.println(collect2.getAverage());

        //

        String collect3 = persons.stream()
                .map(person -> person.getName())
                .distinct()
//                .collect(Collectors.joining(","));
                // 拼接， 第一个参数是中间拼接的内容， 第二个字段是前缀，第三个字段是 后缀
                .collect(Collectors.joining(",", "名字：", " 输出啦"));
        System.out.println(collect3);


        Integer reduce = persons.stream()
                .map(person -> person.getId())
                // 第一个参数 初始化的值  第二个参数 BinaryOperator 将两个元素结合起来变成一个新值
                // 在执行流中， p1 作为计算的第一个参数 0 ，从流中获取数据，作为 p2 的参数值，进行计算，然后再从流中获取数据，进行累加
                .reduce(0, (p1, p2) -> {
                    return p1 + p2;
                });
        System.out.println(reduce);

        Person person = persons.stream()
                // p1 是 new person 的第一个对象，p2 是从流中获取的
                // 关于 id 的累加，结果是 76 ，我们把 id 改为 1 ，结果应该变成 77
                .reduce(new Person(1, "person"), (p1, p2) -> {
                    p1.setId(p1.getId() + p2.getId());
                    p1.setName(p1.getName() + p2.getName());
                    return p1;
                });
        System.out.println(person.toString());


        Integer reduce1 = persons.stream()
                .map(person1 -> person1.getId())
                // Integer 的 内部，完成了 retur a+b 的操作
                .reduce(0, Integer::sum);

        Optional<Integer> reduce2 = persons.stream()
                .map(person1 -> person1.getId())
                // 计算最大值，因为没有提供初始化的参数，所以返回了一个 Optional 对象
                .reduce(Integer::max);
        // 如果存在
        reduce2.ifPresent(integer -> System.out.println(integer));
        Integer integer1 = reduce2.filter(integer -> integer > 0).orElse(0);
        System.out.println(integer1);

        persons.stream()
                //三个参数，第一个是初始化参数，第二个参数 每次都会拿 初始化参数 0 进行相加，第三个参数，会把得到的 sum 值，进行相加
                .reduce(0, (sum, person1) -> {
                    sum += person1.getId();
                    return sum;
                }, (sum1, sum2) -> {
                    return sum1 + sum2;
                });

        // reduce 是并行化的


        IntStream.range(1,4).forEach(n -> System.out.println(n));


    }


}
