package com.springboot.mapper;


import com.springboot.common.Params;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Optional;

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

    @Select("select * from user where id=#{id} limit 1")
    User selectUserById(Integer id);

    @Select("select * from user")
    List<User> selectAllName();
    @Update("UPDATE user  SET name =#{user.name},sex = #{user.sex},age = #{user.age},phone = #{user.phone} WHERE  id = #{user.id}")
    void updateOwnerinfo(@Param("user") User user);

    @Update("update user set pay_money=#{user.payMoney},pay_way=#{user.payWay} where id=#{user.id}")
    void insertPaymoney(@Param("user") User user);

    @Update("UPDATE user SET total_money = total_money + #{users.payMoney} WHERE id = #{users.id}")
    void payByowner(@Param("users")User users);

    @Insert("insert into user(name,password,sex,phone,age,role,total_money) values(#{user.name},#{user.password},#{user.sex},#{user.phone},#{user.age},'ROLE_USER',0)")
    void insertByowner(@Param("user")User user);
}