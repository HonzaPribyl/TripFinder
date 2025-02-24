package com.example.tripfinder.controllers;

import com.example.tripfinder.model.TripFuzzEvaluationDTO;
import com.example.tripfinder.model.TripFuzzyDTO;
import com.example.tripfinder.services.FuzzySearchService;
import com.example.tripfinder.services.JFuzzService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FuzzController {

    private final JFuzzService jFuzzService;
    private final FuzzySearchService fuzzySearchService;

//    @GetMapping("/searchFuzz")
//    public TripFuzzEvaluationDTO searchFuzz() {
//        return jFuzzService.evaluate(1, 1, 1, 5, 1, 1, 1, 1, 1, false);
//    }

    @GetMapping("/searchFuzzByTrip")
    public TripFuzzyDTO searchFuzzByTrip(@RequestParam long trip) {
        return fuzzySearchService.getFuzzyTripById(trip);
    }

    @GetMapping("/searchFuzz")
    public List<TripFuzzyDTO> searchFuzz(
            @RequestParam String from,
            @RequestParam String to
    ) {
        return fuzzySearchService.searchFuzzyTrips(from, to);
    }
}
