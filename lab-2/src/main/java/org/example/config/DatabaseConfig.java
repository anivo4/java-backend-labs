package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class DatabaseConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/shopdb";
    private static final String USER = "shopuser";
    private static final String PASSWORD = "shoppass";

    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(10);
            config.setPoolName("shop-pool");
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    public static void runMigrations() {
        Flyway flyway = Flyway.configure()
                .dataSource(getDataSource())
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();
    }

    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}