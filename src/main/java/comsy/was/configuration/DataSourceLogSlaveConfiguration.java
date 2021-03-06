package comsy.was.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "comsy.was.data.repositoryLogSlave",
        entityManagerFactoryRef = "logSlaveEntityManagerFactory",
        transactionManagerRef = "logSlaveTransactionManager"
)
@RequiredArgsConstructor
public class DataSourceLogSlaveConfiguration {
    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.log.slave")
    public DataSource logSlaveDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean logSlaveEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        var properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(logSlaveDataSource())
                .properties(properties)
                .packages("comsy.was.data.domain.log")  // ????????? ??????
                .persistenceUnit("logSlaveEntityManager")
                .build();
    }

    @Bean
    PlatformTransactionManager logSlaveTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(logSlaveEntityManagerFactory(builder).getObject()));
    }
}
