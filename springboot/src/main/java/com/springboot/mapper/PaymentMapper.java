package com.springboot.mapper;

import com.springboot.common.Params;
import com.springboot.entity.Admin;
import com.springboot.entity.Payment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMapper extends Mapper<Payment> {

    @Insert("INSERT INTO payments(owner_id, payment_item, amount, description, status,payment_time) VALUES (#{ownerId}, #{paymentItem}, #{amount}, #{description}, '未缴费',null)")
    void addPayment(Payment payment);

    @Update("UPDATE payments SET status = '已缴费', payment_time = NOW() WHERE id = #{id}")
    void payPayment(Integer id);


    List<Payment> getPaymentsByOwnerName(Integer ownerId);

    @Update("UPDATE user SET total_money = total_money - #{amount} WHERE id = #{ownerId}")
    void updateOwnerTotalAmount(Integer ownerId, Double amount);


    List<Payment> findbySearch(@Param("params") Params params);

    //根据业主查询业主缴费信息

    List<Payment> findById(Integer ownerId);

    @Select("SELECT * FROM payments WHERE id = #{id}")
    Payment getPaymentById(Integer id);
}
