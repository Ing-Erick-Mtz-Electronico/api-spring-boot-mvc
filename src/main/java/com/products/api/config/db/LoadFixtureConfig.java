package com.products.api.config.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LoadFixtureConfig {

    @Bean
    public CommandLineRunner loadData(JdbcTemplate jdbcTemplate) {
        return args -> {

            log.info("Starting to fixture data...");

            String basePath = "db/fixture/add_products.sql";

            String sql = this.readSqlFile(basePath);
            this.executeSql(jdbcTemplate, sql);

            log.info("Finished loading initial data successfully");
        };
    }

    private String readSqlFile(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        try {
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read SQL file: " + path, e);
        }
    }

    private void executeSql(JdbcTemplate jdbcTemplate, String sql) {
        for (String statement : sql.split(";")) {
            if (!statement.trim().isEmpty()) {
                jdbcTemplate.execute(statement);
            }
        }
    }
}
