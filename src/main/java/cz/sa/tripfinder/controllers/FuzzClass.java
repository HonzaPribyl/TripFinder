package cz.sa.tripfinder.controllers;

import cz.sa.tripfinder.model.TripFuzzEvaluationDTO;
import cz.sa.tripfinder.services.JFuzzService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FuzzClass {

//    private final JFuzzService jFuzzService;

    public static void main(String[] args) throws Exception {
        JFuzzService jFuzzService = new JFuzzService();
        TripFuzzEvaluationDTO tripFuzzEvaluation = jFuzzService.evaluate(1, 1, 1, 5, 80, 1, 1, 1, 1, 1, true);
        System.out.println(tripFuzzEvaluation);
    }
}
