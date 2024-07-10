package com.example.tripfinder.services;

import com.example.tripfinder.mappers.AirportMapper;
import com.example.tripfinder.mappers.TripMapper;
import com.example.tripfinder.model.TripDTO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class TripService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String IMPORTANCE_LOW = "il";
    private static final String IMPORTANCE_MEDIUM = "im";
    private static final String IMPORTANCE_HIGH = "ih";
    private static final String IMPORTANCE_NONE = "in";

    private static final float COEFFICIENT_LOW_IMP = 0.75f;
    private static final float COEFFICIENT_MEDIUM_IMP = 1;
    private static final float COEFFICIENT_HIGH_IMP = 1.5f;
    private static final float COEFFICIENT_NONE_IMP = 0;

    private static final String PREF_LOW = "lp";
    private static final String PREF_MEDIUM = "mp";
    private static final String PREF_HIGH = "hp";

    private static final float LOW_FOOD_PREF = 30;
    private static final float MEDIUM_FOOD_PREF = 50;
    private static final float HIGH_FOOD_PREF = 80;

    private final TripMapper tripMapper;
    private final AirportMapper airportMapper;

    public List<TripDTO> search(
            float maxPrice,
            @Nonnull final String priceImp,
            @Nonnull final String starsImp,
            @Nonnull final String allInclusivePref,
            @Nonnull final String fullBoardPref,
            @Nonnull final String halfBoardPref,
            @Nonnull final String breakfastPref,
            @Nonnull final String noFoodPref,
            @Nonnull List<Long> highPrefAirports,
            @Nonnull List<Long> prefAirports,
            @Nonnull final String foodImp,
            @Nonnull final String ratingImp,
            @Nonnull final String airportImp,
            @Nonnull final String beachDistImp,
            int persons,
            int limit,
            @Nonnull final String from,
            @Nonnull final String to
    ) {

        float priceImpCoeff = impCoeff(priceImp);

        float starsImpCoeff = impCoeff(starsImp);

        float beachDistImpCoeff = impCoeff(beachDistImp);

        float allInclusiveValue = foodImp(allInclusivePref);
        float fullBoardValue = foodImp(fullBoardPref);
        float halfBoardValue = foodImp(halfBoardPref);
        float breakfastValue = foodImp(breakfastPref);
        float noFoodValue = foodImp(noFoodPref);

        float ratingCoeff = impCoeff(ratingImp);

        float foodImpCoeff = impCoeff(foodImp);

        float airportCoeff = impCoeff(airportImp);

        final LocalDate dateFrom = LocalDate.parse(from, DATE_TIME_FORMATTER);
        final LocalDate dateTo = LocalDate.parse(to, DATE_TIME_FORMATTER);

        reinsertAirportPrefs(highPrefAirports, prefAirports);

        return tripMapper.searchTrips(
                maxPrice,
                priceImpCoeff,
                starsImpCoeff,
                allInclusiveValue,
                fullBoardValue,
                halfBoardValue,
                breakfastValue,
                noFoodValue,
                foodImpCoeff,
                ratingCoeff,
                airportCoeff,
                beachDistImpCoeff,
                persons,
                limit,
                dateFrom,
                dateTo
        );
    }

    private float impCoeff(@Nonnull String param) {
        return switch (param) {
            case IMPORTANCE_LOW -> COEFFICIENT_LOW_IMP;
            case IMPORTANCE_MEDIUM -> COEFFICIENT_MEDIUM_IMP;
            case IMPORTANCE_HIGH -> COEFFICIENT_HIGH_IMP;
            case IMPORTANCE_NONE -> COEFFICIENT_NONE_IMP;
            default -> 1;
        };
    }

    private float foodImp(@Nonnull String preference) {
        return switch (preference) {
            case PREF_LOW -> LOW_FOOD_PREF;
            case PREF_MEDIUM -> MEDIUM_FOOD_PREF;
            case PREF_HIGH -> HIGH_FOOD_PREF;
            default -> 50;
        };
    }

    private void reinsertAirportPrefs(
            @Nonnull List<Long> highPrefAirports,
            @Nonnull List<Long> prefAirports
    ) {
        airportMapper.clearAirports();
        highPrefAirports.forEach(l -> airportMapper.addAirportPref(l, true));
        prefAirports.forEach(l -> airportMapper.addAirportPref(l, false));
    }
}
