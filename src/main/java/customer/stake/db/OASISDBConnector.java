package customer.stake.db;

import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OASISDBConnector {

    private JDBCConnectionPool jdbcConnectionPool;
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public OASISDBConnector() {
        jdbcConnectionPool = new JDBCConnectionPool(
                "com.mysql.cj.jdbc.Driver", "jdbc:mysql://" + envConfig.oasisDbHost() + "/" +
                envConfig.oasisDbSchema(),
                envConfig.CSSDbClient(), envConfig.CSSDbPassword());
    }

    public ResultSet executeDmlStatement(String query) {
        Connection connection = jdbcConnectionPool.takeOut();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcConnectionPool.takeIn(connection);
        return null;
    }
}
