package com.example.tripfinder.controllers;

import com.example.tripfinder.model.TripFuzzEvaluationDTO;
import com.example.tripfinder.services.JFuzzService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FuzzClass {

//    private final JFuzzService jFuzzService;

    public static void main(String[] args) throws Exception {
        JFuzzService jFuzzService = new JFuzzService();
        TripFuzzEvaluationDTO tripFuzzEvaluation = jFuzzService.evaluate(1, 1, 1, 5, 1, 1, 1, 1, 1, true);
        System.out.println(tripFuzzEvaluation);
    }
}
