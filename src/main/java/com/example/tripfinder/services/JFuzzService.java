package com.example.tripfinder.services;

import com.example.tripfinder.model.TripFuzzEvaluationDTO;
import jakarta.annotation.Nonnull;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.util.Map;
import java.util.stream.Collectors;

public class JFuzzService {

    private static final String FIS_FILES_PATH = "src/main/resources/fcl/";

    private static final Map<String, String> FILE_PATHS = Map.of(
            "rating_attributes", FIS_FILES_PATH + "rating_attributes.fcl",
            "hotel_equipment", FIS_FILES_PATH + "hotel_equipment.fcl",
            "hotel", FIS_FILES_PATH + "hotel.fcl",
            "location", FIS_FILES_PATH + "location.fcl",
            "hotel_and_location", FIS_FILES_PATH + "hotel_and_location.fcl",
            "convenience", FIS_FILES_PATH + "convenience.fcl",
            "journey", FIS_FILES_PATH + "journey.fcl",
            "trip", FIS_FILES_PATH + "trip.fcl"
    );

    @Nonnull
    public TripFuzzEvaluationDTO evaluate(
            int familyFriendly, int wifi, int swimmingPool, int stars, float rating,
            int locPref, int beachDist, int airportPreference, int foodPackagePreference,
            float price, boolean showCharts) {

        Map<String, FIS> fisMap = loadFuzzySystems();
        Map<String, FunctionBlock> functionBlocks = getFunctionBlocks(fisMap);

        if (showCharts) {
            functionBlocks.values().forEach(JFuzzyChart.get()::chart);
        }

        Variable ratingAttributes = evaluateSystem(fisMap.get("rating_attributes"), functionBlocks.get("rating_attributes"),
                Map.of("stars", (double) stars, "rating", (double) rating),
                showCharts, "rating_attributes");

        Variable equipment = evaluateSystem(fisMap.get("hotel_equipment"), functionBlocks.get("hotel_equipment"),
                Map.of("family_friendly",(double) familyFriendly, "wifi", (double) wifi, "swimming_pool", (double) swimmingPool),
                showCharts, "equipment");

        Variable hotel = evaluateSystem(fisMap.get("hotel"), functionBlocks.get("hotel"),
                Map.of("rating_attributes", ratingAttributes.getValue(), "equipment", equipment.getValue()),
                showCharts, "hotel");

        Variable location = evaluateSystem(fisMap.get("location"), functionBlocks.get("location"),
                Map.of("location_preference", (double) locPref, "beach_distance", (double) beachDist),
                showCharts, "location_attributes");

        Variable hotelAndLocation = evaluateSystem(fisMap.get("hotel_and_location"), functionBlocks.get("hotel_and_location"),
                Map.of("hotel", hotel.getValue(), "location_attributes", location.getValue()),
                showCharts, "hotel_and_location");

        Variable convenience = evaluateSystem(fisMap.get("convenience"), functionBlocks.get("convenience"),
                Map.of("airport_preference", (double) airportPreference, "food_package_preference", (double) foodPackagePreference),
                showCharts, "convenience");

        Variable journey = evaluateSystem(fisMap.get("journey"), functionBlocks.get("journey"),
                Map.of("convenience", convenience.getValue(), "price", (double) price),
                showCharts, "journey");

        Variable trip = evaluateSystem(fisMap.get("trip"), functionBlocks.get("trip"),
                Map.of("journey", journey.getValue(), "hotel_and_location", hotelAndLocation.getValue()),
                showCharts, "trip");

        return new TripFuzzEvaluationDTO(
                ratingAttributes.getValue(),
                equipment.getValue(),
                hotel.getValue(),
                location.getValue(),
                hotelAndLocation.getValue(),
                convenience.getValue(),
                journey.getValue(),
                trip.getValue()
        );
    }

    private Map<String, FIS> loadFuzzySystems() {
        return FILE_PATHS.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> FIS.load(entry.getValue(), true)));
    }

    private Map<String, FunctionBlock> getFunctionBlocks(Map<String, FIS> fisMap) {
        return fisMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getFunctionBlock(entry.getKey())));
    }

    private Variable evaluateSystem(
            FIS fis,
            FunctionBlock functionBlock,
            Map<String, Double> inputs,
            boolean showCharts,
            String variableName) {
        inputs.forEach(fis::setVariable);
        fis.evaluate();
        Variable result = functionBlock.getVariable(variableName);
        if (showCharts) {
            JFuzzyChart.get().chart(result, result.getDefuzzifier(), true);
        }
        return result;
    }
}
