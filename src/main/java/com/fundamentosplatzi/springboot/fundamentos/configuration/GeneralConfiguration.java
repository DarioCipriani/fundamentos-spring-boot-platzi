package com.fundamentosplatzi.springboot.fundamentos.configuration;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithPropertiesImplement;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:connection.properties")
@EnableConfigurationProperties(UserPojo.class)
public class GeneralConfiguration {
    @Value("${value.name}") //Esta notacion nos permite llamar los valores o propiedades que inicializamos en el application.properties
    private String name;

    @Value("${value.apellido}")
    private String apellido;

    @Value("${value.random}")
    private String random;

    @Value("${jdbc.url}")
    private String jdbcUrl;  //en esta variable jdbcUrl voy a tener los valores del arcivo de propiedades

    @Value("${driver}")    //en esta variable driver voy a tener los valores del arcivo de propiedades
    private String driver;

    @Value("${username}")   //en esta variable username voy a tener los valores del arcivo de propiedades
    private String username;

    @Value("${password}")    //en esta variable password voy a tener los valores del arcivo de propiedades
    private String password;

    @Bean
    public MyBeanWithProperties function(){
        return new MyBeanWithPropertiesImplement(name, apellido);
    }

    /*
    * Es la configuracion o implementacion a nivel de BD
     */
    @Bean
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(jdbcUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
