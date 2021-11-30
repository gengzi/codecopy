package fun.gengzi.codecopy.business.subdata.entity;

import org.junit.Test;

public class BussinessTableTest {

    @Test
    public void fun01(){
        BussinessTable bussinessTable = new BussinessTable();
        String string = bussinessTable.toString();
        System.out.println(string);
    }

}