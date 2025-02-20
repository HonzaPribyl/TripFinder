package com.example.tripfinder.services;

import com.example.tripfinder.model.TripFuzzEvaluationDTO;
import jakarta.annotation.Nonnull;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class JFuzzService {

    @Nonnull
    public TripFuzzEvaluationDTO evaluate(
            int familyFriendly,
            int wifi,
            int swimmingPool,
            int stars,
            int locPref,
            int beachDist,
            int airportPreference,
            int foodPackagePreference,
            float price,
            boolean showCharts
    ) {
        String fileNameHotelEq = "src/main/resources/fcl/hotel_equipment.fcl";
        String fileNameHotel = "src/main/resources/fcl/hotel.fcl";
        String fileNameLocation = "src/main/resources/fcl/location.fcl";
        String fileNameHotAndLoc = "src/main/resources/fcl/hotel_and_location.fcl";
        String fileNameConvenience = "src/main/resources/fcl/convenience.fcl";
        String fileNameJourney = "src/main/resources/fcl/journey.fcl";
        String fileNameTrip = "src/main/resources/fcl/trip.fcl";
        FIS fisEq = FIS.load(fileNameHotelEq,true);
        FIS fisHot = FIS.load(fileNameHotel,true);
        FIS fisLoc = FIS.load(fileNameLocation,true);
        FIS fisHotAndLoc = FIS.load(fileNameHotAndLoc,true);
        FIS fisConvenience = FIS.load(fileNameConvenience,true);
        FIS fisJourney = FIS.load(fileNameJourney,true);
        FIS fisTrip = FIS.load(fileNameTrip,true);

        FunctionBlock functionBlockEq = fisEq.getFunctionBlock("hotel_equipment");
        FunctionBlock functionBlockHotel = fisHot.getFunctionBlock("hotel");
        FunctionBlock functionBlockLocation = fisLoc.getFunctionBlock("location");
        FunctionBlock functionBlockHotelAndLocation = fisHotAndLoc.getFunctionBlock("hotel_and_location");
        FunctionBlock functionBlockConvenience = fisConvenience.getFunctionBlock("convenience");
        FunctionBlock functionJourney = fisJourney.getFunctionBlock("journey");
        FunctionBlock functionTrip = fisTrip.getFunctionBlock("trip");

        if (showCharts) {
            JFuzzyChart.get().chart(functionBlockEq);
            JFuzzyChart.get().chart(functionBlockHotel);
            JFuzzyChart.get().chart(functionBlockLocation);
            JFuzzyChart.get().chart(functionBlockHotelAndLocation);
            JFuzzyChart.get().chart(functionBlockConvenience);
            JFuzzyChart.get().chart(functionJourney);
            JFuzzyChart.get().chart(functionTrip);
        }

        fisEq.setVariable("family_friendly", familyFriendly);
        fisEq.setVariable("wifi", wifi);
        fisEq.setVariable("swimming_pool", swimmingPool);

        fisEq.evaluate();

        Variable equipment = functionBlockEq.getVariable("equipment");
        if (showCharts) {
            JFuzzyChart.get().chart(equipment, equipment.getDefuzzifier(), true);
        }

        fisHot.setVariable("stars", stars);
        fisHot.setVariable("equipment", fisEq.getVariable("equipment").getValue());

        fisHot.evaluate();

        Variable hotel = functionBlockHotel.getVariable("hotel");
        if (showCharts) {
            JFuzzyChart.get().chart(hotel, hotel.getDefuzzifier(), true);
        }

        fisLoc.setVariable("location_preference", locPref);
        fisLoc.setVariable("beach_distance", beachDist);

        fisLoc.evaluate();

        Variable location = functionBlockLocation.getVariable("location_attributes");
        if (showCharts) {
            JFuzzyChart.get().chart(location, location.getDefuzzifier(), true);
        }

        fisHotAndLoc.setVariable("hotel", fisHot.getVariable("hotel").getValue());
        fisHotAndLoc.setVariable("location_attributes", fisLoc.getVariable("location_attributes").getValue());

        fisHotAndLoc.evaluate();

        Variable hotelAndLocation = functionBlockHotelAndLocation.getVariable("hotel_and_location");
        if (showCharts) {
            JFuzzyChart.get().chart(hotelAndLocation, hotelAndLocation.getDefuzzifier(), true);
        }

        fisConvenience.setVariable("airport_preference", airportPreference);
        fisConvenience.setVariable("food_package_preference", foodPackagePreference);

        fisConvenience.evaluate();

        Variable convenience = functionBlockConvenience.getVariable("convenience");
        if (showCharts) {
            JFuzzyChart.get().chart(convenience, convenience.getDefuzzifier(), true);
        }

        fisJourney.setVariable("convenience", fisConvenience.getVariable("convenience").getValue());
        fisJourney.setVariable("price", price);

        fisJourney.evaluate();

        Variable journey = functionJourney.getVariable("journey");
        if (showCharts) {
            JFuzzyChart.get().chart(journey, journey.getDefuzzifier(), true);
        }

        fisTrip.setVariable("journey", fisJourney.getVariable("journey").getValue());
        fisTrip.setVariable("hotel_and_location", fisHotAndLoc.getVariable("hotel_and_location").getValue());

        fisTrip.evaluate();

        Variable trip = functionTrip.getVariable("trip");
        if (showCharts) {
            JFuzzyChart.get().chart(trip, trip.getDefuzzifier(), true);
        }

        return new TripFuzzEvaluationDTO(
                equipment.getValue(),
                hotel.getValue(),
                location.getValue(),
                hotelAndLocation.getValue(),
                convenience.getValue(),
                journey.getValue(),
                trip.getValue()
        );
    }
}
