package org.aston.task.db;

import org.aston.task.exceptions.NotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private void runningScripts(Connection connection) throws IOException {
        String sql = new String(Files.readAllBytes(Paths.get("schema.sql")));
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
