package customer.stake.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class JDBCConnectionPool extends ObjectsPool<Connection> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCConnectionPool.class);
    private String host;
    private String user;
    private String password;

    protected JDBCConnectionPool(String driver, String host, String user, String password) {
        super();
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        this.host = host;
        this.user = user;
        this.password = password;
    }

    @Override
    protected Connection create() {
        try {
            return (DriverManager.getConnection(host, user, password));
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            return (null);
        }
    }

    @Override
    protected void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    protected boolean validate(Connection connection) {
        try {
            return (!connection.isClosed());
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            return (false);
        }
    }
}
