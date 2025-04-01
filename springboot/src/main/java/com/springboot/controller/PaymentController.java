package com.springboot.controller;

// PaymentController.java
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.common.Result;
import com.springboot.entity.Admin;
import com.springboot.entity.CarSpace;
import com.springboot.entity.Notice;
import com.springboot.entity.Payment;
import com.springboot.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    //添加缴费信息
    @PostMapping
    public Result addPayment(@RequestBody Payment payment) {
        System.out.println(payment.getPaymentItem()+payment.getAmount());
        if(payment.getId()==null){
            paymentService.addPayment(payment);
        }
       else{
           paymentService.update(payment);
        }
        return Result.success();
    }

    //分页查询
    @GetMapping("/search")
    public Result findBySearch(Params params){

        PageInfo<Payment> info=paymentService.findbySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        paymentService.delete(id);
        return Result.success();
    }

    //业主获得主机的缴费信息
    @GetMapping("/owner/{ownerId}")
    public Result getOwnerPayment(@PathVariable Integer ownerId) {
        List<Payment> payment = paymentService.findById(ownerId);
        System.out.println(payment.get(0).getAmount()+payment.get(0).getOwnerId());
        return Result.success(payment);
    }


    //业主进行缴费
    @PutMapping("/{id}/pay")
    public Result payPayment(@PathVariable Integer id) {
        paymentService.payPayment(id);
        return Result.success();
    }

}