package customer.stake.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBConnector {

    public ResultSet executeDmlStatement(String query, JDBCConnectionPool jdbcConnectionPool) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try (JDBCConnectionHandler handler = new JDBCConnectionHandler(jdbcConnectionPool)) {
            preparedStatement = handler.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;}
}
