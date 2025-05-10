package cz.sa.tripfinder.services;

import cz.sa.tripfinder.mappers.TripMapper;
import cz.sa.tripfinder.model.TripFuzzEvaluationDTO;
import cz.sa.tripfinder.model.TripFuzzyDTO;
import cz.sa.tripfinder.model.TripPureDTO;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FuzzySearchService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final JFuzzService jFuzzService;
    private final TripMapper tripMapper;

    public List<TripFuzzyDTO> searchFuzzyTrips(
            float maxPrice,
            float minPrice,
            String from,
            String to,
            int minDays,
            int maxDays,
            int persons,
            int limit,
            List<Long> highPrefLocs,
            List<Long> prefLocs,
            List<Long> highPrefAirports,
            List<Long> prefAirports,
            Integer allInclusivePref,
            Integer fullBoardPref,
            Integer halfBoardPref,
            Integer breakfastPref,
            Integer noFoodPref,
            Integer minFoodPref,
            Integer minStars,
            Integer minRating,
            Integer minBeachDist,
            Boolean airportFilter,
            Boolean locationFilter,
            Boolean familyFilter,
            Boolean wifiFilter,
            Boolean poolFilter,
            Boolean hotelDistinct
    ) {
        final LocalDate dateFrom = LocalDate.parse(from, DATE_TIME_FORMATTER);
        final LocalDate dateTo = LocalDate.parse(to, DATE_TIME_FORMATTER);

        if (highPrefAirports == null) {
            highPrefAirports = Collections.emptyList();
        }
        if (prefAirports == null) {
            prefAirports = Collections.emptyList();
        }
        final String encodedPrefAirports = MappingHelper.encodeAllowedIds(
                Stream.concat(highPrefAirports.stream(),prefAirports.stream()).toList());

        if (highPrefLocs == null) {
            highPrefLocs = Collections.emptyList();
        }
        if (prefLocs == null) {
            prefLocs = Collections.emptyList();
        }
        final String encodedPrefLocs = MappingHelper.encodeAllowedIds(
                Stream.concat(highPrefLocs.stream(),prefLocs.stream()).toList()
        );

        final Map<String, Integer> foodPackageMap = createFoodPackagesMap(
                allInclusivePref, fullBoardPref, halfBoardPref, breakfastPref, noFoodPref);
        final String allowedFoodPrefs = encodeAllowedFoodPrefs(foodPackageMap, minFoodPref);
        List<TripPureDTO> pureTrips = tripMapper.getPureTrips(
                dateFrom,
                dateTo,
                minDays,
                maxDays,
                maxPrice,
                minPrice,
                persons,
                minStars,
                minRating,
                allowedFoodPrefs,
                minBeachDist,
                airportFilter,
                locationFilter,
                familyFilter,
                wifiFilter,
                poolFilter,
                encodedPrefAirports,
                encodedPrefLocs,
                false,
                null);
        List<TripFuzzyDTO> trips = new ArrayList<>();
        for (TripPureDTO pureTrip : pureTrips) {
            TripFuzzEvaluationDTO fuzzEvaluation = jFuzzService.evaluate(
                    MappingHelper.EQUIPMENT_MAP.get(pureTrip.isFamilyFriendly()),
                    MappingHelper.EQUIPMENT_MAP.get(pureTrip.isInternet()),
                    MappingHelper.EQUIPMENT_MAP.get(pureTrip.isSwimmingPool()),
                    pureTrip.getStars(),
                    pureTrip.getAverageRating(),
                    determinePref(pureTrip.getLocationId(), highPrefLocs, prefLocs),
                    MappingHelper.BEACH_DIST_MAP.get(pureTrip.getBeachDist()),
                    determinePref(pureTrip.getAirportId(), highPrefAirports, prefAirports),
                    foodPackageMap.get(pureTrip.getFoodPackage()),
                    MappingHelper.calculatePriceValue(minPrice, maxPrice, pureTrip.getPrice()),
                    false
            );
            trips.add(new TripFuzzyDTO(pureTrip, fuzzEvaluation));
        }
        Set<String> seenHotels = new HashSet<>();
        trips = trips.stream()
                .sorted(Comparator.comparingDouble(TripFuzzyDTO::getTripScore).reversed())
                .filter(trip -> !hotelDistinct || seenHotels.add(trip.getHotel()))
                .limit(limit)
                .collect(Collectors.toList());
        return trips;
    }

    public List<TripFuzzyDTO> searchFuzzyTripsByHotel(
            float maxPrice,
            float minPrice,
            String from,
            String to,
            int minDays,
            int maxDays,
            int persons,
            int limit,
            List<Long> highPrefAirports,
            List<Long> prefAirports,
            Integer allInclusivePref,
            Integer fullBoardPref,
            Integer halfBoardPref,
            Integer breakfastPref,
            Integer noFoodPref,
            Integer minFoodPref,
            Boolean airportFilter,
            Long hotel
    ) {
        final LocalDate dateFrom = LocalDate.parse(from, DATE_TIME_FORMATTER);
        final LocalDate dateTo = LocalDate.parse(to, DATE_TIME_FORMATTER);

        if (highPrefAirports == null) {
            highPrefAirports = Collections.emptyList();
        }
        if (prefAirports == null) {
            prefAirports = Collections.emptyList();
        }
        final String encodedPrefAirports = MappingHelper.encodeAllowedIds(
                Stream.concat(highPrefAirports.stream(),prefAirports.stream()).toList());

        final Map<String, Integer> foodPackageMap = createFoodPackagesMap(
                allInclusivePref, fullBoardPref, halfBoardPref, breakfastPref, noFoodPref);
        final String allowedFoodPrefs = encodeAllowedFoodPrefs(foodPackageMap, minFoodPref);
        List<TripPureDTO> pureTrips = tripMapper.getPureTrips(
                dateFrom,
                dateTo,
                minDays,
                maxDays,
                maxPrice,
                minPrice,
                persons,
                0,
                0,
                allowedFoodPrefs,
                4,
                airportFilter,
                false,
                false,
                false,
                false,
                encodedPrefAirports,
                "",
                true,
                hotel);
        List<TripFuzzyDTO> trips = new ArrayList<>();
        for (TripPureDTO pureTrip : pureTrips) {
            TripFuzzEvaluationDTO fuzzEvaluation = jFuzzService.evaluate(
                    MappingHelper.EQUIPMENT_MAP.get(pureTrip.isFamilyFriendly()),
                    MappingHelper.EQUIPMENT_MAP.get(pureTrip.isInternet()),
                    MappingHelper.EQUIPMENT_MAP.get(pureTrip.isSwimmingPool()),
                    pureTrip.getStars(),
                    pureTrip.getAverageRating(),
                    3,
                    MappingHelper.BEACH_DIST_MAP.get(pureTrip.getBeachDist()),
                    determinePref(pureTrip.getAirportId(), highPrefAirports, prefAirports),
                    foodPackageMap.get(pureTrip.getFoodPackage()),
                    MappingHelper.calculatePriceValue(minPrice, maxPrice, pureTrip.getPrice()),
                    false
            );
            trips.add(new TripFuzzyDTO(pureTrip, fuzzEvaluation));
        }
        trips = trips.stream()
                .sorted(Comparator.comparingDouble(TripFuzzyDTO::getTripScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
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
    
    private String encodeAllowedFoodPrefs(
            Map<String, Integer> foodPrefs,
            Integer minPref
    ) {
        StringBuilder allowedFoodPrefs = new StringBuilder();
        int index = 1;
        for (Map.Entry<String, Integer> entry : foodPrefs.entrySet()) {
            if (entry.getValue() >= minPref) {
                allowedFoodPrefs.append(index).append(".");
            }
            index++;
        }
        return allowedFoodPrefs.toString();
    }

}
