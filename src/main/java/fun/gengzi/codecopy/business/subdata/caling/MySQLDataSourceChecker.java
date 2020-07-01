package fun.gengzi.codecopy.business.subdata.caling;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class MySQLDataSourceChecker {

    private static final String SHOW_GRANTS_SQL = "SHOW GRANTS";

    private static final String[][] REQUIRED_PRIVILEGES = {{"ALL PRIVILEGES", "ON *.*"}, {"REPLICATION SLAVE", "REPLICATION CLIENT", "ON *.*"}};

    private static final String SHOW_VARIABLES_SQL = "SHOW VARIABLES LIKE '%s'";

    private static final Map<String, String> REQUIRED_VARIABLES = new HashMap(2);

    static {
        REQUIRED_VARIABLES.put("LOG_BIN", "ON");
        REQUIRED_VARIABLES.put("BINLOG_FORMAT", "ROW");
    }

    public void checkPrivilege(final Collection<? extends DataSource> dataSources) {
        for (DataSource each : dataSources) {
            checkPrivilege(each);
        }
    }

    private void checkPrivilege(final DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SHOW_GRANTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String privilege = resultSet.getString(1).toUpperCase();
                if (matchPrivileges(privilege)) {
                    return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Source datasource check privileges failed.");
        }
        throw new RuntimeException("Source datasource is lack of REPLICATION SLAVE, REPLICATION CLIENT ON *.* privileges.");
    }

    private boolean matchPrivileges(final String privilege) {
        return Arrays.stream(REQUIRED_PRIVILEGES)
                .anyMatch(requiredPrivileges -> Arrays.stream(requiredPrivileges).allMatch(required -> privilege.contains(required)));
    }

    public void checkVariable(final Collection<? extends DataSource> dataSources) {
        for (DataSource each : dataSources) {
            checkVariable(each);
        }
    }

    private void checkVariable(final DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            for (Map.Entry<String, String> entry : REQUIRED_VARIABLES.entrySet()) {
                checkVariable(connection, entry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Source datasource check variables failed.");
        }
    }

    private void checkVariable(final Connection connection, final Map.Entry<String, String> entry) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(SHOW_VARIABLES_SQL, entry.getKey()));
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            String value = resultSet.getString(2);
            if (!entry.getValue().equalsIgnoreCase(value)) {
                throw new RuntimeException(String.format("Source datasource required %s = %s, now is %s", entry.getKey(), entry.getValue(), value));
            }
        }
    }




}