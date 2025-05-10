package cz.sa.tripfinder.mappers;

import cz.sa.tripfinder.model.AirportDTO;
import jakarta.annotation.Nonnull;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AirportMapper {

    @Delete("TRUNCATE TABLE airport_prefs")
    void clearAirports();

    @Insert("INSERT INTO airport_prefs (airport, high_pref) VALUES (#{airport}, #{highPref})")
    void addAirportPref(@Nonnull Long airport, boolean highPref);

    @Select("SELECT id, iata, name FROM airports")
    List<AirportDTO> getAirports();
}
