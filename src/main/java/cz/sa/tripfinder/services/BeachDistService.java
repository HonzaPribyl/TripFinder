package cz.sa.tripfinder.services;

import cz.sa.tripfinder.mappers.BeachDistMapper;
import cz.sa.tripfinder.model.BeachDistDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BeachDistService {

    BeachDistMapper beachDistMapper;

    public List<BeachDistDTO> getBeachDistances() {
        return beachDistMapper.getBeachDistances();
    }
}
