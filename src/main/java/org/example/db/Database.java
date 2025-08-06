package org.example.db;

import org.flywaydb.core.Flyway;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Connection connection;

    public static void init() {
        try {
            Path appDir = Path.of(System.getProperty("user.home"), ".ocr_manager");
            Files.createDirectories(appDir);
            String url = "jdbc:sqlite:" + appDir.resolve("app.db");

            Flyway.configure()
                    .dataSource(url, null, null)
                    .locations("classpath:db/migration")
                    .load()
                    .migrate();

            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            throw new RuntimeException("DB init failed", e);
        }
    }

    public static Connection get() { return connection; }
}
