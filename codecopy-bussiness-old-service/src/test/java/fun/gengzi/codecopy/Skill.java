package fun.gengzi.codecopy;

/**
 * 修饰器
 */
public class Skill implements People{
    // 拥有被装饰类的实例变量
    public People people;

    // 通过构造方法获取实际的被修饰对象
    public Skill(People people) {
        this.people = people;
    }

    @Override
    public void fighting() {
        people.fighting();
    }
}
