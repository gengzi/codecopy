package fun.gengzi.codecopy;


/**
 * 装饰类 继承 对象接口
 */
public class Finery extends Person {

    private Person person;

    public void decorate(Person person) {
        this.person = person;
    }

    public void show() {
        person.show();

    }


}
