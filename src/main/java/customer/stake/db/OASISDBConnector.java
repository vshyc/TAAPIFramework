package customer.stake.db;

import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;

public class OASISDBConnector extends DBConnector{

    private static JDBCConnectionPool jdbcConnectionPool;
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public OASISDBConnector() {
        jdbcConnectionPool = new JDBCConnectionPool(
                "com.mysql.cj.jdbc.Driver", "jdbc:mysql://" + envConfig.oasisDbHost() + "/" +
                envConfig.oasisDbSchema() + "?serverTimezone=UTC",
                envConfig.serviceDbClient(), envConfig.serviceDbPassword());
    }

    public static JDBCConnectionPool getJdbcConnectionPool() {
        return jdbcConnectionPool;
    }
}
