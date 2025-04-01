package com.springboot.controller;


//import com.github.pagehelper.PageInfo;
//import com.springboot.common.Params;
//import com.springboot.common.Result;
//import com.springboot.entity.Admin;
//import com.springboot.service.AdminService;
//import org.springframework.web.bind.annotation.*;


import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.common.Result;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.springboot.entity.User;
import com.springboot.service.AdminService;
import com.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.acl.Owner;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log= LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserService userService;

//    @PostMapping("/login")
//    public Result login(@RequestBody User user){
//
//        User loginUser=userService.login(user);
//        return Result.success(loginUser);
//    }
//    @PostMapping("/register")
//    public Result register(@RequestBody User user){
//        userService.add(user);
//        return Result.success();
//    }
    @PostMapping
    public Result save(@RequestBody User user){
        System.out.println(user);
        if(user.getId()==null){
            userService.add(user);
        }
        else {
            userService.update(user);
            System.out.println(user.getPhone());
        }
        return Result.success();
    }

    @PostMapping("/info")
    public Result updateinfo(@RequestBody User user){
        System.out.println(user);
        userService.updateOwnerinfo(user);
        System.out.println(user.getPhone());
        return Result.success();
    }
    @PostMapping("/pay")
    public Result OnwerPay(@RequestBody User user){
        System.out.println("/user/pay");
        System.out.println("充钱金额："+user.getPayMoney());
        System.out.println("充钱金额的id："+user.getId());
        System.out.println("总金额："+user.getTotalMoney());
        System.out.println(user.getPayWay());
        userService.insertPaymoney(user);
        userService.payByowner(user);
        return Result.success();
    }


    @DeleteMapping("/{id}")
//    @PostMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        userService.delete(id);
        return Result.success();
    }
    @GetMapping
    public Result findAll() {
        List<User> list=userService.findAll();
        return Result.success(list);
    }
    @GetMapping("/search")
    public Result findBySearch(Params params){
        log.info("拦截器已放行，正式调用接口内部，查询管理员信息");
        PageInfo<User> info=userService.findbySearch(params);
        return Result.success(info);
    }
    @GetMapping("/names")
    public Result findAllName() {
        List<User> list=userService.findAllName();
        return Result.success(list);
    }




//    @PostMapping("/changePassword")
//    public String changePassword(@RequestBody Map<String, String> request) {
//        System.out.println("修改密码的用户是："+request.get("username"));
//        System.out.println("修改的旧密码是"+request.get("oldPassword"));
//        System.out.println("修改的新密码是"+request.get("newPassword"));
//        String username = request.get("username");
//        String oldPassword = request.get("oldPassword");
//        String newPassword = request.get("newPassword");
//
//        if (userService.checkPassword(username, oldPassword)) {
//            userService.updatePassword(username, newPassword);
//            return "密码修改成功";
//        } else {
//            return "原密码错误";
//        }
//    }
}
