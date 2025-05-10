package cz.sa.tripfinder.services;

import cz.sa.tripfinder.mappers.AirportMapper;
import cz.sa.tripfinder.model.AirportDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AirportService {

    AirportMapper airportMapper;

    public List<AirportDTO> getAirports() {
        return airportMapper.getAirports();
    }
}
