package com.example.tripfinder.services;

import com.example.tripfinder.mappers.AirportMapper;
import com.example.tripfinder.mappers.TripMapper;
import com.example.tripfinder.model.TripByHotelDTO;
import com.example.tripfinder.model.TripDTO;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class TripService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String IMPORTANCE_LOW = "il";
    private static final String IMPORTANCE_MEDIUM = "im";
    private static final String IMPORTANCE_HIGH = "ih";
    private static final String IMPORTANCE_NONE = "in";
    private static final String IMPORTANCE_NECESSARY = "inc";

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
            float minPrice,
            @Nonnull final String priceImp,
            @Nonnull final String starsImp,
            int minStars,
            int minRating,
            @Nonnull final String allInclusivePref,
            @Nonnull final String fullBoardPref,
            @Nonnull final String halfBoardPref,
            @Nonnull final String breakfastPref,
            @Nonnull final String noFoodPref,
            @Nonnull final String minFoodPref,
            @Nullable List<Long> highPrefAirports,
            @Nullable List<Long> prefAirports,
            @Nullable List<Long> highPrefLocs,
            @Nullable List<Long> prefLocs,
            @Nonnull final String locImp,
            @Nonnull final String foodImp,
            @Nonnull final String ratingImp,
            @Nonnull final String airportImp,
            @Nonnull final String beachDistImp,
            @Nonnull final Long minBeachDist,
            @Nonnull final String familyImp,
            @Nonnull final String wifiImp,
            @Nonnull final String poolImp,
            int persons,
            int limit,
            @Nonnull final String from,
            @Nonnull final String to,
            int minDays,
            int maxDays,
            boolean filterAirports,
            boolean filterLocs,
            boolean hotelDistinct
    ) {

        float priceImpCoeff = impCoeff(priceImp);

        float locImpCoeff = impCoeff(locImp);

        float starsImpCoeff = impCoeff(starsImp);

        float beachDistImpCoeff = impCoeff(beachDistImp);

        boolean familyFilter = sortAccessory(familyImp);
        float familyImpCoeff = impCoeff(familyImp);

        boolean wifiFilter = sortAccessory(wifiImp);
        float wifiImpCoeff = impCoeff(wifiImp);

        boolean poolFilter = sortAccessory(poolImp);
        float poolImpCoeff = impCoeff(poolImp);

        float allInclusiveValue = foodImp(allInclusivePref);
        float fullBoardValue = foodImp(fullBoardPref);
        float halfBoardValue = foodImp(halfBoardPref);
        float breakfastValue = foodImp(breakfastPref);
        float noFoodValue = foodImp(noFoodPref);
        float minFoodValue = foodImp(minFoodPref);

        float ratingCoeff = impCoeff(ratingImp);

        float foodImpCoeff = impCoeff(foodImp);

        float airportCoeff = impCoeff(airportImp);

        final LocalDate dateFrom = LocalDate.parse(from, DATE_TIME_FORMATTER);
        final LocalDate dateTo = LocalDate.parse(to, DATE_TIME_FORMATTER);

        if (highPrefAirports == null) {
            highPrefAirports = Collections.emptyList();
        }
        final String encodedHighPrefAirports = IdEncoder.encodeAllowedIds(highPrefAirports);
        if (prefAirports == null) {
            prefAirports = Collections.emptyList();
        }
        final String encodedPrefAirports = IdEncoder.encodeAllowedIds(prefAirports);

        if (highPrefLocs == null) {
            highPrefLocs = Collections.emptyList();
        }
        final String encodedHighPrefLocs = IdEncoder.encodeAllowedIds(highPrefLocs);
        if (prefLocs == null) {
            prefLocs = Collections.emptyList();
        }
        final String encodedPrefLocs = IdEncoder.encodeAllowedIds(prefLocs);

        return tripMapper.searchTrips(
                maxPrice,
                minPrice,
                priceImpCoeff,
                locImpCoeff,
                starsImpCoeff,
                minStars,
                minRating,
                allInclusiveValue,
                fullBoardValue,
                halfBoardValue,
                breakfastValue,
                noFoodValue,
                minFoodValue,
                ratingCoeff,
                airportCoeff,
                foodImpCoeff,
                beachDistImpCoeff,
                minBeachDist,
                familyImpCoeff,
                wifiImpCoeff,
                poolImpCoeff,
                persons,
                limit,
                dateFrom,
                dateTo,
                minDays,
                maxDays,
                filterAirports,
                filterLocs,
                familyFilter,
                wifiFilter,
                poolFilter,
                encodedHighPrefAirports,
                encodedPrefAirports,
                encodedHighPrefLocs,
                encodedPrefLocs,
                hotelDistinct
        );
    }

    public List<TripByHotelDTO> searchByHotel(
            float maxPrice,
            @Nonnull final String priceImp,
            @Nonnull final String allInclusivePref,
            @Nonnull final String fullBoardPref,
            @Nonnull final String halfBoardPref,
            @Nonnull final String breakfastPref,
            @Nonnull final String noFoodPref,
            @Nullable List<Long> highPrefAirports,
            @Nullable List<Long> prefAirports,
            @Nonnull final String foodImp,
            @Nonnull final String airportImp,
            int persons,
            int limit,
            @Nonnull final String from,
            @Nonnull final String to,
            int minDays,
            int maxDays,
            Long hotel
    ) {
        float priceImpCoeff = impCoeff(priceImp);

        float allInclusiveValue = foodImp(allInclusivePref);
        float fullBoardValue = foodImp(fullBoardPref);
        float halfBoardValue = foodImp(halfBoardPref);
        float breakfastValue = foodImp(breakfastPref);
        float noFoodValue = foodImp(noFoodPref);

        float foodImpCoeff = impCoeff(foodImp);

        float airportCoeff = impCoeff(airportImp);

        final LocalDate dateFrom = LocalDate.parse(from, DATE_TIME_FORMATTER);
        final LocalDate dateTo = LocalDate.parse(to, DATE_TIME_FORMATTER);

        if (highPrefAirports == null) {
            highPrefAirports = Collections.emptyList();
        }
        if (prefAirports == null) {
            prefAirports = Collections.emptyList();
        }
        reinsertAirportPrefs(highPrefAirports, prefAirports);

        return tripMapper.searchTripsByHotel(
                maxPrice,
                priceImpCoeff,
                allInclusiveValue,
                fullBoardValue,
                halfBoardValue,
                breakfastValue,
                noFoodValue,
                airportCoeff,
                foodImpCoeff,
                persons,
                limit,
                dateFrom,
                dateTo,
                minDays,
                maxDays,
                hotel
        );
    }

    private boolean sortAccessory(@Nonnull String param) {
        return switch (param) {
            case IMPORTANCE_NECESSARY -> true;
            default -> false;
        };
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
