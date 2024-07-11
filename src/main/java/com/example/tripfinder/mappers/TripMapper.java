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
            "hot.stars AS stars, " +
            "a.name || ' (' || a.iata || ')' AS airport, " +
            "fp.name AS foodPackage, " +
            "bd.name AS beachDistance, " +
            "AVG(r.rating) AS averageRating, " +
            "t.date_from AS dateFrom, " +
            "t.date_to AS dateTo, " +
            "t.date_to - t.date_from AS days, " +
            "(#{maxPrice}-t.price) * 0.005 * #{priceImportance} AS priceScore, " +
            "hot.stars * 20 * #{starsImp} AS starsScore, " +
            "CASE " +
                    "WHEN fp.id = 1 THEN #{allInclusiveValue} " +
                    "WHEN fp.id = 2 THEN #{fullBoardValue} " +
                    "WHEN fp.id = 3 THEN #{halfBoardValue} " +
                    "WHEN fp.id = 4 THEN #{breakfastValue} " +
                    "WHEN fp.id = 5 THEN #{noFoodValue} " +
            "END * CAST(#{foodImp} AS numeric) AS foodScore, " +
            "AVG(r.rating) * 10 * CAST(#{ratingCoeff} AS numeric) AS ratingScore, " +
            "CASE " +
                    "WHEN t.airport = ANY(SELECT airport FROM airport_prefs WHERE high_pref IS TRUE) THEN 50 " +
                    "WHEN t.airport = ANY(SELECT airport FROM airport_prefs WHERE high_pref IS NOT TRUE) THEN 30 " +
                    "ELSE 0 " +
            "END * CAST(#{airportCoeff} AS numeric) AS airportScore, " +
            "CASE " +
                    "WHEN bd.id = 1 THEN 90 " +
                    "WHEN bd.id = 2 THEN 75 " +
                    "WHEN bd.id = 3 THEN 60 " +
                    "WHEN bd.id = 4 THEN 40 " +
            "END * CAST(#{beachDistImp} AS numeric) AS beachDistScore, " +
            "FROM trips t " +
            "JOIN hotels hot ON t.hotel = hot.id " +
            "JOIN food_packages fp ON t.food_package = fp.id " +
            "JOIN airports a ON t.airport = a.id " +
            "JOIN beach_distance bd ON hot.beach_dist = bd.id " +
            "LEFT JOIN reviews r ON hot.id = r.hotel " +
            "WHERE price <= #{maxPrice} " +
            "AND persons >= #{persons} " +
            "AND date_from > #{from} " +
            "AND date_to < #{to} " +
            "GROUP BY t.id " +
            ") " +
            "SELECT " +
            "id, " +
            "price, " +
            "hotel, " +
            "stars, " +
            "foodPackage, " +
            "airport, " +
            "beachDistance, " +
            "averageRating, " +
            "dateFrom, " +
            "dateTo, " +
            "days, " +
            "priceScore, " +
            "starsScore, " +
            "foodScore, " +
            "ratingScore, " +
            "airportScore, " +
            "beachDistScore, " +
            "(priceScore + starsScore + foodScore + ratingScore + airportScore + beachDistScore) AS totalScore " +
            "FROM cte " +
            "WHERE days >= #{minDays} " +
            "AND days <= #{maxDays} " +
            "ORDER BY totalScore DESC " +
            "LIMIT #{limit}"
    )
    List<TripDTO> searchTrips(
            float maxPrice,
            float priceImportance,
            float starsImp,
            float allInclusiveValue,
            float fullBoardValue,
            float halfBoardValue,
            float breakfastValue,
            float noFoodValue,
            float ratingCoeff,
            float airportCoeff,
            float foodImp,
            float beachDistImp,
            int persons,
            int limit,
            LocalDate from,
            LocalDate to,
            int minDays,
            int maxDays);
}
