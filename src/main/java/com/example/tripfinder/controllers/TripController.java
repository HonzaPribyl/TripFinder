package com.example.tripfinder.controllers;

import com.example.tripfinder.model.TripDTO;
import com.example.tripfinder.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/search")
    public List<TripDTO> search(
            @RequestParam float maxPrice,
            @RequestParam String priceImp,
            @RequestParam String starsImp,
            @RequestParam String allInclusivePref,
            @RequestParam String fullBoardPref,
            @RequestParam String halfBoardPref,
            @RequestParam String breakfastPref,
            @RequestParam String noFoodPref,
            @RequestParam String foodImp,
            @RequestParam String beachDistImp,
            @RequestParam int persons,
            @RequestParam int limit,
            @RequestParam String from,
            @RequestParam String to
            ) {
        return tripService.search(
                maxPrice,
                priceImp,
                starsImp,
                allInclusivePref,
                fullBoardPref,
                halfBoardPref,
                breakfastPref,
                noFoodPref,
                foodImp,
                beachDistImp,
                persons,
                limit,
                from,
                to);
    }

}
