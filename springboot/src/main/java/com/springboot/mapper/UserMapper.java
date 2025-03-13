package com.springboot.mapper;


import com.springboot.common.Params;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.springboot.entity.User;
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
public interface UserMapper extends Mapper<User> {

    //1、基于注解的方法
    //@Select("selsct * form user")
    List<User> getUser();


    List<User> findbySearch(@Param("params") Params params);

    @Select("select * from user where name=#{name} limit 1")
    User findByName(@Param("name") String name);

    @Select("select * from user where name=#{name} and password=#{password} limit 1")
    User findByNameAndPassword(@Param("name")String name, @Param("password")String password);

    @Update("update user set password=#{password} where name=#{name}")
    void save(String name ,String password);
}
