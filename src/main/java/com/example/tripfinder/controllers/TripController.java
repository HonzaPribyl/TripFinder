package com.example.tripfinder.controllers;

import com.example.tripfinder.model.TripByHotelDTO;
import com.example.tripfinder.model.TripDTO;
import com.example.tripfinder.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/search")
    public String search(
            @RequestParam float maxPrice,
            @RequestParam float minPrice,
            @RequestParam String priceImp,
            @RequestParam String starsImp,
            @RequestParam int minStars,
            @RequestParam int minRating,
            @RequestParam String allInclusivePref,
            @RequestParam String fullBoardPref,
            @RequestParam String halfBoardPref,
            @RequestParam String breakfastPref,
            @RequestParam String noFoodPref,
            @RequestParam String minFoodPref,
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
            @RequestParam boolean filterAirports,
            @RequestParam boolean filterLocs,
            @RequestParam boolean hotelDistinct,
            Model model
            ) {
        List<TripDTO> trips = tripService.search(
                maxPrice,
                minPrice,
                priceImp,
                starsImp,
                minStars,
                minRating,
                allInclusivePref,
                fullBoardPref,
                halfBoardPref,
                breakfastPref,
                noFoodPref,
                minFoodPref,
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
                filterAirports,
                filterLocs,
                hotelDistinct);
        model.addAttribute("trips", trips);
        return "trips";
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
