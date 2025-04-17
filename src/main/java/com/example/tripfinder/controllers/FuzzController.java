package com.example.tripfinder.controllers;

import com.example.tripfinder.model.TripFuzzyDTO;
import com.example.tripfinder.services.FuzzySearchService;
import com.example.tripfinder.services.JFuzzService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FuzzController {

    private final JFuzzService jFuzzService;
    private final FuzzySearchService fuzzySearchService;

    @GetMapping("/searchFuzzByTrip")
    public TripFuzzyDTO searchFuzzByTrip(@RequestParam long trip) {
        return fuzzySearchService.getFuzzyTripById(trip);
    }

    @GetMapping("/searchFuzz")
    public String searchFuzz(
            @RequestParam float maxPrice,
            @RequestParam float minPrice,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam int minDays,
            @RequestParam int maxDays,
            @RequestParam int persons,
            @RequestParam(required = false) final List<Long> highPrefAirports,
            @RequestParam(required = false) final List<Long> prefAirports,
            @RequestParam(required = false) final List<Long> highPrefLocs,
            @RequestParam(required = false) final List<Long> prefLocs,
            @RequestParam Integer allInclusivePref,
            @RequestParam Integer fullBoardPref,
            @RequestParam Integer halfBoardPref,
            @RequestParam Integer breakfastPref,
            @RequestParam Integer noFoodPref,
            @RequestParam Integer minStars,
            @RequestParam Integer minBeachDist,
            @RequestParam Boolean familyFilter,
            @RequestParam Boolean wifiFilter,
            @RequestParam Boolean poolFilter,
            Model model
    ) {
        List<TripFuzzyDTO> trips = fuzzySearchService.searchFuzzyTrips(
                maxPrice,
                minPrice,
                from,
                to,
                minDays,
                maxDays,
                persons,
                highPrefLocs,
                prefLocs,
                highPrefAirports,
                prefAirports,
                allInclusivePref,
                fullBoardPref,
                halfBoardPref,
                breakfastPref,
                noFoodPref,
                minStars,
                minBeachDist,
                familyFilter,
                wifiFilter,
                poolFilter);
        model.addAttribute("trips", trips);
        return "tripsFuzzy";
    }
}
