package com.example.tripfinder.services;

import com.example.tripfinder.mappers.LocationMapper;
import com.example.tripfinder.model.LocationDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LocationService {

    LocationMapper locationMapper;

    public List<LocationDTO> getLocations() {
        return locationMapper.getLocations();
    }
}
