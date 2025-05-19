package cz.sa.tripfinder.mappers;

import cz.sa.tripfinder.model.LocationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LocationMapper {

    @Select("SELECT id, name FROM locations")
    List<LocationDTO> getLocations();
}
