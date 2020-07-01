package fun.gengzi.codecopy.business.subdata.caling;

import com.alibaba.druid.pool.DruidDataSource;
import fun.gengzi.codecopy.business.shorturl.service.impl.ShortUrlGeneratorServiceImplTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySQLDataSourceCheckerTest {
    private Logger logger = LoggerFactory.getLogger(ShortUrlGeneratorServiceImplTest.class);

    private static final String HOST = "localhost";

    private static final int PORT = 3306;

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "111";


    @Test
    public void fun() {
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName("com.mysql.jdbc.Driver");
        result.setUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=UTF-8", HOST, PORT, "ds_data"));
        result.setUsername(USER_NAME);
        result.setPassword(PASSWORD);

        MySQLDataSourceChecker mySQLDataSourceChecker = new MySQLDataSourceChecker();
        mySQLDataSourceChecker.checkPrivilege(Collections.singleton(result));
        mySQLDataSourceChecker.checkVariable(Collections.singleton(result));


    }

}