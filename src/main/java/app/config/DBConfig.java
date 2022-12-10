package app.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = "app")
@PropertySource("classpath:db.properties")
public class DBConfig {
    private final Environment en;

    public DBConfig(Environment en) {
        this.en = en;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource managerDataSource = new DriverManagerDataSource();

        managerDataSource.setDriverClassName(Objects.requireNonNull(en.getProperty("db.driver")));
        managerDataSource.setUrl(en.getProperty("db.url"));
        managerDataSource.setUsername(en.getProperty("db.username"));
        managerDataSource.setPassword(en.getProperty("db.password"));

        return managerDataSource;
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();

        properties.put("hibernate.show_sql", en.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", en.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", en.getProperty("hibernate.dialect"));

        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerBean() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

        entityManager.setDataSource(dataSource());
        entityManager.setJpaProperties(properties());
        entityManager.setPackagesToScan(en.getProperty("hibernate.package"));
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return entityManager;
    }

    @Bean
    public EntityManager entityManager(@NotNull EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();

        manager.setDataSource(dataSource());
        manager.setEntityManagerFactory(entityManagerBean().getObject());

        return manager;
    }
}
