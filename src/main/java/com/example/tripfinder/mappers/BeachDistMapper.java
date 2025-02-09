package com.example.tripfinder.mappers;

import com.example.tripfinder.model.BeachDistDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BeachDistMapper {

    @Select("SELECT id, name FROM beach_distance")
    List<BeachDistDTO> getBeachDistances();
}
