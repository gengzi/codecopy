package fun.gengzi.codecopy;

/**
 * 具体的修饰类-多人运动
 */
public class ManypeopleMotion extends Skill{

    public ManypeopleMotion(People people) {
        super(people);
    }

    @Override
    public void fighting() {
        super.fighting();
        System.out.println("通过多人运动变高");
    }
}
