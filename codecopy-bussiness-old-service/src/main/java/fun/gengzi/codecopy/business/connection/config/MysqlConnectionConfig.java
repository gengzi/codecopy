package fun.gengzi.codecopy.business.connection.config;

import cn.hutool.core.net.NetUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.DruidDriver;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.alibaba.druid.support.console.DruidStat;
import com.alibaba.druid.support.jconsole.DruidDriverPanel;
import com.alibaba.druid.util.DruidDataSourceUtils;
import com.alibaba.druid.util.DruidWebUtils;
import fun.gengzi.codecopy.business.connection.entity.MysqlEntity;
import fun.gengzi.codecopy.business.subdata.strategy.complex.InFiledstodbComplexKeysShardingConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <h1>mysql 连接配置类</h1>
 * <p>
 * <p>
 * IP地址根据网络号和主机号来分，分为A、B、C三类及特殊地址D、E。    全0和全1的都保留不用。
 * <p>
 * A类：(1.0.0.0-126.0.0.0)（默认子网掩码：255.0.0.0或 0xFF000000）第一个字节为网络号，后三个字节为主机号。该类IP地址的最前面为“0”，所以地址的网络号取值于1~126之间。一般用于大型网络。
 * <p>
 * B类：(128.0.0.0-191.255.0.0)（默认子网掩码：255.255.0.0或0xFFFF0000）前两个字节为网络号，后两个字节为主机号。该类IP地址的最前面为“10”，所以地址的网络号取值于128~191之间。一般用于中等规模网络。
 * <p>
 * C类：(192.0.0.0-223.255.255.0)（子网掩码：255.255.255.0或 0xFFFFFF00）前三个字节为网络号，最后一个字节为主机号。该类IP地址的最前面为“110”，所以地址的网络号取值于192~223之间。一般用于小型网络。
 * <p>
 * D类：是多播地址。该类IP地址的最前面为“1110”，所以地址的网络号取值于224~239之间。一般用于多路广播用户[1]  。
 * <p>
 * E类：是保留地址。该类IP地址的最前面为“1111”，所以地址的网络号取值于240~255之间。
 * <p>
 * <p>
 * <p>
 * 在IP地址3种主要类型里，各保留了3个区域作为私有地址，其地址范围如下：
 * A类地址：10.0.0.0～10.255.255.255
 * B类地址：172.16.0.0～172.31.255.255
 * C类地址：192.168.0.0～192.168.255.255
 * <p>
 * 回送地址：127.0.0.1。 也是本机地址，等效于localhost或本机IP。一般用于测试使用。例如：ping 127.0.0.1来测试本机TCP/IP是否正常。
 *
 * 分库分表实践
 *
 * 123.0.xxx.xxx
 * 123.255.xxx.xxx
 *
 *
 *
 *
 *
 * @author gengzi
 * @date 2020年7月13日17:17:24
 */
public class MysqlConnectionConfig {

    private Logger logger = LoggerFactory.getLogger(MysqlConnectionConfig.class);

    public static void main(String[] args) throws SQLException, ClassNotFoundException {


        // IP 范围 排除无用的ip列表
        // 可能的用户名 密码 匹配，常用端口匹配
        // 多线程数据库连接 检测
        // 先 telnet 检测，ip 端口是否可访问，再检测 用户 密码 能否 登陆，创建线程池，模拟连接。
        // 将可以使用的mysql 库信息，存入数据库
        // 整体迁移表，到另外的数据库


//        DruidDataSource druidDataSource = new DruidDataSource();
//        DruidDriverPanel druidDriverPanel = new DruidDriverPanel();
//        DriverManager.getConnection()

//        DruidDriver instance = DruidDriver.getInstance();
//        Driver driver = instance.createDriver("com.mysql.jdbc.Driver");


        for (int i = 0; i < 255; i++) {
            try {
                MysqlEntity mysqlEntity = new MysqlEntity();
                // 假设限定 ip "218.245"  个数 255 * 255
                final String ipaddress = "123.206.28." + i;
                boolean open = NetUtil.isOpen(new InetSocketAddress(ipaddress, 3306), 1000);
                if (!open) {
                    System.out.println("Not available " + ipaddress);
                    continue;
                }
                mysqlEntity.setIp(ipaddress);
                mysqlEntity.setPort("3306");
                mysqlEntity.setUsername("root");
                mysqlEntity.setUsername("root");
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://" + mysqlEntity.getIp() + ":3306?serverTimezone=Asia/Shanghai", "root", "root");
                System.out.println("success :" + ipaddress);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;

//            e.printStackTrace();

            }
        }


//        Connection connect = driver.connect("jdbc:mysql://localhost", new Properties());
//        Properties properties = new Properties();
//        properties.setProperty("username","root");
//        properties.setProperty("password","111");
//        Connection connect = driver.connect("jdbc:mysql://localhost:3306", properties);

//
//        boolean b = driver.acceptsURL("");


    }


}
