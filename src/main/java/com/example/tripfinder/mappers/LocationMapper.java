package com.example.tripfinder.mappers;

import com.example.tripfinder.model.LocationDTO;
import jakarta.annotation.Nonnull;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LocationMapper {

    @Delete("TRUNCATE TABLE location_prefs")
    void clearLocations();

    @Insert("INSERT INTO location_prefs (location, high_pref) VALUES (#{airport}, #{highPref})")
    void addLocationPref(@Nonnull Long airport, boolean highPref);

    @Select("SELECT id, name FROM locations")
    List<LocationDTO> getLocations();
}
