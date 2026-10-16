package com.anivo4.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    private static final String CONFIG_FILE = "application.properties";

    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            Properties props = loadProperties();

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.user"));
            config.setPassword(props.getProperty("db.password"));
            config.setMaximumPoolSize(
                    Integer.parseInt(props.getProperty("db.pool.size", "10"))
            );
            config.setPoolName(props.getProperty("db.pool.name", "shop-pool"));

            dataSource = new HikariDataSource(config);
            log.info("HikariDataSource initialized");
        }
        return dataSource;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();

        try (InputStream in = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (in == null) {
                throw new IllegalStateException(CONFIG_FILE + " not found in classpath");
            }
            props.load(in);
        } catch (IOException e) {
            log.error("Failed to load " + CONFIG_FILE, e);
            throw new RuntimeException(e);
        }

        return props;
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