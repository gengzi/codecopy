package fun.gengzi.codecopy.business.classloader.hotdeployment;

public class MyTest {

    public static void main(String[] args) throws ClassNotFoundException {
        HotDeploymentClassLoader hotDeploymentClassLoader = new HotDeploymentClassLoader(ClassLoader.getSystemClassLoader(),"D:\\ideaworkspace\\codecopy\\codecopy\\target\\classes\\");
        hotDeploymentClassLoader.findClass("fun.gengzi.codecopy.business.classloader.hotdeployment.TestCode");
        hotDeploymentClassLoader.findClass("fun.gengzi.codecopy.business.classloader.hotdeployment.TestCode");

    }
}
