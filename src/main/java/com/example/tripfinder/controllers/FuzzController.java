package com.example.tripfinder.controllers;

import com.example.tripfinder.model.TripFuzzEvaluationDTO;
import com.example.tripfinder.services.JFuzzService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FuzzController {

    private final JFuzzService jFuzzService;

    @GetMapping("/searchFuzz")
    public TripFuzzEvaluationDTO searchFuzz() {
        return jFuzzService.evaluate(1, 1, 1, 5, 1, 1, 1, 1, 1, false);
    }
}
