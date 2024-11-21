package com.example.tripfinder.controllers;

import com.example.tripfinder.services.AirportService;
import com.example.tripfinder.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final AirportService airportService;
    private final LocationService locationService;

    @GetMapping("/searchForm")
    public String searchForm(Model model) {
        model.addAttribute("airports", airportService.getAirports());
        model.addAttribute("locations", locationService.getLocations());
        return "searchForm";
    }
}
