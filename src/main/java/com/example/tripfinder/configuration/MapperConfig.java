package com.example.tripfinder.configuration;

import com.example.tripfinder.mappers.TripMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.mapper.MapperFactoryBean;

import javax.sql.DataSource;

@Configuration
public class MapperConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/tripfinderdb")
                .driverClassName("org.postgresql.Driver")
                .username("myuser")
                .password("1234")
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
}
