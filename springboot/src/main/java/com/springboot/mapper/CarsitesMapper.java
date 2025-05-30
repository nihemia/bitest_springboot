package com.springboot.mapper;


import com.springboot.common.Params;
import com.springboot.entity.CarSpace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
public interface CarsitesMapper extends Mapper<CarSpace> {

    List<CarSpace> findBySearch(@Param("params") Params params);

    @Select("select * from carsties where csNum=#{csNum} limit 1")
    CarSpace findByNum(@Param("csNum") Integer csNum);
}
