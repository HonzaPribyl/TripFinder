package com.example.tripfinder.services;

import com.example.tripfinder.mappers.TripMapper;
import com.example.tripfinder.model.TripFuzzEvaluationDTO;
import com.example.tripfinder.model.TripFuzzyDTO;
import com.example.tripfinder.model.TripPureDTO;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class FuzzySearchService {

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
                1,
                BEACH_DIST_MAP.get(pureTrip.getBeachDist()),
                1,
                1,
                1,
                false
        );
        return new TripFuzzyDTO(pureTrip, fuzzEvaluation);
    }

}
