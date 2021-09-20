package customer.stake.db;

import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CSSDBConnector {
    private JDBCConnectionPool jdbcConnectionPool;
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public CSSDBConnector() {
        jdbcConnectionPool = new JDBCConnectionPool(
                "com.mysql.cj.jdbc.Driver", "jdbc:mysql://" + envConfig.CSSDbHost() + "/" +
                envConfig.CSSDbSchema() + "?serverTimezone=UTC",
                envConfig.CSSDbClient(), envConfig.CSSDbPassword());
    }

    public void executeDmlStatement(String query) {
        Connection connection = jdbcConnectionPool.takeOut();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcConnectionPool.takeIn(connection);
    }
}
