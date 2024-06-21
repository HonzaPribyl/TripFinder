package com.example.tripfinder.mappers;

import com.example.tripfinder.model.TripDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TripMapper {

    @Select(
            "SELECT " +
                    "id, " +
                    "price, " +
                    "date_from AS dateFrom, " +
                    "date_to AS dateTo, " +
                    "((#{maxPrice}-price) * #{priceImportance}) AS score " +
                    "FROM trips " +
                    "WHERE price <= #{maxPrice} " +
                    "AND date_from > #{from} " +
                    "AND date_to < #{to} " +
                    "ORDER BY score DESC " +
                    "LIMIT #{limit}"
    )
    List<TripDTO> searchTrips(
            float maxPrice,
            float priceImportance,
            int limit,
            LocalDate from,
            LocalDate to);
}
