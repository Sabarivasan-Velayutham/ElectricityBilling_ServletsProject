package com.db;

import java.sql.Connection;
import java.sql.DriverManager;

import jakarta.servlet.ServletContext;

public class DBConnect {

    public static Connection getConn(ServletContext servletContext) {
        Connection conn = null;
        String dbName = servletContext.getInitParameter("dbName");
        String username = servletContext.getInitParameter("dbUsername");
        String password = servletContext.getInitParameter("dbPassword");

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}