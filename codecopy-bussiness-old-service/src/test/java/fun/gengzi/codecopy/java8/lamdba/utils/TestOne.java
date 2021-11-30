package fun.gengzi.codecopy.java8.lamdba.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestOne {

    public static void main(String[] args) {
        MyPeraent myPeraent = new MyPeraent();
        MyPeraent.MySon mySon = new MyPeraent.MySon();

        mySon.setA("1");
        mySon.setA1(new ArrayList<>());

        myPeraent.setMySon(mySon);

        ArrayList<MyPeraent.MySon> mySons = new ArrayList<>();
        boolean add = mySons.add(myPeraent.getMySon());

        List<MyPeraent.MySon> collect = mySons.stream().filter(e -> true).collect(Collectors.toList());
        System.out.println(collect);
    }
}
