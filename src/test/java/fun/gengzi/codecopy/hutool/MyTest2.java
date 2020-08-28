package fun.gengzi.codecopy.hutool;


import fun.gengzi.codecopy.utils.datamask.BeanDataMaskUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyTest2 {


    @Test
    public void fun01() {
        TestObj testObJ = new TestObj();
        testObJ.setEmail("11640@qq.com");
        testObJ.setPhone("13526978456");
        testObJ.setTest("fdsf");

        TestObj testObj = (TestObj) BeanDataMaskUtils.maskObj(testObJ);

        System.out.println(testObj);

    }

    @Test
    public void fun02() {
        try {
            TestObj testObj = new TestObj();
            testObj.setPhone("13526978456");
            testObj.setTest("fdsf");

            ArrayList<TestObj> objects = new ArrayList<>();
            TestObj testObj1 = new TestObj();
            testObj1.setPhone("13526978456");
            testObj1.setTest("fdsf");
            TestObj testObj3 = new TestObj();
            testObj3.setPhone("13526978456");
            testObj3.setTest("fdsf");
            TestObj testObj4 = new TestObj();
            testObj4.setPhone("13526978456");
            testObj4.setTest("fdsf");
            objects.add(testObj3);
            objects.add(testObj1);
            objects.add(testObj4);

            testObj.setTestObjs(objects);
            Class<? extends TestObj> aClass = testObj.getClass();


            Field listField = aClass.getDeclaredField("testObjs");
            System.out.println(listField.getGenericType());
            //获取 list 字段的泛型参数
            ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();

            Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
            System.out.println(listActualTypeArguments[listActualTypeArguments.length - 1]);
            for (int i = 0; i < listActualTypeArguments.length; i++) {
                System.out.println(listActualTypeArguments[i]);
                Object o = listField.get(testObj);
                Class<?> aClass1 = o.getClass();


            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fun03() {
        TestObj testObj = new TestObj();
        testObj.setPhone("13526978456");
        testObj.setEmail("11640@qq.com");
        testObj.setTest("fdsf");

        ArrayList<TestObj> objects = new ArrayList<>();
        TestObj testObj1 = new TestObj();
        testObj1.setPhone("13526978456");
        testObj1.setEmail("11640@qq.com");
        testObj1.setTest("fdsf");
        TestObj testObj3 = new TestObj();
        testObj3.setPhone("13526978456");
        testObj3.setTest("fdsf");
        TestObj testObj4 = new TestObj();
        testObj4.setPhone("13526978456");
        testObj4.setTest("fdsf");
        objects.add(testObj3);
        objects.add(testObj1);
        objects.add(testObj4);


        testObj.setTestObjs(objects);

        TestObj o = (TestObj) BeanDataMaskUtils.maskObj(testObj);


        System.out.println(o);

    }


    @Test
    public void fun04() {
        TestObj testObj = new TestObj();
        testObj.setPhone("13526978456");
        testObj.setEmail("11640@qq.com");
        testObj.setTest("fdsf");

        ArrayList<TestObj> objects = new ArrayList<>();
        TestObj testObj1 = new TestObj();
        testObj1.setPhone("13526978456");
        testObj1.setEmail("11640@qq.com");
        testObj1.setTest("fdsf");
        TestObj testObj3 = new TestObj();
        testObj3.setPhone("13526978456");
        testObj3.setTest("fdsf");
        TestObj testObj4 = new TestObj();
        testObj4.setPhone("13526978456");
        testObj4.setTest("fdsf");
        objects.add(testObj3);
        objects.add(testObj1);
        objects.add(testObj4);




        testObj.setTestObjs(objects);
        String[] strings = {"中国河南郑州二七广场火车站","中国河南郑州二七广场高铁站"};
        testObj.setAddressinfo(strings);

        TestObj o = (TestObj) BeanDataMaskUtils.maskObj(testObj);


        System.out.println(o);

    }


}
