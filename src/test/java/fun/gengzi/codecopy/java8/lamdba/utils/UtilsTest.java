package fun.gengzi.codecopy.java8.lamdba.utils;

import fun.gengzi.codecopy.utils.JsonUtils;
import io.swagger.models.auth.In;
import oracle.ucp.proxy.annotation.Pre;
import org.junit.jupiter.api.Test;
import scala.collection.immutable.Stream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * lamdba 函数式接口测试
 * lamdba 无法使用default 方法
 */
public class UtilsTest {

    /**
     * Predicate 断言，一个布尔类型的函数
     * 可以用于 集合类，filter 的过滤等等
     */
    @Test
    public void fun() {
        // Predicate是一个布尔类型的函数
        Predicate<String> predicate = (s) -> s.length() > 0;
        boolean str1 = predicate.test("hello world");
        boolean str2 = predicate.test("");

        System.out.println(str1);
        System.out.println(str2);

        // 短路与 &&
        boolean test = predicate.and((s) -> s.equals("hello")).test("aaa");
        System.out.println(test);
        // 逻辑非
        boolean hello_world = predicate.negate().test("hello world");
        System.out.println(hello_world);
        // 逻辑或
        boolean aaa = predicate.or(predicate).test("aaa");
        System.out.println(aaa);
        // 判断两个对象是否相等
        boolean test2 = Predicate.isEqual("a2").test("a3");
        System.out.println(test2);
        boolean test3 = Predicate.isEqual("a3").test("a3");
        System.out.println(test3);
        boolean test4 = Predicate.isEqual(null).test("a3");
        System.out.println(test4);


        Predicate<String> ceshi1 = String::isEmpty;
        boolean test1 = ceshi1.test("");
        System.out.println(test1);




//        Predicate<String> predicate1 = new Predicate<String>() {
//            /**
//             * Evaluates this predicate on the given argument.
//             *
//             * @param s the input argument
//             * @return {@code true} if the input argument matches the predicate,
//             * otherwise {@code false}
//             */
//            @Override
//            public boolean test(String s) {
//                return predicate.test(s) && hello.test(s);
//            }
//        };
//        boolean test = predicate1.test("111");
//        System.out.println(test);
    }

    /**
     * 表示接受一个参数，并返回一个结果
     * 可以理解为  y = f(x)
     * 接受x 参数，输出y，那么 f(x) 这个函数，我们自己定义就可以了
     */
    @Test
    public void fun02() {

        // 定义 f(x) 函数
        Function<Integer, Integer> function = (x) -> x + 1;
        // 获取结果
        Integer result = function.apply(5);
        System.out.println(result);

        // 定义 f(x) 函数
        Function<Integer, Integer> function1 = (x) -> x * 5;

        // compose 代表先执行 compose 传入的逻辑，再执行apply 的逻辑
        // 6
        Integer apply = function.compose(function1).apply(1);
        System.out.println(apply);

        // andThen 代表先执行当前的逻辑，再执行，andthen 传入的逻辑
        Integer apply1 = function.andThen(function1).apply(1);
        System.out.println(apply1);

        // ((1+1)+1)*5*5  建造者模式
        Integer apply2 = function.andThen(function1).andThen(function1).compose(function).apply(1);
        System.out.println(apply2);

        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String
                ::valueOf);
        backToString.apply("123"); // "123"
    }


    /**
     * 接口产生一个给定类型的结果
     */
    @Test
    public void fun03() {
        Supplier<String> str = String::new;
        String s = str.get();
        Supplier<Person> str1 = Person::new;
        // 在执行get 方法时，才会拿到person 对象 ，跟spring beanfactory  在执行 getbean 的时候，才会创建该对象
        Person person = str1.get();
    }

    /**
     * 接受一个参数输入且没有任何返回值的操作
     * 在 集合的 foreach 中 就需要填这个接口
     */
    @Test
    public void fun04() {
        Consumer<Person> personConsumer = (t) -> System.out.println("第一打印" + t.toString());
        Consumer<Person> personConsumer2 = (t) -> System.out.println("第二打印" + t.toString());
        // 执行
        personConsumer.accept(new Person());

        // 现在执行 accpect ，再执行 addthen 添加的
        personConsumer.andThen(personConsumer2).accept(new Person());

        // foreach 代码中，就调用改的 action.accept(t);
        Arrays.asList("1", "2").forEach((x) -> {
            System.out.println(x);
        });
    }

    /**
     * 比较器
     */
    @Test
    public void fun05() {
        Comparator<Person> personComparator = (p1, p2) -> {
            return p1.getName().compareTo(p2.getName());
        };
        personComparator.compare(new Person(), new Person());

    }

    /**
     * optional
     */
    @Test
    public void fun06() {
        Optional<String> optionalS = Optional.of("bam");
        // 判断是否存在
        boolean present = optionalS.isPresent();
        System.out.println(present);
        // 返回存在的值
        String s = optionalS.get();
        System.out.println(s);
        // 返回值（如果存在），否则返回other
        String fallback = optionalS.orElse("fallback");
        System.out.println(fallback);
        // 如果存在，就执行代码
        optionalS.ifPresent((ss) -> {
            System.out.println(ss.charAt(0));
        });
    }


    @Test
    public void fun07() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");


        stringCollection.stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(s -> System.out.println(s));

        stringCollection.stream()
                .sorted((p1, p2) -> {
                    return p1.compareTo(p2);
                })
                .filter(s -> s.startsWith("b"))
                .forEach(s -> System.out.println(s));

        // sorted只是创建一个流对象排序的视图,不会改变原来集合的顺序
        stringCollection.stream()
                .sorted(Comparator.naturalOrder())
                .filter(s -> s.startsWith("b"))
                .forEach(s -> System.out.println(s));

        // map 它能够把流对象中的每一个元素对应到另外一个对象上。

        stringCollection.stream()
                .sorted()
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .forEach(s -> System.out.println(s));


        // match 判断某一种规则是否与流对象相互吻合的 终结操作

        // 检测所有都是 a 开头的
        boolean a = stringCollection.stream()
                .allMatch(s -> s.startsWith("a"));
        System.out.println(a);

        // 检测没有 a 开头的
        boolean a1 = stringCollection.stream()
                .noneMatch(s -> s.startsWith("a"));
        System.out.println(a1);

        // 检测可能 a 开头的
        boolean a2 = stringCollection.stream()
                .anyMatch(s -> s.startsWith("a"));
        System.out.println(a2);


        // count 求个数  终结操作

        long a3 = stringCollection.stream()
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .count();

        System.out.println(a3);


        //reduce它能够通过某一个方法，对元素进行削减操作。 终结操作

        Optional<String> a4 = stringCollection.stream()
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .reduce((s1, s2) ->
                        s1 + "*" + s2
                );
        String s = a4.get();
        System.out.println(s);


    }


    @Test
    public void fun08() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        // 并行操作
        stringCollection.parallelStream().sorted().count();

        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            // t避免我们将null写入
            map.putIfAbsent(i, "val" + i);
        }
        // 判断是否存在，存在，就设置这个val
        map.computeIfPresent(3, (num, val) -> num + val);
        map.get(3);
        map.computeIfPresent(9, (num, val) -> null);
        boolean b = map.containsKey(9);
        System.out.println(b);
        // 不存在，就放进去
        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23); // true
        // 存在，就进不去
        map.computeIfAbsent(3, num -> "bam");
        map.get(3);

        map.remove(3);

        // 获取这个key，没有就获取一个默认值
        String not_found = map.getOrDefault(100, "not found");
        System.out.println(not_found);
        // 合并操作，如果存在，就合并，如果不存在，就存入map
        map.merge(112, "val9", (value, newvalue) -> value.concat(newvalue));
        map.merge(0, "concat", (value, newValue) -> value.concat(newValue));

        map.forEach((id, val) -> System.out.println(id + ":" + val));
    }


    @Test
    public void fun100() {
        List<Person> list = new ArrayList();
        list.add(new Person(1, "haha"));
        list.add(new Person(2, "rere"));
        list.add(new Person(3, "fefe"));
//Map<Integer, Person> mapp = list.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
//Map<Integer, Person> mapp = list.stream().collect(Collectors.toMap(x -> x.getId(), x->x));
//System.out.println(mapp);
        Map<Integer, String> map = list.stream().collect(Collectors.toMap(Person::getId, Person::getName));
//Map<Integer, String> map = list.stream().collect(Collectors.toMap(Person::getId, Person::getName,(x1,x2)->x1))

    }

    @Test
    public void fun102() {
        fun101(s -> {
            System.out.println(s);
            s.toLowerCase();
        });

        fun101(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

    }

    public void fun101(Consumer<String> consumer) {
        consumer.accept("M");
    }


}
