package fun.gengzi.design.pattern.factory;


/**
 * 工厂类
 */
public class BeanFactory {

    public Object getBean(String name) {
        Object object = null;
        if("xx".equals(name)){
           object = new Object();
        }
        return object;
    }

}
