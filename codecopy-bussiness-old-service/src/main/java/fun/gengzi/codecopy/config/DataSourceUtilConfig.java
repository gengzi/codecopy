package fun.gengzi.codecopy.config;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * sharding jdbc datasource config
 *
 * https://github.com/geomonlin/incubator-shardingsphere-example/blob/4.0.0-RC2/example-core/example-api/src/main/java/org/apache/shardingsphere/example/core/api/DataSourceUtil.java
 *
 * @author gengzi
 * @date 2020年6月24日13:52:03
 */
public final class DataSourceUtilConfig {

    private static final String HOST = "localhost";

    private static final int PORT = 3306;

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