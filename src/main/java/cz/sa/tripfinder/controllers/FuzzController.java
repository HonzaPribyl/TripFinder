package cz.sa.tripfinder.controllers;

import cz.sa.tripfinder.model.TripFuzzyDTO;
import cz.sa.tripfinder.services.FuzzySearchService;
import cz.sa.tripfinder.services.JFuzzService;
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
            @RequestParam int limit,
            @RequestParam(required = false) final List<Long> highPrefAirports,
            @RequestParam(required = false) final List<Long> prefAirports,
            @RequestParam(required = false) final List<Long> highPrefLocs,
            @RequestParam(required = false) final List<Long> prefLocs,
            @RequestParam Integer allInclusivePref,
            @RequestParam Integer fullBoardPref,
            @RequestParam Integer halfBoardPref,
            @RequestParam Integer breakfastPref,
            @RequestParam Integer noFoodPref,
            @RequestParam Integer minFoodPref,
            @RequestParam Integer minStars,
            @RequestParam Integer minRating,
            @RequestParam Integer minBeachDist,
            @RequestParam Boolean filterAirports,
            @RequestParam Boolean filterLocs,
            @RequestParam Boolean familyFilter,
            @RequestParam Boolean wifiFilter,
            @RequestParam Boolean poolFilter,
            @RequestParam Boolean hotelDistinct,
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
                limit,
                highPrefLocs,
                prefLocs,
                highPrefAirports,
                prefAirports,
                allInclusivePref,
                fullBoardPref,
                halfBoardPref,
                breakfastPref,
                noFoodPref,
                minFoodPref,
                minStars,
                minRating,
                minBeachDist,
                filterAirports,
                filterLocs,
                familyFilter,
                wifiFilter,
                poolFilter,
                hotelDistinct);
        model.addAttribute("trips", trips);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("allInclusivePref", allInclusivePref);
        model.addAttribute("fullBoardPref", fullBoardPref);
        model.addAttribute("halfBoardPref", halfBoardPref);
        model.addAttribute("breakfastPref", breakfastPref);
        model.addAttribute("noFoodPref", noFoodPref);
        model.addAttribute("minFoodPref", minFoodPref);
        model.addAttribute("highPrefAirports", highPrefAirports);
        model.addAttribute("prefAirports", prefAirports);
        model.addAttribute("persons", persons);
        model.addAttribute("limit", limit);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("minDays", minDays);
        model.addAttribute("maxDays", maxDays);
        model.addAttribute("filterAirports", filterAirports);
        return "tripsFuzzy";
    }

    @GetMapping("/searchFuzzByHotel")
    public String searchFuzzByHotel(
            @RequestParam float maxPrice,
            @RequestParam float minPrice,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam int minDays,
            @RequestParam int maxDays,
            @RequestParam int persons,
            @RequestParam int limit,
            @RequestParam(required = false) final List<Long> highPrefAirports,
            @RequestParam(required = false) final List<Long> prefAirports,
            @RequestParam Integer allInclusivePref,
            @RequestParam Integer fullBoardPref,
            @RequestParam Integer halfBoardPref,
            @RequestParam Integer breakfastPref,
            @RequestParam Integer noFoodPref,
            @RequestParam Integer minFoodPref,
            @RequestParam Boolean filterAirports,
            @RequestParam long hotel,
            Model model
    ) {
        List<TripFuzzyDTO> trips = fuzzySearchService.searchFuzzyTripsByHotel(
                maxPrice,
                minPrice,
                from,
                to,
                minDays,
                maxDays,
                persons,
                limit,
                highPrefAirports,
                prefAirports,
                allInclusivePref,
                fullBoardPref,
                halfBoardPref,
                breakfastPref,
                noFoodPref,
                minFoodPref,
                filterAirports,
                hotel);
        model.addAttribute("trips", trips);
        return "tripsFuzzy";
    }
}
