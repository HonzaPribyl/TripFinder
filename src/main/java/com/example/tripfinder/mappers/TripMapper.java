package com.example.tripfinder.mappers;

import com.example.tripfinder.model.TripByHotelDTO;
import com.example.tripfinder.model.TripDTO;
import com.example.tripfinder.model.TripPureDTO;
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
            "l.name AS location, " +
            "hot.name AS hotel, " +
            "hot.stars AS stars, " +
            "a.name || ' (' || a.iata || ')' AS airport, " +
            "fp.name AS foodPackage, " +
            "bd.name AS beachDistance, " +
            "hot.family_friendly AS family_friendly, " +
            "hot.wifi AS wifi, " +
            "hot.swimming_pool AS swimming_pool, " +
            "COALESCE(AVG(r.rating),5) AS averageRating, " +
            "t.date_from AS dateFrom, " +
            "t.date_to AS dateTo, " +
            "DATEDIFF('DAY', t.date_from, t.date_to) AS days, " +
            "(#{maxPrice}-t.price) * 0.005 * #{priceImportance} AS priceScore, " +
            "hot.stars * 20 * #{starsImp} AS starsScore, " +
            "CASE " +
                    "WHEN fp.id = 1 THEN #{allInclusiveValue} " +
                    "WHEN fp.id = 2 THEN #{fullBoardValue} " +
                    "WHEN fp.id = 3 THEN #{halfBoardValue} " +
                    "WHEN fp.id = 4 THEN #{breakfastValue} " +
                    "WHEN fp.id = 5 THEN #{noFoodValue} " +
            "END * CAST(#{foodImp} AS numeric) AS foodScore, " +
            "COALESCE(AVG(r.rating),5) * 10 * CAST(#{ratingCoeff} AS numeric) AS ratingScore, " +
            "CASE " +
                    "WHEN #{highPrefAirports} LIKE '%' || t.airport || '.%' THEN 50 " +
                    "WHEN #{prefAirports} LIKE '%' || t.airport || '.%' THEN 30 " +
                    "ELSE 0 " +
            "END * CAST(#{airportCoeff} AS numeric) AS airportScore, " +
            "CASE " +
                    "WHEN #{highPrefLocs} LIKE '%' || hot.location || '.%' THEN 80 " +
                    "WHEN #{prefLocs} LIKE '%' || hot.location || '.%' THEN 50 " +
                    "ELSE 0 " +
            "END * CAST(#{locCoeff} AS numeric) AS locationScore, " +
            "CASE " +
                    "WHEN bd.id = 1 THEN 90 " +
                    "WHEN bd.id = 2 THEN 75 " +
                    "WHEN bd.id = 3 THEN 60 " +
                    "WHEN bd.id = 4 THEN 40 " +
            "END * CAST(#{beachDistImp} AS numeric) AS beachDistScore, " +
            "CASE " +
                    "WHEN hot.family_friendly = true THEN 60 " +
                    "ELSE 0 " +
            "END * CAST(#{familyImp} AS numeric) AS familyScore, " +
            "CASE " +
                    "WHEN hot.wifi = true THEN 60 " +
                    "ELSE 0 " +
            "END * CAST(#{wifiImp} AS numeric) AS wifiScore, " +
            "CASE " +
                    "WHEN hot.swimming_pool = true THEN 60 " +
                    "ELSE 0 " +
            "END * CAST(#{poolImp} AS numeric) AS swimmingPoolScore, " +
            "FROM trips t " +
            "JOIN hotels hot ON t.hotel = hot.id " +
            "JOIN food_packages fp ON t.food_package = fp.id " +
            "JOIN airports a ON t.airport = a.id " +
            "JOIN locations l ON hot.location = l.id " +
            "JOIN beach_distance bd ON hot.beach_dist = bd.id " +
            "LEFT JOIN reviews r ON hot.id = r.hotel " +
            "WHERE price <= #{maxPrice} " +
            "AND price >= #{minPrice} " +
            "AND persons >= #{persons} " +
            "AND date_from > #{from} " +
            "AND date_to < #{to} " +
            "AND hot.stars >= #{minStars} " +
            "AND hot.beach_dist <= #{minBeachDist} " +
            "AND (NOT #{familyFilter} OR family_friendly = true) " +
            "AND (NOT #{wifiFilter} OR wifi = true) " +
            "AND (NOT #{poolFilter} OR swimming_pool = true) " +
            "GROUP BY t.id " +
            ") " +
            "SELECT DISTINCT ON (CASE WHEN #{hotelDistinct} THEN hotel ELSE id::TEXT END) " +
            "id, " +
            "price, " +
            "location, " +
            "hotel, " +
            "stars, " +
            "foodPackage, " +
            "airport, " +
            "beachDistance, " +
            "family_friendly, " +
            "wifi, " +
            "swimming_pool, " +
            "averageRating, " +
            "dateFrom, " +
            "dateTo, " +
            "days, " +
            "priceScore, " +
            "locationScore, " +
            "starsScore, " +
            "foodScore, " +
            "ratingScore, " +
            "airportScore, " +
            "beachDistScore, " +
            "familyScore, " +
            "wifiScore, " +
            "swimmingPoolScore, " +
            "(priceScore + locationScore + starsScore + foodScore" +
                    " + ratingScore + airportScore + beachDistScore + familyScore" +
                    " + wifiScore + swimmingPoolScore) AS totalScore " +
            "FROM cte " +
            "WHERE days >= #{minDays} " +
            "AND days <= #{maxDays} " +
            "AND foodScore >= #{minFoodValue} " +
            "AND averageRating >= #{minRating} " +
            "AND (NOT #{airportFilter} OR airportScore > 0) " +
            "AND (NOT #{locFilter} OR locationScore > 0) " +
            "ORDER BY totalScore DESC " +
            "LIMIT #{limit}"
    )
    List<TripDTO> searchTrips(
            float maxPrice,
            float minPrice,
            float priceImportance,
            float locCoeff,
            float starsImp,
            int minStars,
            int minRating,
            float allInclusiveValue,
            float fullBoardValue,
            float halfBoardValue,
            float breakfastValue,
            float noFoodValue,
            float minFoodValue,
            float ratingCoeff,
            float airportCoeff,
            float foodImp,
            float beachDistImp,
            Long minBeachDist,
            float familyImp,
            float wifiImp,
            float poolImp,
            int persons,
            int limit,
            LocalDate from,
            LocalDate to,
            int minDays,
            int maxDays,
            boolean airportFilter,
            boolean locFilter,
            boolean familyFilter,
            boolean wifiFilter,
            boolean poolFilter,
            String highPrefAirports,
            String prefAirports,
            String highPrefLocs,
            String prefLocs,
            boolean hotelDistinct);

    @Select(
            "WITH cte AS (" +
                    "SELECT " +
                    "t.id AS id, " +
                    "t.price AS price, " +
                    "l.name AS location, " +
                    "hot.name AS hotel, " +
                    "hot.stars AS stars, " +
                    "a.name || ' (' || a.iata || ')' AS airport, " +
                    "fp.name AS foodPackage, " +
                    "bd.name AS beachDistance, " +
                    "hot.family_friendly AS family_friendly, " +
                    "hot.wifi AS wifi, " +
                    "hot.swimming_pool AS swimming_pool, " +
                    "COALESCE(AVG(r.rating),0) AS averageRating, " +
                    "t.date_from AS dateFrom, " +
                    "t.date_to AS dateTo, " +
                    "t.date_to - t.date_from AS days, " +
                    "(#{maxPrice}-t.price) * 0.005 * #{priceImportance} AS priceScore, " +
                    "CASE " +
                    "WHEN fp.id = 1 THEN #{allInclusiveValue} " +
                    "WHEN fp.id = 2 THEN #{fullBoardValue} " +
                    "WHEN fp.id = 3 THEN #{halfBoardValue} " +
                    "WHEN fp.id = 4 THEN #{breakfastValue} " +
                    "WHEN fp.id = 5 THEN #{noFoodValue} " +
                    "END * CAST(#{foodImp} AS numeric) AS foodScore, " +
                    "CASE " +
                    "WHEN t.airport = ANY(SELECT airport FROM airport_prefs WHERE high_pref IS TRUE) THEN 50 " +
                    "WHEN t.airport = ANY(SELECT airport FROM airport_prefs WHERE high_pref IS NOT TRUE) THEN 30 " +
                    "ELSE 0 " +
                    "END * CAST(#{airportCoeff} AS numeric) AS airportScore, " +
                    "FROM trips t " +
                    "JOIN hotels hot ON t.hotel = hot.id " +
                    "JOIN food_packages fp ON t.food_package = fp.id " +
                    "JOIN airports a ON t.airport = a.id " +
                    "JOIN locations l ON hot.location = l.id " +
                    "JOIN beach_distance bd ON hot.beach_dist = bd.id " +
                    "LEFT JOIN reviews r ON hot.id = r.hotel " +
                    "WHERE price <= #{maxPrice} " +
                    "AND price >= #{minPrice} " +
                    "AND persons >= #{persons} " +
                    "AND date_from > #{from} " +
                    "AND date_to < #{to} " +
                    "AND hot.id = #{hotel} " +
                    "GROUP BY t.id " +
                    ") " +
                    "SELECT " +
                    "id, " +
                    "price, " +
                    "location, " +
                    "hotel, " +
                    "stars, " +
                    "foodPackage, " +
                    "airport, " +
                    "beachDistance, " +
                    "family_friendly, " +
                    "wifi, " +
                    "swimming_pool, " +
                    "averageRating, " +
                    "dateFrom, " +
                    "dateTo, " +
                    "days, " +
                    "priceScore, " +
                    "foodScore, " +
                    "airportScore, " +
                    "(priceScore + foodScore + airportScore) AS totalScore " +
                    "FROM cte " +
                    "WHERE days >= #{minDays} " +
                    "AND days <= #{maxDays} " +
                    "AND foodScore >= #{minFoodValue} " +
                    "AND (NOT #{airportFilter} OR airportScore > 0) " +
                    "ORDER BY totalScore DESC " +
                    "LIMIT #{limit}"
    )
    List<TripByHotelDTO> searchTripsByHotel(
            float maxPrice,
            float minPrice,
            float priceImportance,
            float allInclusiveValue,
            float fullBoardValue,
            float halfBoardValue,
            float breakfastValue,
            float noFoodValue,
            float minFoodValue,
            float airportCoeff,
            float foodImp,
            int persons,
            int limit,
            LocalDate from,
            LocalDate to,
            int minDays,
            int maxDays,
            boolean airportFilter,
            Long hotel);

    @Select("SELECT " +
            "t.id AS id, " +
            "t.price AS price, " +
            "l.name AS location, " +
            "l.id AS locationId, " +
            "hot.name AS hotel, " +
            "hot.stars AS stars, " +
            "fp.name AS foodPackage, " +
            "a.name || ' (' || a.iata || ')' AS airport, " +
            "a.id AS airportId, " +
            "bd.name AS beachDistance, " +
            "hot.family_friendly AS family_friendly, " +
            "hot.wifi AS wifi, " +
            "hot.swimming_pool AS swimming_pool, " +
            "COALESCE(AVG(r.rating),0) AS averageRating, " +
            "t.date_from AS dateFrom, " +
            "t.date_to AS dateTo, " +
            "t.date_to - t.date_from AS days, " +
            "FROM trips t " +
            "JOIN hotels hot ON t.hotel = hot.id " +
            "JOIN food_packages fp ON t.food_package = fp.id " +
            "JOIN airports a ON t.airport = a.id " +
            "JOIN locations l ON hot.location = l.id " +
            "JOIN beach_distance bd ON hot.beach_dist = bd.id " +
            "LEFT JOIN reviews r ON hot.id = r.hotel " +
            "WHERE t.id = #{id} " +
            "GROUP BY t.id")
    TripPureDTO getPureTripById(Long id);

    @Select("SELECT " +
            "t.id AS id, " +
            "t.price AS price, " +
            "l.name AS location, " +
            "l.id AS locationId, " +
            "hot.name AS hotel, " +
            "hot.stars AS stars, " +
            "fp.name AS foodPackage, " +
            "a.name || ' (' || a.iata || ')' AS airport, " +
            "a.id AS airportId, " +
            "bd.name AS beachDistance, " +
            "hot.family_friendly AS family_friendly, " +
            "hot.wifi AS wifi, " +
            "hot.swimming_pool AS swimming_pool, " +
            "COALESCE(AVG(r.rating),5) AS averageRating, " +
            "t.date_from AS dateFrom, " +
            "t.date_to AS dateTo, " +
            "DATEDIFF('DAY', t.date_from, t.date_to) AS days " +
            "FROM trips t " +
            "JOIN hotels hot ON t.hotel = hot.id " +
            "JOIN food_packages fp ON t.food_package = fp.id " +
            "JOIN airports a ON t.airport = a.id " +
            "JOIN locations l ON hot.location = l.id " +
            "JOIN beach_distance bd ON hot.beach_dist = bd.id " +
            "LEFT JOIN reviews r ON hot.id = r.hotel " +
            "WHERE t.date_from > #{from} " +
            "AND t.date_to < #{to} " +
            "AND DATEDIFF('DAY', t.date_from, t.date_to) BETWEEN #{minDays} AND #{maxDays} " +
            "AND t.persons >= #{persons} " +
            "AND t.price <= #{maxPrice} " +
            "AND t.price >= #{minPrice} " +
            "AND hot.stars >= #{minStars} " +
            "AND #{allowedFoodPackages} LIKE '%' || t.food_package || '.%' " +
            "AND hot.beach_dist <= #{minBeachDist} " +
            "AND (NOT #{airportFilter} OR #{prefAirports} LIKE '%' || t.airport || '.%') " +
            "AND (NOT #{locFilter} OR #{prefLocs} LIKE '%' || hot.location || '.%') " +
            "AND (NOT #{familyFilter} OR hot.family_friendly = true) " +
            "AND (NOT #{wifiFilter} OR hot.wifi = true) " +
            "AND (NOT #{poolFilter} OR hot.swimming_pool = true) " +
            "GROUP BY t.id " +
            "HAVING COALESCE(AVG(r.rating),5) >= #{minRating}")
    List<TripPureDTO> getPureTrips(
            LocalDate from,
            LocalDate to,
            int minDays,
            int maxDays,
            float maxPrice,
            float minPrice,
            int persons,
            int minStars,
            int minRating,
            String allowedFoodPackages,
            int minBeachDist,
            boolean airportFilter,
            boolean locFilter,
            boolean familyFilter,
            boolean wifiFilter,
            boolean poolFilter,
            String prefAirports,
            String prefLocs
    );

}
