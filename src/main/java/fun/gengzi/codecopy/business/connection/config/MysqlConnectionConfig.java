package fun.gengzi.codecopy.business.connection.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.DruidDriver;
import com.alibaba.druid.support.jconsole.DruidDriverPanel;
import com.alibaba.druid.util.DruidDataSourceUtils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <h1>mysql 连接配置类</h1>
 *
 * @author gengzi
 * @date 2020年7月13日17:17:24
 */
public class MysqlConnectionConfig {


    public static void main(String[] args) throws SQLException {

        // IP 范围 排除无用的ip列表
        // 可能的用户名 密码 匹配，常用端口匹配
        // 多线程数据库连接 检测
        // 先 telnet 检测，ip 端口是否可访问，再检测 用户 密码 能否 登陆，创建线程池，模拟连接。
        // 将可以使用的mysql 库信息，存入数据库
        // 整体迁移表，到另外的数据库



//        DruidDataSource druidDataSource = new DruidDataSource();
//        DruidDriverPanel druidDriverPanel = new DruidDriverPanel();
//        DriverManager.getConnection()

        DruidDriver instance = DruidDriver.getInstance();
        Driver driver = instance.createDriver("com.mysql.jdbc.Driver");

//        Connection connect = driver.connect("jdbc:mysql://localhost", new Properties());
//        Properties properties = new Properties();
//        properties.setProperty("username","root");
//        properties.setProperty("password","111");
//        Connection connect = driver.connect("jdbc:mysql://localhost:3306", properties);


        boolean b = driver.acceptsURL("");



    }


}
