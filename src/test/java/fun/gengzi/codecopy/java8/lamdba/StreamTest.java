package fun.gengzi.codecopy.java8.lamdba;

import org.junit.Test;

import javax.naming.PartialResultException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
}
