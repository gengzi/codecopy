package fun.gengzi.codecopy.business.subdata.sharding.proxy.service.impl;

import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.business.subdata.sharding.proxy.datasource.ShardingProxyDataSourceConfig;
import fun.gengzi.codecopy.business.subdata.sharding.proxy.service.ShardingProxyService;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ShardingProxyServiceImpl implements ShardingProxyService {


    /**
     * 插入一条数据
     *
     * @param bussinessTable 业务实体
     * @return {@link BussinessTable}
     */
    @Override
    public BussinessTable insertBussinessData(BussinessTable bussinessTable) {
        DataSource dataSource = ShardingProxyDataSourceConfig.createDataSource("sharding_db");
        String sql = "INSERT INTO t_bussiness (name) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bussinessTable.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bussinessTable;
    }
}
