package com.example.tripfinder.services;

import com.example.tripfinder.mappers.HotelMapper;
import com.example.tripfinder.model.HotelDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HotelService {

    HotelMapper hotelMapper;

    public List<HotelDTO> getHotels() {
        return hotelMapper.getHotels();
    }
}
