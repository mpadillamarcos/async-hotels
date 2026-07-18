package mpadillamarcos.asynchotels.infra;

import org.springframework.boot.flyway.autoconfigure.FlywayAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureDataSourceInitialization;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@AutoConfigureDataSourceInitialization
@ContextConfiguration(classes = {
    FlywayAutoConfiguration.class,
    DataSourceAutoConfiguration.class
})
public class DbTestBase {

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:15-alpine");
}