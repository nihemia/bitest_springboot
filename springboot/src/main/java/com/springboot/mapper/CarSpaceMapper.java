package com.springboot.mapper;

import com.springboot.common.Params;
import com.springboot.entity.CarSpace;
import com.springboot.entity.Payment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CarSpaceMapper extends Mapper<CarSpace> {

    List<CarSpace> findAll();

    List<CarSpace> findAllName();

    CarSpace findById(Integer id);

//    void save(CarSpace carSpace);
//
//    void update(CarSpace carSpace);

    void delete(Integer id);

    List<CarSpace> findByOwnerId(Integer ownerId);

    List<CarSpace> findbySearch(@Param("params") Params params);
}