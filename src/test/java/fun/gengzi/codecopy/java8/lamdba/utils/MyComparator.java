package fun.gengzi.codecopy.java8.lamdba.utils;

import cn.hutool.core.util.IdcardUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MyComparator {

    @Test
    public void fun01() {
        List<String> strings = Arrays.asList("aaaa", "a", "aaa");

        // 参数是一个 TOIntFunction 接口  接口实现可以使用匿名内部类的形式，也可以使用 lamdba 表达式 展示
        Comparator<String> stringComparator = Comparator.comparingInt((String s) -> s.length());
        strings.sort(stringComparator);
        strings.forEach(s -> System.out.println(s));

        // 使用treemap 进行排序，排序规则就是 stringComparator
        ConcurrentMap<String, Object> objectObjectConcurrentMap = Maps.newConcurrentMap();
        objectObjectConcurrentMap.put("13232", "a");
        objectObjectConcurrentMap.put("22", "a1");
        objectObjectConcurrentMap.put("3", "a2");
        objectObjectConcurrentMap.put("456", "a3");
        TreeMap<String, Object> stringObjectTreeMap = Maps.newTreeMap(stringComparator);
        stringObjectTreeMap.putAll(objectObjectConcurrentMap);
        // 打印
        stringObjectTreeMap.forEach((s, object) -> {
            System.out.println(s);
        });
    }


//    @Resource(name = "getSignatureInfo", type = Function.class)
//    private Function<Object, String> signatureInfo;

    /**
     * 调用方式：
     * 使用 spring 注入
     *
     * @return
     * @Resource(name = "getSignatureInfo", type = Function.class)
     * private Function<Object, String> signatureInfo;
     * <p>
     * 注入后，调用 apply 方法，进行计算
     * @R
     */
    public Function<Object, String> getSignatureInfo() {
        return o -> {
            // function 函数，跟数学中的 y = f(x) 很像,传入x，执行 f(x) 的到 y
            if (o instanceof String) {
                return (String) o;
            }
            return "error";
        };
    }


    @Test
    public void fun02() {
        // signatureInfo.apply();
        // 调用 getSignatureInfo 方法
        String info = getSignatureInfo().apply("打印我");
        System.out.println(info);
    }


    /**
     * 通过身份证设置性别
     * <p>
     * supplier 产生一个给定类型的结果  不需要参数
     * <p>
     * consumer 不返回结果，但是需要一个参数 ，比如 集合.foreach  用的就是 consumer
     */
    private void setSexFromIdCardNo(String idCardNo, Supplier<String> supplier, Consumer<String> consumer) {
        boolean flag = StringUtils.isNoneBlank(idCardNo) && StringUtils.isNoneBlank(supplier.get()) && !"0".equals(supplier.get());
        if(flag){
            int genderByIdCard = IdcardUtil.getGenderByIdCard(idCardNo);
            if(genderByIdCard == 1){
                consumer.accept(backInfo("m"));
            }else if(genderByIdCard == 0){
                consumer.accept("F");
            }
        }
    }


    public String backInfo(String info){
        // 先走这个逻辑，再执行 consumer 实现的接口逻辑
        info = info.toUpperCase();
        System.out.println(info);
        return info;
    }


    public String sex = "e";

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     *  调用 setSexFromIdCardNo 需要传入两个接口的实现，可以使用匿名内部类实现，也可以使用 lamdba 表达式
     *  或者使用 :: 双冒号的形式，调用现有的方法，代替两个接口的实现
     */
    @Test
    public  void fun03(){
//        setSexFromIdCardNo("410327188510154456",()->"hh",s -> System.out.println(s));
//        setSexFromIdCardNo("410327188510154456", () -> "hh", new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                s.toLowerCase();
//            }
//        });

        setSexFromIdCardNo("410327188510154456",()->"hh",s -> s.toLowerCase());

        MyComparator myComparator = new MyComparator();
        setSexFromIdCardNo("410327188510154456",myComparator::getSex,myComparator::setSex);
        System.out.println(myComparator.getSex());  // 打印 M
    }


}
