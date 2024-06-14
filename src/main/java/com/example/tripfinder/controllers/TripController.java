package com.example.tripfinder.controllers;

import com.example.tripfinder.mappers.TripMapper;
import com.example.tripfinder.model.TripDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TripController {

    private final TripMapper tripMapper;

    public TripController(TripMapper tripMapper) {
        this.tripMapper = tripMapper;
    }

    @GetMapping("/")
    public List<TripDTO> getAllTrips()
    {
        return tripMapper.findAll();
    }

}
