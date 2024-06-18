package com.example.tripfinder.mappers;

import com.example.tripfinder.model.TripDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TripMapper {

    @Select("SELECT id, price FROM trips")
    List<TripDTO> findAll();
}
