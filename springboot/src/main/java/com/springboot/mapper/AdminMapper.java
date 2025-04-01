package com.springboot.mapper;



import com.springboot.common.Params;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
public interface AdminMapper extends Mapper<Admin> {

    //1、基于注解的方法
    //@Select("selsct * form user")
    List<Admin> getAdmin();


    List<Admin> findbySearch(@Param("params") Params params);

    @Select("select * from admin where name=#{name} limit 1")
    Admin findByName(@Param("name") String name);

    @Select("select * from admin where name=#{name} and password=#{password} limit 1")
    Admin findByNameAndPassword(@Param("name")String name, @Param("password")String password);

    @Update("update admin set password=#{password} where name=#{name}")
    void save(String name ,String password);

    @Select("select * from admin where id=#{id} limit 1")
    Admin selectUserById(Integer id);

    @Select("select * from admin")
    List<Admin> selectAllName();
}
