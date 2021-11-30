package fun.gengzi.codecopy.hutool;

import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
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
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public class MyTest {


    // xml 转 bean ， 将 xml 标签，转成 bean 属性  完成
    // xml 内容替换为  #标签#  ，再手动替换固定字段  完成
    // 生成表单项 ，根据 实体
    // bean 转 map ，封装参数 完成


    // 电子化影像 替换字段
    // 提交接口， 参数校验，参数封装


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
    public void fun03() {
        String s = "NameBean";
        String s1 = String.valueOf(s.charAt(0)).toLowerCase() + s.substring(1);
        System.out.println(s1);

    }


    @Test
    public void fun02() {
        soutBean(xml);
    }

    /**
     * xml 转 实体属性字段
     *
     * @param xml
     */
    public static void soutBean(String xml) {
        String regStr = "</(.*?)>";
        List<String> resultFindAll = ReUtil.findAll(regStr, xml, 1, new ArrayList<String>());
        Map<String, String> filedAndDesc = new HashMap<>();
        resultFindAll.forEach(
                s -> {
                    String regStrByDesc = "<" + s + ">(((?!<).)*)</" + s + ">";
                    List<String> temp = ReUtil.findAll(regStrByDesc, xml, 1, new ArrayList<String>());
                    if (temp != null && !temp.isEmpty()) {
                        filedAndDesc.put(s, temp.get(0));
                    }
                }
        );
        filedAndDesc.forEach((key, value) -> {
            String newKey = String.valueOf(key.charAt(0)).toLowerCase() + key.substring(1);
            System.out.println("// " + value);
            System.out.println("private String " + newKey + ";");
        });
    }


    /**
     * javabean 转 map
     *
     * @param bean
     * @return
     * @throws Exception
     */
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


    /**
     * xml转属性字段拼接为  <A>#a#</A>
     *
     * @param xml
     */
    public static void xmlToNewXml(String xml) {
        AtomicReference<String> newxml = new AtomicReference<>("");
        String regStr = "</(.*?)>";
        List<String> resultFindAll = ReUtil.findAll(regStr, xml, 1, new ArrayList<String>());
        Map<String, String> filedAndDesc = new HashMap<>();
        resultFindAll.forEach(
                s -> {
                    String regStrByDesc = "<" + s + ">(((?!<).)*)</" + s + ">";
                    List<String> temp = ReUtil.findAll(regStrByDesc, xml, 0, new ArrayList<String>());
                    if (temp != null && !temp.isEmpty()) {
                        filedAndDesc.put(s, temp.get(0));
                    }
                }
        );
        newxml.set(xml);
        filedAndDesc.forEach((key, value) -> {
            System.out.println(value);
//            String regStrByDesc = "<(.*?)>"+value+"</(.*?)>";
            String newKey = String.valueOf(key.charAt(0)).toLowerCase() + key.substring(1);
            String temp = newxml.toString().replaceAll(value, "<" + key + ">#" + newKey + "#</" + key + ">");
            newxml.set(temp);
        });
        System.out.println(newxml.toString());
    }

    @Test
    public void fun04() {
        xmlToNewXml(xml);
    }


    @Test
    public void fun05() {
        int i = Integer.parseInt("01");
        System.out.println(i);
    }


    @Test
    public void fun06() {
        byte[] data = "我是一段测试字符串".getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        //签名
        byte[] signed = sign.sign(data);
        //验证签名
        boolean verify = sign.verify(data, signed);
        System.out.println(verify);
    }


}
