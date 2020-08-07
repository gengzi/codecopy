package fun.gengzi.codecopy;

import cn.hutool.core.io.file.FileReader;

import java.io.*;

public class MyTest {

    public static void main(String[] args) throws FileNotFoundException {
        // 为 张三 进行装饰
//        Person person = new Person("张三");
//        // 准备一个裙子
//        Skirt skirt = new Skirt();
//        // 准备一个t
//        Tshirt tshirt = new Tshirt();
//
//        // 穿裙子到 张三
//        skirt.decorate(person);
//        // 在穿裙子的张三，再穿 t
//        tshirt.decorate(skirt);
//        // 现在的张三
//        tshirt.show();
//
//        Thread.State

//        Loser loser = new Loser();
//        RichWoman richWoman = new RichWoman(loser);
//        PlasticSurgery plasticSurgery = new PlasticSurgery(richWoman);
//        ManypeopleMotion manypeopleMotion = new ManypeopleMotion(plasticSurgery);
//        manypeopleMotion.fighting();
//        System.out.println("---我变成了一个高富帅---");

        PayBaseEntity payBaseEntity = new PayBaseEntity();
        payBaseEntity.setPayMethod("wxzf");
        payMoney(payBaseEntity);
    }

    public static String payMoney(PayBaseEntity pay) {
        PayFactory payFactory = new PayFactory(pay.getPayMethod());
        return payFactory.toPay(pay);
    }

    public void fun() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        // 装饰类
        Reader bufferedReader = new BufferedReader(inputStreamReader);
        LineNumberReader lineNumberReader = new LineNumberReader(bufferedReader);

    }


    public void fun03(){
//        FileReader fileReader = new FileReader("");
//        fileReader.readBytes();
//
//        java.io.FileReader
    }



}
