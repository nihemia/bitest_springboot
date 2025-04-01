package com.springboot.mapper;

import com.springboot.common.Params;
import com.springboot.entity.CarSpace;
import com.springboot.entity.MaintenanceRequest;
import com.springboot.entity.Payment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRequestMapper extends tk.mybatis.mapper.common.Mapper<Payment> {
//    /**
//     * 插入一条维修请求记录
//     * @param request 维修请求对象
//     */
//    void insertMaintenance(MaintenanceRequest request);
//
//    /**
//     * 根据 ID 删除维修请求记录
//     * @param id 维修请求 ID
//     */
//    void deleteByPrimaryKey(Integer id);
//
//    /**
//     * 根据 ID 更新维修请求记录
//     * @param request 维修请求对象
//     */
//    void updateByPrimaryKeySelective(MaintenanceRequest request);
//
//    /**
//     * 查询所有维修请求记录
//     * @return 维修请求列表
//     */
//    List<MaintenanceRequest> findAll();
//
//    /**
//     * 根据 ID 查询维修请求记录
//     * @param id 维修请求 ID
//     * @return 维修请求对象
//     */
//    MaintenanceRequest selectByPrimaryKey(Integer id);

//    @Insert("INSERT INTO weixiu(userid, room_number, description, status,request_time) VALUES (#{maintenance.userId}, #{maintenance.roomNumber}, #{maintenance.description}, '未处理',NOW())")
    @Insert("INSERT INTO weixiu(userid, room_number, description, status,request_time) VALUES (#{uid}, #{maintenance.roomNumber}, #{maintenance.description}, '未处理',NOW())")
    void addMaintenance(@Param("maintenance") MaintenanceRequest maintenance,Integer uid);
//    void addMaintenance(@Param("maintenance") MaintenanceRequest maintenance);

//    @Update("UPDATE weixiu SET status = '已处理', complate_time = NOW()  WHERE id = #{maintenance.id}")
    @Update("UPDATE weixiu SET status = '已处理', complate_time = NOW(),adminid=#{maintenance.adminId}  WHERE id = #{maintenance.id}")
    void updateMaintenance(@Param("maintenance")MaintenanceRequest maintenance);

    List<MaintenanceRequest> getPaymentsByOwnerName(Integer ownerId);


    List<MaintenanceRequest> findbySearch(@Param("params") Params params);

    //根据业主查询业主缴费信息
    List<MaintenanceRequest> findById(Integer ownerId);

    @Select("SELECT * FROM weixiu WHERE id = #{id}")
    MaintenanceRequest findRequestById(Integer id);

//    @Select("SELECT * FROM weixiu WHERE userid = #{ownerId}")
//    List<MaintenanceRequest> findByOwnerId(Integer ownerId);
    List<MaintenanceRequest> getByOwnerName(Integer ownerId);

    @Delete(" DELETE FROM weixiu WHERE id = #{id}")
    void deleteByMainId(Integer id);

    List<MaintenanceRequest> findAll();
}