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
        FIS fisEq = FIS.load(fileNameHotelEq,true);
        FIS fisHot = FIS.load(fileNameHotel,true);

        FunctionBlock functionBlockEq = fisEq.getFunctionBlock("hotel_equipment");
        FunctionBlock functionBlockHotel = fisHot.getFunctionBlock("hotel");

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

    }
}
