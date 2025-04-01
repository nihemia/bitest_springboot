package com.springboot.mapper;

import com.springboot.common.Params;
import com.springboot.entity.CarSpace;
import com.springboot.entity.House;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface HouseMapper extends Mapper<House> {

    List<House> findAll();

    List<House> findAllName();

    House findById(Integer id);

//    void save(CarSpace carSpace);
//
//    void update(CarSpace carSpace);

    void delete(Integer id);

    List<House> findByOwnerId(Integer ownerId);

    List<House> findbySearch(@Param("params") Params params);

    @Insert("insert into house(house_number,status,houseDate,owner_id,houseBuild) values(#{houses.houseNumber},#{houses.status},#{houses.houseDate},#{houses.ownerId},#{houses.houseBuild})")
    void insertHouse(@Param("houses") House houses);

    @Update("update house set house_number=#{houses.houseNumber}, status=#{houses.status},houseDate=#{houses.houseDate},owner_id=#{houses.ownerId},houseBuild=#{houses.houseBuild} where id=#{houses.id}")
    void updateeHouse(@Param("houses")House houses);
}