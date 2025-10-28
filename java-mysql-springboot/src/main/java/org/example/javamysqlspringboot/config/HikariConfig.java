package org.example.javamysqlspringboot.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class HikariConfig {

    private final DatabaseConfig databaseConfig;

    public HikariConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Bean
    @Primary
    public DataSource hikariDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}