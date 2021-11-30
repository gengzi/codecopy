package fun.gengzi.codecopy.thread;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;


public class Test01 {

    @Test
    public void fun01(){

        LinkedTransferQueue linkedTransferQueue = new LinkedTransferQueue();

        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();

        String filename = "zhangsan_fdd_xxx";

        boolean matches = filename.matches("_");


        char[] chars = filename.toCharArray();

        String[] split = filename.split("_");
        if(split.length == 2){

        }




    }
}
