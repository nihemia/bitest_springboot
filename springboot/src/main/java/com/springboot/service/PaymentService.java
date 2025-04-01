package com.springboot.service;

// PaymentService.java
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.entity.Admin;
import com.springboot.entity.Payment;
import com.springboot.entity.User;
import com.springboot.exception.CustomException;
import com.springboot.mapper.PaymentMapper;
import com.springboot.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    @Resource
    private PaymentMapper paymentMapper;
    @Resource
    private UserMapper userMapper;

//    public List<Payment> getPaymentsByUserId(Integer ownerId) {
//        return paymentMapper.findByUserId(ownerId);
//    }
//
//    public Payment createPayment(Integer ownerId, BigDecimal amount, String paymentMethod) {
////        User owner = userMapper.findById(ownerId).orElseThrow(() -> new RuntimeException("User not found"));
//        User owner = userMapper.selectUserById(ownerId);
//        Payment payment = new Payment();
//        payment.setOwnerName(owner.getName());
//        payment.setAmount(amount);
//        payment.setPaymentMethod(paymentMethod);
//        return paymentMapper.save(payment);
//    }
//
//
//
//    public void deletePayment(Integer paymentId) {
//        paymentMapper.deleteById(paymentId);
//    }
//
//    public Payment updatePayment(Integer paymentId, BigDecimal amount, String paymentMethod) {
//        Payment payment = paymentMapper.findById(paymentId);
//        payment.setAmount(amount);
//        payment.setPaymentMethod(paymentMethod);
//        return paymentMapper.save(payment);
//    }
//
//
//
////    public PageInfo<Payment> getAllPayments(Params params) {
////        PageHelper.startPage(params.getPageNum(), params.getPageSize());
////        List<Payment> list=paymentMapper.findbySearch(params);
////        return PageInfo.of(list);
////    }
//    public List<Payment> getAllPayments() {
////    List<Payment> list=paymentMapper.findbySearch(params);
//    List<Payment> list=paymentMapper.findAll();
//    return list;
//}
//
//    public Payment getPaymentsByOwnerId(Integer ownerId) {
//        return paymentMapper.findById(ownerId);
//    }
//
//    public void add(Payment payment) {
//        paymentMapper.add(payment);
//        System.out.println("service——————————/admin/add");
//    }


    public void addPayment(Payment payment) {
        System.out.println("service"+payment.getPaymentItem()+payment.getOwnerId()+payment.getAmount());
        paymentMapper.addPayment(payment);
    }

//    @Transactional
//    public void payPayment(Integer id) {
//        Payment payment = getPaymentById(id);
//        paymentMapper.payPayment(id);
//        paymentMapper.updateOwnerTotalAmount(payment.getOwnerId(), payment.getAmount());
//    }

//    public List<Payment> getPaymentsByOwnerName(Integer ownerId) {
//        return paymentMapper.getPaymentsByOwnerName(ownerId);
//    }

    public List<Payment> findById(Integer ownerId) {
        // 这里需要实现根据 ID 获取 Payment 的逻辑
        return paymentMapper.findById(ownerId);
    }

    public PageInfo<Payment> findbySearch(Params params) {
        //  开启分页查询, 当执行查询时，插件进行相关的sql拦截进行分页操作，返回一个page对象
        System.out.println("搜索名字："+params.getName());
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Payment> list=paymentMapper.findbySearch(params);
        return PageInfo.of(list);
//            return adminMapper.findbysearch(params);
    }


    public void update(Payment payment) {
        paymentMapper.updateByPrimaryKey(payment);
    }

    public void delete(Integer id) {
        paymentMapper.deleteByPrimaryKey(id);
    }

    public void payPayment(Integer id) {
        Payment payment = getPaymentById(id);
        User user =userMapper.selectUserById(payment.getOwnerId());
//        System.out.println("业主id++++++++"+payment.getOwnerId());
//        System.out.println("业主id++++++++"+user.getName());

//        Double total=user.getTotalMoney()-payment.getAmount();
//        System.out.println("缴费前余额"+user.getTotalMoney()+"缴费金额："+payment.getAmount());
//        System.out.println("缴费后"+total);
        if(user.getTotalMoney()-payment.getAmount()>0){
            paymentMapper.payPayment(id);
            paymentMapper.updateOwnerTotalAmount(payment.getOwnerId(), payment.getAmount());
        }
        else{
            throw new CustomException("余额不足，请充值");
        }
    }

    private Payment getPaymentById(Integer id) {
        return paymentMapper.getPaymentById(id);
    }

}
