package co.com.bancolombia.r2dbc.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class PostgreSQLConnectionPool {
    /* Change these values for your project */
    public static final int INITIAL_SIZE = 12;
    public static final int MAX_SIZE = 15;
    public static final int MAX_IDLE_TIME = 30;
    public static final int DEFAULT_PORT = 5432;

	@Bean
	public ConnectionPool getConnectionConfig(PostgresqlConnectionProperties properties) {
		// Construir las opciones de conexión R2DBC
		ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
				.option(ConnectionFactoryOptions.DRIVER, "postgresql")
				.option(ConnectionFactoryOptions.HOST, properties.host())
				.option(ConnectionFactoryOptions.PORT, properties.port())
				.option(ConnectionFactoryOptions.DATABASE, properties.database())
				.option(ConnectionFactoryOptions.USER, properties.username())
				.option(ConnectionFactoryOptions.PASSWORD, properties.password())
				.option(ConnectionFactoryOptions.SSL, true)
				.build();

		// Crear la ConnectionFactory usando las opciones
		ConnectionFactory connectionFactory = ConnectionFactories.get(options);

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(connectionFactory)
                .name("api-postgres-connection-pool")
                .initialSize(INITIAL_SIZE)
                .maxSize(MAX_SIZE)
                .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
                .validationQuery("SELECT 1")
                .build();

		return new ConnectionPool(poolConfiguration);
	}
}