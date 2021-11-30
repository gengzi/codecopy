package fun.gengzi.codecopy;

/**
 * 具体的修饰类-整容
 */
public class PlasticSurgery extends Skill{

    public PlasticSurgery(People people) {
        super(people);
    }

    @Override
    public void fighting() {
        super.fighting();
        System.out.println("通过整容变帅");
    }
}
