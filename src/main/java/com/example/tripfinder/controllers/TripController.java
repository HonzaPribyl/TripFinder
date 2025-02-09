package com.example.tripfinder.controllers;

import com.example.tripfinder.model.TripByHotelDTO;
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
            @RequestParam float minPrice,
            @RequestParam String priceImp,
            @RequestParam String starsImp,
            @RequestParam String allInclusivePref,
            @RequestParam String fullBoardPref,
            @RequestParam String halfBoardPref,
            @RequestParam String breakfastPref,
            @RequestParam String noFoodPref,
            @RequestParam(required = false) final List<Long> highPrefAirports,
            @RequestParam(required = false) final List<Long> prefAirports,
            @RequestParam(required = false) final List<Long> highPrefLocs,
            @RequestParam(required = false) final List<Long> prefLocs,
            @RequestParam String locImp,
            @RequestParam String foodImp,
            @RequestParam String ratingImp,
            @RequestParam String airportImp,
            @RequestParam String beachDistImp,
            @RequestParam Long minBeachDist,
            @RequestParam String familyImp,
            @RequestParam String wifiImp,
            @RequestParam String poolImp,
            @RequestParam int persons,
            @RequestParam int limit,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam int minDays,
            @RequestParam int maxDays,
            @RequestParam boolean filterLocs,
            @RequestParam boolean hotelDistinct
            ) {
        return tripService.search(
                maxPrice,
                minPrice,
                priceImp,
                starsImp,
                allInclusivePref,
                fullBoardPref,
                halfBoardPref,
                breakfastPref,
                noFoodPref,
                highPrefAirports,
                prefAirports,
                highPrefLocs,
                prefLocs,
                locImp,
                foodImp,
                ratingImp,
                airportImp,
                beachDistImp,
                minBeachDist,
                familyImp,
                wifiImp,
                poolImp,
                persons,
                limit,
                from,
                to,
                minDays,
                maxDays,
                filterLocs,
                hotelDistinct);
    }

    @GetMapping("/searchByHotel")
    public List<TripByHotelDTO> searchByHotel(
            @RequestParam float maxPrice,
            @RequestParam String priceImp,
            @RequestParam String allInclusivePref,
            @RequestParam String fullBoardPref,
            @RequestParam String halfBoardPref,
            @RequestParam String breakfastPref,
            @RequestParam String noFoodPref,
            @RequestParam(required = false) final List<Long> highPrefAirports,
            @RequestParam(required = false) final List<Long> prefAirports,
            @RequestParam String foodImp,
            @RequestParam String airportImp,
            @RequestParam int persons,
            @RequestParam int limit,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam int minDays,
            @RequestParam int maxDays,
            @RequestParam Long hotel
    ) {
        return tripService.searchByHotel(
                maxPrice,
                priceImp,
                allInclusivePref,
                fullBoardPref,
                halfBoardPref,
                breakfastPref,
                noFoodPref,
                highPrefAirports,
                prefAirports,
                foodImp,
                airportImp,
                persons,
                limit,
                from,
                to,
                minDays,
                maxDays,
                hotel
        );
    }

}
