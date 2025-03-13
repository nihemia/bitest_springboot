package com.springboot.mapper;


import com.springboot.common.Params;

import com.springboot.entity.Admin;
import com.springboot.entity.Book;
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
public interface BookMapper extends Mapper<Book> {


    List<Book> findBySearch(@Param("params") Params params);

    @Select("select * from book where name=#{name} limit 1")
    Book findByName(@Param("name") String name);
}
