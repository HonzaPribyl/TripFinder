package com.example.tripfinder.controllers;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzClass {
    public static void main(String[] args) throws Exception {
        // Load from 'FCL' file
        String fileName = "src/main/resources/fcl/tipper.fcl";
        FIS fis = FIS.load(fileName,true);

        FunctionBlock functionBlock = fis.getFunctionBlock("tipper");

        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

        // Show
        JFuzzyChart.get().chart(functionBlock);

        // Set inputs
        fis.setVariable("service", 3);
        fis.setVariable("food", 7);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable tip = functionBlock.getVariable("tip");
//        JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

        // Print ruleSet
        System.out.println(fis);
    }
}
