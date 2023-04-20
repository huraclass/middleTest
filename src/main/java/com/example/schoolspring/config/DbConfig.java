package com.example.schoolspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
//public class DbConfig {
//    @Bean(destroyMethod="close")
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.mariadb.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mariadb://localhost:3306/school");
//        dataSource.setUsername("root");
//        dataSource.setPassword("0206");
//        dataSource.setMaxIdle(5);
//        dataSource.setMinIdle(0);
//        dataSource.setDefaultAutoCommit(false);
//        return dataSource;
//    }
//
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
//}
