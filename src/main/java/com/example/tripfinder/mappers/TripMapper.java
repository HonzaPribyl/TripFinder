package com.example.tripfinder.mappers;

import com.example.tripfinder.model.TripDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TripMapper {

    @Select(
            "WITH cte AS (" +
            "SELECT " +
            "t.id AS id, " +
            "t.price AS price, " +
            "hot.name AS hotel, " +
            "bd.name AS beachDistance, " +
            "t.date_from AS dateFrom, " +
            "t.date_to AS dateTo, " +
            "(#{maxPrice}-t.price) * #{priceImportance} AS priceScore, " +
            "CASE " +
                    "WHEN bd.id = 1 THEN 90 " +
                    "WHEN bd.id = 2 THEN 75 " +
                    "WHEN bd.id = 3 THEN 60 " +
                    "WHEN bd.id = 4 THEN 40 " +
            "END * CAST(#{beachDistImp} AS numeric) AS beachDistScore " +
            "FROM trips t " +
            "JOIN hotels hot ON t.hotel = hot.id " +
            "JOIN beach_distance bd ON hot.beach_dist = bd.id " +
            "WHERE price <= #{maxPrice} " +
            "AND date_from > #{from} " +
            "AND date_to < #{to} " +
            ") " +
            "SELECT " +
            "id, " +
            "price, " +
            "hotel, " +
            "beachDistance, " +
            "dateFrom, " +
            "dateTo, " +
            "priceScore, " +
            "beachDistScore, " +
            "(priceScore + beachDistScore) AS totalScore, " +
            "FROM cte " +
            "ORDER BY totalScore DESC " +
            "LIMIT #{limit}"
    )
    List<TripDTO> searchTrips(
            float maxPrice,
            float priceImportance,
            float beachDistImp,
            int limit,
            LocalDate from,
            LocalDate to);
}
