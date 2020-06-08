package fun.gengzi.codecopy.hutool;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import fun.gengzi.codecopy.Person;
import fun.gengzi.codecopy.java8.lamdba.utils.Trader;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class MyTest {

    /**
     *
     */
    @Test
    public void fun() {
        Trader trader = new Trader("张三", "李四");

        try {
            Map<String, String> stringObjectMap = bean2map(trader);
            stringObjectMap.forEach((s, v) -> {
                System.out.println(s);
                System.out.println(v);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // xml 转 bean ， 将 xml 标签，转成 bean 属性
    // xml 内容替换为  #标签#  ，再手动替换固定字段
    // 生成表单项 ，根据 实体
    // bean 转 map ，封装参数


    // 电子化影像 替换字段
    // 提交接口， 参数校验，参数封装


    //把JavaBean转化为map
    public static Map<String, String> bean2map(Object bean) throws Exception {
        Map<String, String> map = new HashMap<>();
        //获取JavaBean的描述器
        BeanInfo b = Introspector.getBeanInfo(bean.getClass(), Object.class);
        //获取属性描述器
        PropertyDescriptor[] pds = b.getPropertyDescriptors();
        //对属性迭代
        for (PropertyDescriptor pd : pds) {
            //属性名称
            String propertyName = pd.getName();
            //属性值,用getter方法获取
            Method m = pd.getReadMethod();
            Object properValue = m.invoke(bean);//用对象执行getter方法获得属性值
            //把属性名-属性值 存到Map中
            map.put("#" + propertyName + "#", properValue + "");
        }
        return map;
    }

    final String xml = "<root>\n" +
            "    <flw>\n" +
            "    <name>aa</name>\n" +
            "    <age>22</age>\n" +
            "    <instance_info>\n" +
            "          <num>1</num>\n" +
            "           <code>0</code>\n" +
            "          <instance>\n" +
            "               <ip>10.1.1.2</ip>\n" +
            "               <mask>9999</mask>\n" +
            "         </instance>\n" +
            "         <instance>\n" +
            "               <ip>10.1.1.5</ip>\n" +
            "               <mask>717</mask>\n" +
            "         </instance>\n" +
            "    </instance_info>\n" +
            "    \n" +
            "    <instance_info>\n" +
            "          <num>2</num>\n" +
            "           <code>33</code>\n" +
            "          <instance>\n" +
            "               <ip>10.1.1.2</ip>\n" +
            "               <mask>9999</mask>\n" +
            "         </instance>\n" +
            "         <instance>\n" +
            "               <ip>10.1.1.9</ip>\n" +
            "               <mask>878</mask>\n" +
            "         </instance>\n" +
            "    </instance_info>\n" +
            "   </flw>\n" +
            "</root>";


    @Test
    public void fun02() {

        soutBean(xml);
    }

    @Test
    public void fun03() {
        String s = "NameBean";
        String s1 = String.valueOf(s.charAt(0)).toLowerCase() + s.substring(1);
        System.out.println(s1);

    }


    public static void soutBean(String xml) {
        String regStr = "</(.*?)>";
        List<String> resultFindAll = ReUtil.findAll(regStr, xml, 1, new ArrayList<String>());
        Map<String, String> filedAndDesc = new HashMap<>();
        resultFindAll.forEach(
                s -> {
                    String regStrByDesc = "<" + s + ">(((?!<).)*)</" + s + ">";
                    List<String> temp = ReUtil.findAll(regStrByDesc, xml, 1, new ArrayList<String>());
                    if (temp != null && !temp.isEmpty()) {
                        filedAndDesc.put(s,temp.get(0));
                    }
                }
        );

//        filedAndDesc.forEach((key,value)->{
//            System.out.println(key);
//            System.out.println(value);
//        });

        filedAndDesc.forEach((key,value)->{
            String newKey = String.valueOf(key.charAt(0)).toLowerCase() + key.substring(1);
            System.out.println("// "+value);
            System.out.println("private String " + newKey + ";");
        });


//        List<String> resultNew = resultFindAll.stream()
//                .distinct()
//                .map(s -> String.valueOf(s.charAt(0)).toLowerCase() + s.substring(1))
//                .collect(Collectors.toList());
//
//
//        resultNew.forEach(s -> System.out.println("private String " + s + ";"));
    }


}
