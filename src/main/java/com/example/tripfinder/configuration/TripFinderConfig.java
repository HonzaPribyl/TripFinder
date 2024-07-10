package com.example.tripfinder.configuration;

import com.example.tripfinder.mappers.AirportMapper;
import com.example.tripfinder.mappers.TripMapper;
import com.example.tripfinder.services.TripService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.mapper.MapperFactoryBean;

import javax.sql.DataSource;

@Configuration
public class TripFinderConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:test;MODE=PostgreSQL;INIT=RUNSCRIPT FROM 'src/main/resources/schema.sql'")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public MapperFactoryBean<TripMapper> tripMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<TripMapper> mapperFactoryBean = new MapperFactoryBean<>(TripMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapperFactoryBean;
    }

    @Bean
    public MapperFactoryBean<AirportMapper> airportMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<AirportMapper> mapperFactoryBean = new MapperFactoryBean<>(AirportMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapperFactoryBean;
    }

    @Bean
    public TripService tripService(TripMapper tripMapper, AirportMapper airportMapper) {
        return new TripService(tripMapper, airportMapper);
    }

}
