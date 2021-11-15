package customer.stake.db;

import java.sql.Connection;

public class JDBCConnectionHandler implements AutoCloseable {

    private final JDBCConnectionPool jdbcConnectionPool;
    private final Connection connection;

    public JDBCConnectionHandler(JDBCConnectionPool jdbcConnectionPool) {
        this.jdbcConnectionPool = jdbcConnectionPool;
        connection = open();
    }

    private Connection open() {
        return jdbcConnectionPool.takeOut();
    }

    /**
     * It is called automatically when leaving a try block
     */
    @Override
    public void close() {
        jdbcConnectionPool.takeIn(connection);
    }

    public Connection getConnection() {
        return connection;
    }
}
