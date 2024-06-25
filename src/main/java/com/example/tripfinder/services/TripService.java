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

    private final TripMapper tripMapper;

    public List<TripDTO> search(
            float maxPrice,
            float priceImp,
            int limit,
            @Nonnull final String from,
            @Nonnull final String to
    ) {
        final LocalDate dateFrom = LocalDate.parse(from, DATE_TIME_FORMATTER);
        final LocalDate dateTo = LocalDate.parse(to, DATE_TIME_FORMATTER);
        return tripMapper.searchTrips(
                maxPrice,
                priceImp,
                limit,
                dateFrom,
                dateTo
        );
    }
}
