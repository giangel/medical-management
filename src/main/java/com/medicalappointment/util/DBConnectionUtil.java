// File: src/com/medicalappointment/util/DBConnectionUtil.java
package com.medicalappointment.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Central point for obtaining a JDBC connection. Configuration is read once
 * from db.properties on the classpath (WEB-INF/classes/db.properties after
 * build). To change database settings, edit that file only, nothing else in
 * the codebase needs to change.
 */
public final class DBConnectionUtil {

    private static String url;
    private static String username;
    private static String password;

    static {
        Properties props = new Properties();
        try (InputStream input = DBConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IllegalStateException(
                    "db.properties was not found on the classpath. Place it directly in the src folder root.");
            }
            props.load(input);
            String driver = props.getProperty("db.driver");
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            Class.forName(driver);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to initialize DBConnectionUtil: " + e.getMessage());
        }
    }

    private DBConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}