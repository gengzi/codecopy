package fun.gengzi.codecopy.java8.lamdba;


import com.sun.xml.internal.fastinfoset.sax.SAXDocumentSerializerWithPrefixMapping;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class lamdbaTest {

    /**
     * 任意只包含一个抽象方法的接口，我们都可以用来做成lambda表达式
     */
    @Test
    public void fun() {

        List<String> strs = Arrays.asList("c", "a", "d");

        Collections.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        Collections.sort(strs, (String o1, String o2) -> {
            return o2.compareTo(o1);
        });

        Collections.sort(strs, (String o1, String o2) -> o2.compareTo(o1));

        Collections.sort(strs, (o1, o2) -> o2.compareTo(o1));

        // 自然顺序相反的比较
        Collections.sort(strs, Comparator.reverseOrder());

        // 自然顺序的比较
        Collections.sort(strs, Comparator.naturalOrder());


        strs.forEach(s -> {
            System.out.println(s);
        });

        strs.forEach(s -> System.out.println(s));

        // 函数式接口 ： 只包含一个抽象方法的接口
        // 任意只包含一个抽象方法的接口，我们都可以用来做成lambda表达式

    }

    /**
     *  使用函数式接口
     */
    @Test
    public void fun2() {
        String strw = "aaa";
        MyPrint<String> myPrint = (str) -> str;
        System.out.println(myPrint.output(strw));
    }


    /**
     * 双 :: 的使用
     * 获取方法或者构造函数的的引用
     * 类 :: 静态方法
     */
    @Test
    public void fun3(){
        int num = 100;
        MyPrint<Integer> myPrint = String::valueOf;
        System.out.println(myPrint.output(num));
    }

    /**
     * 双 :: 的使用
     * 获取方法或者构造函数的的引用
     * 对象 :: 一般方法
     */
    @Test
    public void fun4(){
        Something something = new Something();
        String strw = "hello world";
        MyPrint<String> myPrint = something::startsWith;
        System.out.println(myPrint.output(strw));
    }

    /**
     * 双 :: 的使用
     * 获取方法或者构造函数的的引用
     * 类 :: 构造方法
     */
    @Test
    public void fun5(){
        String strw = "hello world";
        // String::new 来拿到一个 String 对象的引用
        MyPrint<String> myPrint = String::new;
        System.out.println(myPrint.output(strw));
    }

}
