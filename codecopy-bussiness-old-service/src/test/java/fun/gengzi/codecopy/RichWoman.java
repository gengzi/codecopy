package fun.gengzi.codecopy;

/**
 * 具体的修饰类-交一个富婆
 */
public class RichWoman extends Skill{

    public RichWoman(People people) {
        super(people);
    }

    @Override
    public void fighting() {
        super.fighting();
        System.out.println("通过交一个富婆变有钱");
    }
}
