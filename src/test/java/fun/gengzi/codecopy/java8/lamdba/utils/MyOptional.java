package fun.gengzi.codecopy.java8.lamdba.utils;


import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.zookeeper.Op;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * optional 示例
 */
public class MyOptional {
    /**
     * 创建optional 的三种方式
     */
    @Test
    public void fun01() {
        // 创建一个空的optional
        Optional<Object> empty = Optional.empty();

        // 依照一个非空值，获取一个optional
        // 如果为null ，会报空指针异常
        Optional<String> ss = Optional.of("ss");

        // 可接受null的optional
        Optional<Object> o = Optional.ofNullable(null);
    }


    @Test
    public void fun02() {


    }


    /**
     * 使用map 从 optional 对象中提取和转换值
     */
    @Test
    public void fun03() {
        Person person = new Person();
        person.setId(1);
        person.setName("zz");

        Person personNull = null;

        String name = null;
        if (person != null) {
            name = person.getName();
        }

        // 使用 orelse ，如果值存在，就返回，如果值不存在，就返回设置的值
        Optional<Person> person1 = Optional.ofNullable(personNull);
        Optional<String> name1 = person1.map(p -> p.getName());
        System.out.println(name1.orElse("未知"));

        // 在person 获取姓名，如果不存在，就返回一个默认姓名 张三
        // orElseGet 可以写一个返回值的逻辑  Supplier
        String info = Optional.ofNullable(personNull)
                .map(p -> p.getName())
                .orElseGet(() -> "张三");
        System.out.println(info);

        // 抛出一个异常 Supplier
//        Optional.ofNullable(personNull)
//                .orElseThrow(() -> {
//                    throw new RuntimeException("error");
//                });


    }

    /**
     * flatMap Optional<Optional<Roles>> 将两层 Optional 合并成一个，返回
     */
    public void fun04() {
        Person person = new Person();
//        Optional.ofNullable(person)
//                .map(person1 -> person1.getRoles())
//                .map()
//                .get();
        String id = Optional.ofNullable(person)
                .flatMap(person1 -> person1.getRoles())
                .map(roles -> roles.get(0).getId())
                .get();


        Optional<Person> person1 = Optional.ofNullable(person);
        Optional<Role> role = Optional.ofNullable(new Role());

        //  调用方法 ，有两个参数，一个是 person 一个是 role ，在方法中执行， 通过 flatmap 和 map 可以不用判断是否为 null
        Optional<String> optionalS = person1.flatMap(person2 -> role.map(role1 -> fun(person2, role1)));
    }

    public String fun(Person person, Role role) {
        return "";
    }

    /**
     * filter 进行过滤判断
     */
    @Test
    public void fun05(){
        Person person = new Person();
        String s = Optional.ofNullable(person)
                .filter(person1 -> person1.getName().equals("张三"))
                .map(person1 -> person1.getName()).orElse("xx");
    }
}
