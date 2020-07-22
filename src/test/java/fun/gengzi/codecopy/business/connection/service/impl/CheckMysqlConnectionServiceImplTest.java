package fun.gengzi.codecopy.business.connection.service.impl;

import cn.hutool.core.net.NetUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckMysqlConnectionServiceImplTest {

    @Test
    public void fun01(){
        long l = NetUtil.ipv4ToLong("114.116.40.0"); //1677721600  1761607679
        System.out.println(l);

//        for (int i = 1677721600 ; i < 1761607679; i++) {
//            String s = NetUtil.longToIpv4(i);
//            System.out.println(s);
//        }

//
//
//        System.out.println(l);
    }

}