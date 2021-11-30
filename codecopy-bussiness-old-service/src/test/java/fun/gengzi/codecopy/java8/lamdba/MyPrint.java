package fun.gengzi.codecopy.java8.lamdba;

/**
 * 函数式接口，只包含一个抽象方法
 */
@FunctionalInterface
public interface MyPrint<T> {

    String output(T str);


    default void outputinfo(){
        System.out.println("info");
    }

}
