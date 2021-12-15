package fun.gengzi.algorithm;


import fun.gengzi.dao.GoodsJPA;
import fun.gengzi.entity.GoodsEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PublicDataExecute {

    @Autowired
    private GoodsJPA goodsJPA;


    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {


        PublicDataExecute publicDataExecute = new PublicDataExecute();
        publicDataExecute.fun01(GoodsJPA.class,goodsJPA,new GoodsEntity(),"save");
    }

    public void fun01(Class clazz ,GoodsJPA jpa, GoodsEntity entity,String method,Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // 检索方法，是否在这个对象中

        // 存在，获取此方法参数，如果是对象，判断是否为 goodentity ，如果是，调用该方法

        // 如果不是对象，获取参数列表，进行 参数类型匹配，匹配成功，调用方法执行，不匹配，报错

        Method[] methods1 = clazz.getMethods();


        Method save = clazz.getMethod("save", Object.class);

        Class<?>[] parameterTypes = save.getParameterTypes();

        Object invoke = save.invoke(jpa, entity);

        GoodsJPA.class.getMethod("save", Object.class);


//        Class<? extends GoodsJPA> aClass = jpa.getClass();
//        Method[] methods = aClass.getMethods();
//        log.info("所有的方法{}",methods);


    }


}
