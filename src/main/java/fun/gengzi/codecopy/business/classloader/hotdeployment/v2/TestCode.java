package fun.gengzi.codecopy.business.classloader.hotdeployment.v2;

public class TestCode {

    static {
        System.out.println("classloader:" + TestCode.class.getClassLoader());
        System.out.println("chufa111111111");
    }

    public TestCode() {
        System.out.println("gouzhaofangfa132111fdaf2fdsfad5522255");
    }
}
