package com.example.tripfinder.controllers;

import com.example.tripfinder.mappers.TripMapper;
import com.example.tripfinder.model.TripDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TripController {

    private final TripMapper tripMapper;

    public TripController(TripMapper tripMapper) {
        this.tripMapper = tripMapper;
    }

    @GetMapping("/search")
    public List<TripDTO> search(
            @RequestParam float maxPrice,
            @RequestParam float priceImp,
            @RequestParam int limit
    ) {
        return tripMapper.findAll(maxPrice, priceImp, limit);
    }

}
