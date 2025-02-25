package com.example.tripfinder.controllers;

import com.example.tripfinder.model.BeachDistDTO;
import com.example.tripfinder.services.AirportService;
import com.example.tripfinder.services.BeachDistService;
import com.example.tripfinder.services.HotelService;
import com.example.tripfinder.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final AirportService airportService;
    private final LocationService locationService;
    private final HotelService hotelService;
    private final BeachDistService beachDistService;

    @GetMapping("/searchForm")
    public String searchForm(Model model) {
        model.addAttribute("airports", airportService.getAirports());
        model.addAttribute("locations", locationService.getLocations());
        List<BeachDistDTO> beachDists = beachDistService.getBeachDistances();
        beachDists.sort(Comparator.comparingLong(BeachDistDTO::getId).reversed());
        model.addAttribute("beach_dists", beachDists);
        return "searchForm";
    }

    @GetMapping("/searchFuzzyForm")
    public String searchFuzzyForm(Model model) {
        model.addAttribute("airports", airportService.getAirports());
        model.addAttribute("locations", locationService.getLocations());
        return "searchFormFuzzy";
    }

    @GetMapping("/searchHotelSpecificForm")
    public String searchHotSpecForm(Model model) {
        model.addAttribute("hotels", hotelService.getHotels());
        model.addAttribute("airports", airportService.getAirports());
        return "searchFormHotelSpecific";
    }
}
