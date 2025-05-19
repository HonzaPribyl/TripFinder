package cz.sa.tripfinder.services;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;

public class MappingHelper {

    public static final Map<Boolean, Integer> EQUIPMENT_MAP = Map.of(
            true, 1,
            false, 2
    );

    public static final Map<String, Integer> BEACH_DIST_MAP = Map.of(
            "Přímo u pláže", 5,
            "Do 5 min", 10,
            "Do 15 min", 15,
            "Více, než 15 min", 20
    );

    @Nonnull
    public static String encodeAllowedIds(
            @Nonnull final List<Long> allowedIds) {
        StringBuilder allowedPrefs = new StringBuilder();
        for (Long id : allowedIds) {
            allowedPrefs.append("a");
            allowedPrefs.append(id);
            allowedPrefs.append(".");
        }
        return allowedPrefs.toString();
    }

    public static float calculatePriceValue(float minPrice, float maxPrice, float price) {
        return 4 * (price - minPrice) / (maxPrice - minPrice) + 1;
    }

}
