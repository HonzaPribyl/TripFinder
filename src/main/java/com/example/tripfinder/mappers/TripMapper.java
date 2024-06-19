package com.example.tripfinder.mappers;

import com.example.tripfinder.model.TripDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TripMapper {

    @Select(
            "SELECT id, price, ((#{maxPrice}-price) * #{priceImportance}) AS score " +
                    "FROM trips " +
                    "WHERE price <= #{maxPrice} " +
                    "ORDER BY score DESC " +
                    "LIMIT #{limit}"
    )
    List<TripDTO> findAll(
            float maxPrice,
            float priceImportance,
            int limit);
}
