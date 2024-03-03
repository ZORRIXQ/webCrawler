package com.zorrix.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//this class is connecting program to db
public class ConnectionService {
    private static final String jdbcUrl = "jdbc:postgresql://localhost:11111/postgres";
    private static final String username = "postgres";
    private static final String password = "password))";
    private Connection connection;

    private ConnectionService(){}

    static public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
