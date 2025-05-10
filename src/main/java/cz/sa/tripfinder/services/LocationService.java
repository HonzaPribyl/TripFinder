package cz.sa.tripfinder.services;

import cz.sa.tripfinder.mappers.LocationMapper;
import cz.sa.tripfinder.model.LocationDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LocationService {

    LocationMapper locationMapper;

    public List<LocationDTO> getLocations() {
        return locationMapper.getLocations();
    }
}
