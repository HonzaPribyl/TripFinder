package com.example.tripfinder.services;

import com.example.tripfinder.mappers.BeachDistMapper;
import com.example.tripfinder.model.BeachDistDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BeachDistService {

    BeachDistMapper beachDistMapper;

    public List<BeachDistDTO> getBeachDistances() {
        return beachDistMapper.getBeachDistances();
    }
}
