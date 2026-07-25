// File: src/com/medicalappointment/util/DBConnectionUtil.java
package com.medicalappointment.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Central point for obtaining a JDBC connection.
 *
 * Configuration priority:
 *   1. Environment variables: DB_URL, DB_USERNAME, DB_PASSWORD, DB_DRIVER
 *   2. db.properties on the classpath (local dev fallback only — do not put
 *      real production credentials in this file / in git)
 *
 * On Render, set DB_URL / DB_USERNAME / DB_PASSWORD as environment variables
 * pointing at your Neon connection string.
 */
public final class DBConnectionUtil {

    private static String url;
    private static String username;
    private static String password;

    static {
        Properties props = new Properties();
        try (InputStream input = DBConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (Exception e) {
            // db.properties is optional when env vars are supplied; ignore.
        }

        String driver = System.getenv().getOrDefault("DB_DRIVER", props.getProperty("db.driver", "org.postgresql.Driver"));
        url = firstNonNull(System.getenv("DB_URL"), props.getProperty("db.url"));
        username = firstNonNull(System.getenv("DB_USERNAME"), props.getProperty("db.username"));
        password = firstNonNull(System.getenv("DB_PASSWORD"), props.getProperty("db.password"));

        if (url == null || username == null || password == null) {
            throw new IllegalStateException(
                "Database configuration missing. Set DB_URL, DB_USERNAME, DB_PASSWORD environment " +
                "variables (recommended for Render), or provide src/db.properties for local dev.");
        }

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Failed to load DB driver '" + driver + "': " + e.getMessage());
        }
    }

    private static String firstNonNull(String a, String b) {
        return (a != null && !a.isBlank()) ? a : b;
    }

    private DBConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}