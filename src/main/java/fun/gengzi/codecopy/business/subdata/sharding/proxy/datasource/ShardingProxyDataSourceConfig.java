package fun.gengzi.codecopy.business.subdata.sharding.proxy.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <h1>操作由ShardingProxy构建的数据源</h1>
 *
 * @author gengzi
 * @date 2020年6月30日15:03:37
 */
public class ShardingProxyDataSourceConfig {

    private static final String HOST = "localhost";

    private static final int PORT = 3308;

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "111";

    /**
     * 创建一个datasource
     *
     * @param dataSourceName 库名称
     * @return {@link DataSource}
     */
    public static DataSource createDataSource(final String dataSourceName) {
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName("com.mysql.jdbc.Driver");
        result.setUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=UTF-8", HOST, PORT, dataSourceName));
        result.setUsername(USER_NAME);
        result.setPassword(PASSWORD);
        return result;
    }
}