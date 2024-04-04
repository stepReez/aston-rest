package org.aston.task.db;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

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
