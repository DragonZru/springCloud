package com.spring.web.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.spring.web.*.mapper")
@EnableTransactionManagement
public class MybatisConfig {

    // TODO why datasource not use HikariDataSource default.?? spring boot version already 3.X

    @Bean
    //@Primary
    //@ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create(properties.getClassLoader())
                .type(HikariDataSource.class)
                .driverClassName(properties.determineDriverClassName())
                .url(properties.determineUrl())
                .username(properties.determineUsername())
                .password(properties.determinePassword())
                .build();
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // 设置驼峰转下划线
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);

        //Mybatis的事物管理
        //factoryBean.setTransactionFactory(new ManagedTransactionFactory());

        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    //开启spring 事物处理功能 注意⚠️，这里的datasource必须和注册SqlSessionFactory 使用的dataSource 保持一致，即在同一个个数据源中开启事物
    //适用于单数据源的事物管理
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    //spring 可以设置多数据源的容器事物管理 使用JTA   java transaction api
}
