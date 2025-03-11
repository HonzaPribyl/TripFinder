package com.example.tripfinder.services;

import com.example.tripfinder.mappers.TripMapper;
import com.example.tripfinder.model.TripFuzzEvaluationDTO;
import com.example.tripfinder.model.TripFuzzyDTO;
import com.example.tripfinder.model.TripPureDTO;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class FuzzySearchService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final JFuzzService jFuzzService;
    private final TripMapper tripMapper;

    private static final Map<Boolean, Integer> EQUIPMENT_MAP = Map.of(
            true, 1,
            false, 3
    );

    private static final Map<String, Integer> BEACH_DIST_MAP = Map.of(
            "Přímo u pláže", 5,
            "Do 5 min", 15,
            "Do 15 min", 25,
            "Více, než 15 min", 35
    );

    public TripFuzzyDTO getFuzzyTripById(Long tripId) {
        TripPureDTO pureTrip = tripMapper.getPureTripById(tripId);
        TripFuzzEvaluationDTO fuzzEvaluation = jFuzzService.evaluate(
                EQUIPMENT_MAP.get(pureTrip.isFamilyFriendly()),
                EQUIPMENT_MAP.get(pureTrip.isInternet()),
                EQUIPMENT_MAP.get(pureTrip.isSwimmingPool()),
                pureTrip.getStars(),
                pureTrip.getAverageRating(),
                1,
                BEACH_DIST_MAP.get(pureTrip.getBeachDist()),
                1,
                1,
                1,
                false
        );
        return new TripFuzzyDTO(pureTrip, fuzzEvaluation);
    }

    public List<TripFuzzyDTO> searchFuzzyTrips(
            float maxPrice,
            float minPrice,
            String from,
            String to,
            int minDays,
            int maxDays,
            int persons,
            List<Long> highPrefLocs,
            List<Long> prefLocs,
            List<Long> highPrefAirports,
            List<Long> prefAirports,
            Integer allInclusivePref,
            Integer fullBoardPref,
            Integer halfBoardPref,
            Integer breakfastPref,
            Integer noFoodPref,
            Integer minStars,
            Integer minBeachDist,
            Boolean familyFilter,
            Boolean wifiFilter,
            Boolean poolFilter
    ) {
        final LocalDate dateFrom = LocalDate.parse(from, DATE_TIME_FORMATTER);
        final LocalDate dateTo = LocalDate.parse(to, DATE_TIME_FORMATTER);
        if (highPrefLocs == null) highPrefLocs = new ArrayList<>();
        if (prefLocs == null) prefLocs = new ArrayList<>();
        if (highPrefAirports == null) highPrefAirports = new ArrayList<>();
        if (prefAirports == null) prefAirports = new ArrayList<>();
        Map<String, Integer> foodPackageMap = createFoodPackagesMap(
                allInclusivePref, fullBoardPref, halfBoardPref, breakfastPref, noFoodPref);
        List<TripPureDTO> pureTrips = tripMapper.getPureTrips(
                dateFrom,
                dateTo,
                minDays,
                maxDays,
                maxPrice,
                minPrice,
                persons,
                minStars,
                minBeachDist,
                familyFilter,
                wifiFilter,
                poolFilter);
        List<TripFuzzyDTO> trips = new ArrayList<>();
        for (TripPureDTO pureTrip : pureTrips) {
            TripFuzzEvaluationDTO fuzzEvaluation = jFuzzService.evaluate(
                    EQUIPMENT_MAP.get(pureTrip.isFamilyFriendly()),
                    EQUIPMENT_MAP.get(pureTrip.isInternet()),
                    EQUIPMENT_MAP.get(pureTrip.isSwimmingPool()),
                    pureTrip.getStars(),
                    pureTrip.getAverageRating(),
                    determinePref(pureTrip.getLocationId(), highPrefLocs, prefLocs),
                    BEACH_DIST_MAP.get(pureTrip.getBeachDist()),
                    determinePref(pureTrip.getAirportId(), highPrefAirports, prefAirports),
                    foodPackageMap.get(pureTrip.getFoodPackage()),
                    calculatePriceValue(minPrice, maxPrice, pureTrip.getPrice()),
                    false
            );
            trips.add(new TripFuzzyDTO(pureTrip, fuzzEvaluation));
        }
        trips.sort(Comparator.comparingDouble(TripFuzzyDTO::getTripScore).reversed());
        return trips;
    }

    private Integer determinePref(
            Long id,
            List<Long> highPref,
            List<Long> pref
    ) {
        if (highPref.contains(id)) return 1;
        if (pref.contains(id)) return 2;
        return 3;
    }

    private Map<String, Integer> createFoodPackagesMap(
            Integer allInclusivePref,
            Integer fullBoardPref,
            Integer halfBoardPref,
            Integer breakfastPref,
            Integer noFoodPref
    ) {
        return Map.of(
                "All inclusive", allInclusivePref,
                "Plná penze", fullBoardPref,
                "Polopenze", halfBoardPref,
                "Snídaně", breakfastPref,
                "Bez stravy", noFoodPref
        );
    }

    private float calculatePriceValue(float minPrice, float maxPrice, float price) {
        return 6 * (price - minPrice) / (maxPrice - minPrice);
    }

}
