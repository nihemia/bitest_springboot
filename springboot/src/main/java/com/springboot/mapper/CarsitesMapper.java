package com.springboot.mapper;


import com.springboot.common.Params;
import com.springboot.entity.Admin;
import com.springboot.entity.Book;
import com.springboot.entity.Carsites;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */

@Repository
//@Mapper
//public interface AdminMapper extends BaseMapper<Admin> {
public interface CarsitesMapper extends Mapper<Carsites> {

    List<Carsites> findBySearch(@Param("params") Params params);

    @Select("select * from carsties where csNum=#{csNum} limit 1")
    Carsites findByNum(@Param("csNum") Integer csNum);
}
