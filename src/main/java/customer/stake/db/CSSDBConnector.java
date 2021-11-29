package customer.stake.db;

import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;

public class CSSDBConnector {
    private static JDBCConnectionPool jdbcConnectionPool;
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public CSSDBConnector() {
        jdbcConnectionPool = new JDBCConnectionPool(
                "com.mysql.cj.jdbc.Driver", "jdbc:mysql://" + envConfig.CSSDbHost() + "/" +
                envConfig.CSSDbSchema() + "?serverTimezone=UTC",
                envConfig.serviceDbClient(), envConfig.serviceDbPassword());
    }
}
