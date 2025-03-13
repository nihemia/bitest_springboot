package com.springboot.mapper;


import com.springboot.common.Params;
import com.springboot.entity.Book;
import com.springboot.entity.Type;
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
public interface TypeMapper extends Mapper<Type> {


    List<Type> findbySearch(@Param("params") Params params);

    @Select("select * from type where name=#{name} limit 1")
    Type findByName(@Param("name") String name);
}
