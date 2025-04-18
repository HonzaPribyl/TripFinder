package com.example.tripfinder.configuration;

import com.example.tripfinder.mappers.*;
import com.example.tripfinder.services.*;
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
    public MapperFactoryBean<LocationMapper> locationMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<LocationMapper> mapperFactoryBean = new MapperFactoryBean<>(LocationMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapperFactoryBean;
    }

    @Bean
    public MapperFactoryBean<HotelMapper> hotelMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<HotelMapper> mapperFactoryBean = new MapperFactoryBean<>(HotelMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapperFactoryBean;
    }

    @Bean
    public MapperFactoryBean<BeachDistMapper> beachDistMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<BeachDistMapper> mapperFactoryBean = new MapperFactoryBean<>(BeachDistMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapperFactoryBean;
    }

    @Bean
    public TripService tripService(
            TripMapper tripMapper,
            AirportMapper airportMapper) {
        return new TripService(tripMapper, airportMapper);
    }

    @Bean
    public AirportService airportService(
            AirportMapper airportMapper
    ) {
        return new AirportService(airportMapper);
    }

    @Bean
    public LocationService locationService(
            LocationMapper locationMapper
    ) {
        return new LocationService(locationMapper);
    }

    @Bean
    public HotelService hotelService(
            HotelMapper hotelMapper
    ) {
        return new HotelService(hotelMapper);
    }

    @Bean
    public BeachDistService beachDistService(
            BeachDistMapper beachDistMapper
    ) {
        return new BeachDistService(beachDistMapper);
    }

    @Bean
    public JFuzzService jFuzzService() {return new JFuzzService();}

    @Bean
    public FuzzySearchService fuzzySearchService(
            JFuzzService jFuzzService,
            TripMapper tripMapper) {
        return new FuzzySearchService(jFuzzService, tripMapper);
    }

}
