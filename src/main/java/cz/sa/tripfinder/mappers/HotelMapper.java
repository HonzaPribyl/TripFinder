package cz.sa.tripfinder.mappers;

import cz.sa.tripfinder.model.HotelDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HotelMapper {

    @Select("SELECT id, name FROM hotels")
    List<HotelDTO> getHotels();
}
