package com.example.tripfinder.services;

import com.example.tripfinder.mappers.TripMapper;
import com.example.tripfinder.model.TripDTO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class TripService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String IMPORTANCE_LOW = "il";
    private static final String IMPORTANCE_MEDIUM = "im";
    private static final String IMPORTANCE_HIGH = "ih";

    private static final float COEFFICIENT_LOW_IMP = 0.75f;
    private static final float COEFFICIENT_MEDIUM_IMP = 1;
    private static final float COEFFICIENT_HIGH_IMP = 1.5f;

    private final TripMapper tripMapper;

    public List<TripDTO> search(
            float maxPrice,
            float priceImp,
            int limit,
            @Nonnull final String beachDistImp,
            @Nonnull final String from,
            @Nonnull final String to
    ) {
        float beachDistImpCoeff = switch (beachDistImp) {
            case IMPORTANCE_LOW -> COEFFICIENT_LOW_IMP;
            case IMPORTANCE_MEDIUM -> COEFFICIENT_MEDIUM_IMP;
            case IMPORTANCE_HIGH -> COEFFICIENT_HIGH_IMP;
            default -> 1;
        };

        final LocalDate dateFrom = LocalDate.parse(from, DATE_TIME_FORMATTER);
        final LocalDate dateTo = LocalDate.parse(to, DATE_TIME_FORMATTER);

        return tripMapper.searchTrips(
                maxPrice,
                priceImp,
                beachDistImpCoeff,
                limit,
                dateFrom,
                dateTo
        );
    }
}
