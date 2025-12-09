package fun.gengzi.design.pattern;

/**
 * 单例模式
 */
//public class Singleton {
//
//    // 饿汉式，启动时就创建
//    // 懒汉式，使用时创建
//
//    private static final Singleton singleton = new Singleton();
//
//
//    private Singleton(){}
//
//
//    public static Singleton getInstance(){
//        return singleton;
//    }
//
//}

/**
 * 静态内部类构造
 */
//public class Singleton {
//
//    // 饿汉式，启动时就创建
//    // 懒汉式，使用时创建
//
//    public static class Holder{
//        private static final Singleton singleton = new Singleton();
//    }
//
//    private Singleton(){}
//
//
//    public static Singleton getInstance(){
//        return Holder.singleton;
//    }
//
//}

/**
 * 双重检查锁定
 */
//public class Singleton {
//
//
//    private static volatile Singleton singleton;
//
//
//    private Singleton() {
//    }
//
//
//    public Singleton getInstance() {
//        if (singleton == null) {
//            synchronized (Singleton.class) {
//                if (singleton == null) {
//                    singleton = new Singleton();
//                }
//            }
//        }
//        return singleton;
//    }
//
//
//}


/// **
// * 静态内部类
// */
//public class Singleton{
//
//    private Singleton(){}
//
//    public static class SingletonHolder{
//        private static final Singleton singleton = new Singleton();
//    }
//
//    public static Singleton getInstance(){
//        return SingletonHolder.singleton;
//    }
//
//}


public class Singleton {

    private static volatile Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}


