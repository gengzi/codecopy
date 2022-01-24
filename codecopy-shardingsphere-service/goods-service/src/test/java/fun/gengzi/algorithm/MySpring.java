package fun.gengzi.algorithm;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <h1> </h1>
 *
 * @author Administrator
 * @date 2022/1/17 14:09
 */
public class MySpring {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("xx.xml");
        classPathXmlApplicationContext.getBean("xx");
    }



}
