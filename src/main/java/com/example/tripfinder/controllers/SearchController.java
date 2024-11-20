package com.example.tripfinder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @GetMapping("/look")
    public String searchForm(Model model) {
        return "search";
    }
}
