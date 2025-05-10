package cz.sa.tripfinder.services;

import cz.sa.tripfinder.mappers.HotelMapper;
import cz.sa.tripfinder.model.HotelDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HotelService {

    HotelMapper hotelMapper;

    public List<HotelDTO> getHotels() {
        return hotelMapper.getHotels();
    }
}
