package com.example.tripfinder.controllers;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzClass {
    public static void main(String[] args) throws Exception {
        // Load from 'FCL' file
        String fileNameHotelEq = "src/main/resources/fcl/hotel_equipment.fcl";
        String fileNameHotel = "src/main/resources/fcl/hotel.fcl";
        String fileNameLocation = "src/main/resources/fcl/location.fcl";
        String fileNameHotAndLoc = "src/main/resources/fcl/hotel_and_location.fcl";
        String fileNameConvenience = "src/main/resources/fcl/convenience.fcl";
        String fileNameJourney = "src/main/resources/fcl/journey.fcl";
        FIS fisEq = FIS.load(fileNameHotelEq,true);
        FIS fisHot = FIS.load(fileNameHotel,true);
        FIS fisLoc = FIS.load(fileNameLocation,true);
        FIS fisHotAndLoc = FIS.load(fileNameHotAndLoc,true);
        FIS fisConvenience = FIS.load(fileNameConvenience,true);
        FIS fisJourney = FIS.load(fileNameJourney,true);

        FunctionBlock functionBlockEq = fisEq.getFunctionBlock("hotel_equipment");
        FunctionBlock functionBlockHotel = fisHot.getFunctionBlock("hotel");
        FunctionBlock functionBlockLocation = fisLoc.getFunctionBlock("location");
        FunctionBlock functionBlockHotelAndLocation = fisHotAndLoc.getFunctionBlock("hotel_and_location");
        FunctionBlock functionBlockConvenience = fisConvenience.getFunctionBlock("convenience");
        FunctionBlock functionJourney = fisJourney.getFunctionBlock("journey");

        // Error while loading?
        if( fisEq == null ) {
            System.err.println("Can't load file: '" + fileNameHotelEq + "'");
            return;
        }

        // Show
        JFuzzyChart.get().chart(functionBlockEq);

        // Set inputs
        fisEq.setVariable("family_friendly", 1);
        fisEq.setVariable("wifi", 1);
        fisEq.setVariable("swimming_pool", 1);

        // Evaluate
        fisEq.evaluate();

        // Show output variable's chart
        Variable equipment = functionBlockEq.getVariable("equipment");
        JFuzzyChart.get().chart(equipment, equipment.getDefuzzifier(), true);

        fisHot.setVariable("stars", 5);
        fisHot.setVariable("equipment", fisEq.getVariable("equipment").getValue());

        fisHot.evaluate();

        Variable hotel = functionBlockHotel.getVariable("hotel");
        JFuzzyChart.get().chart(hotel, hotel.getDefuzzifier(), true);

        fisLoc.setVariable("location_preference", 1);
        fisLoc.setVariable("beach_distance", 5);

        fisLoc.evaluate();

        Variable location = functionBlockLocation.getVariable("location_attributes");
        JFuzzyChart.get().chart(location, location.getDefuzzifier(), true);

        fisHotAndLoc.setVariable("hotel", fisHot.getVariable("hotel").getValue());
        fisHotAndLoc.setVariable("location_attributes", fisLoc.getVariable("location_attributes").getValue());

        fisHotAndLoc.evaluate();

        Variable hotelAndLocation = functionBlockHotelAndLocation.getVariable("hotel_and_location");
        JFuzzyChart.get().chart(hotelAndLocation, hotelAndLocation.getDefuzzifier(), true);

        fisConvenience.setVariable("airport_preference", 1);
        fisConvenience.setVariable("food_package_preference", 1);

        fisConvenience.evaluate();

        Variable convenience = functionBlockConvenience.getVariable("convenience");
        JFuzzyChart.get().chart(convenience, convenience.getDefuzzifier(), true);

        fisJourney.setVariable("convenience", fisConvenience.getVariable("convenience").getValue());
        fisJourney.setVariable("price", 1);

        fisJourney.evaluate();

        Variable journey = functionJourney.getVariable("journey");
        JFuzzyChart.get().chart(journey, journey.getDefuzzifier(), true);

    }
}
