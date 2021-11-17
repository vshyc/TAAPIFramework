package customer.stake.db;

import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OASISDBConnector {

    private JDBCConnectionPool jdbcConnectionPool;
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public OASISDBConnector() {
        jdbcConnectionPool = new JDBCConnectionPool(
                "com.mysql.cj.jdbc.Driver", "jdbc:mysql://" + envConfig.oasisDbHost() + "/" +
                envConfig.oasisDbSchema() + "?serverTimezone=UTC",
                envConfig.serviceDbClient(), envConfig.serviceDbPassword());
    }

    public ResultSet executeDmlStatement(String query) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;

        try (JDBCConnectionHandler handler = new JDBCConnectionHandler(jdbcConnectionPool)) {
            preparedStatement = handler.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


}
