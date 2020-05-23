package fun.gengzi.codecopy;


/**
 * 人物, 具体的对象
 */
public class Person {
    public Person() {
    }

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println("装饰" + name);
    }

}
