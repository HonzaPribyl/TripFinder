package com.example.tripfinder.services;

import jakarta.annotation.Nonnull;

import java.util.List;

public class IdEncoder {

    @Nonnull
    public static String encodeAllowedIds(
            @Nonnull final List<Long> allowedIds) {
        StringBuilder allowedFoodPrefs = new StringBuilder();
        for (Long id : allowedIds) {
            allowedFoodPrefs.append(id);
            allowedFoodPrefs.append(".");
        }
        return allowedFoodPrefs.toString();
    }

}
