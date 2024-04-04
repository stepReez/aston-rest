package org.aston.task.db;

import java.sql.*;

public class ConnectionManagerImpl implements ConnectionManager {

    Connection connection;

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
            String connectionString = "jdbc:postgresql://db:5432/aston";
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(connectionString, "postgres", "password");
        return connection;
    }
}
