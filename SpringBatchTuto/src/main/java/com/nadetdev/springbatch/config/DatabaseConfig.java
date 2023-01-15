package com.nadetdev.springbatch.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DatabaseConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.univertsitydatasource")
	public DataSource univertsityDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.postgresdatasource")
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	
	@Bean
	public EntityManagerFactory postgresEntityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean postgresLem = new LocalContainerEntityManagerFactoryBean();
		postgresLem.setDataSource(postgresDataSource());
		postgresLem.setPackagesToScan("com.nadetdev.springbatch.entity.postgres");
		postgresLem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		postgresLem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		postgresLem.afterPropertiesSet();
		
		return postgresLem.getObject();
	}
	
	@Bean
	public EntityManagerFactory mysqlEntityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean mySqlLem = new LocalContainerEntityManagerFactoryBean();
		mySqlLem.setDataSource(univertsityDataSource());
		mySqlLem.setPackagesToScan("com.nadetdev.springbatch.entity.mysql");
		mySqlLem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		mySqlLem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		mySqlLem.afterPropertiesSet();
		
		return mySqlLem.getObject();
	}
	
	@Bean
	@Primary
	public JpaTransactionManager jpaTransactionManager() {
		
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		
		jpaTransactionManager.setDataSource(univertsityDataSource());
		jpaTransactionManager.setEntityManagerFactory(mysqlEntityManagerFactory());
		
		return jpaTransactionManager;
	}


}
