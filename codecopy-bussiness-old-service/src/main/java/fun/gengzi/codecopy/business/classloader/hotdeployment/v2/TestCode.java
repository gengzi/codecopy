package fun.gengzi.codecopy.business.classloader.hotdeployment.v2;

public class TestCode {

    static {
        System.out.println("TestCode类加载器:" + TestCode.class.getClassLoader());
    }

    public TestCode() {
        System.out.println("version2.0");
    }
}
