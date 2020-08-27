package fun.gengzi.codecopy.hutool;


import fun.gengzi.codecopy.utils.datamask.BeanDataMaskUtils;
import org.junit.Test;

public class MyTest2 {


    @Test
    public void fun01(){
        TestObj testObJ = new TestObj();
        testObJ.setEmail("11640@qq.com");
        testObJ.setPhone("13526978456");
        testObJ.setTest("fdsf");

        TestObj testObj = (TestObj) BeanDataMaskUtils.maskObj(testObJ);

        System.out.println(testObj);

    }



}
