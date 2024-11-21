package com.example.tripfinder.services;

import com.example.tripfinder.mappers.AirportMapper;
import com.example.tripfinder.model.AirportDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AirportService {

    AirportMapper airportMapper;

    public List<AirportDTO> getAirports() {
        return airportMapper.getAirports();
    }
}
