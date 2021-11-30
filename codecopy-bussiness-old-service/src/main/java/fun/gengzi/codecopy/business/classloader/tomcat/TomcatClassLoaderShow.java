package fun.gengzi.codecopy.business.classloader.tomcat;

import java.lang.reflect.Method;

public class TomcatClassLoaderShow {


    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader sharedLoader = null;

        String methodName = "setParentClassLoader";
        Class<?> paramTypes[] = new Class[1];
        paramTypes[0] = Class.forName("java.lang.ClassLoader");
        Object paramValues[] = new Object[1];
        paramValues[0] = sharedLoader;
//        Method method =
//                startupInstance.getClass().getMethod(methodName, paramTypes);
//        method.invoke(startupInstance, paramValues);





    }







}
