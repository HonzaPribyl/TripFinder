package com.example.tripfinder.mappers;

import jakarta.annotation.Nonnull;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AirportMapper {

    @Delete("TRUNCATE TABLE airport_prefs")
    void clearAirports();

    @Insert("INSERT INTO airport_prefs (airport, high_pref) VALUES (#{airport}, #{highPref})")
    void addAirportPref(@Nonnull Long airport, boolean highPref);
}
