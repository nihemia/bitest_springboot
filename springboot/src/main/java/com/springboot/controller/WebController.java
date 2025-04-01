package com.springboot.controller;


import com.springboot.common.Result;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.springboot.entity.User;
import com.springboot.service.AdminService;
import com.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@CrossOrigin
@RestController
//@RequestMapping("/web")
public class WebController {

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        System.out.println(account.getRole()+account.getName()+account.getId());
        Account dbAccount=null;
        System.out.println("/login"+account.getId()+account.getName());
        if("ROLE_ADMIN".equals(account.getRole())){
            dbAccount=adminService.login(account);
            System.out.println(account.getRole());
        }
        else if("ROLE_USER".equals(account.getRole())){
            dbAccount=userService.login(account);
            System.out.println("WebCOntroller"+account.getId());
        }

        return Result.success(dbAccount);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }
    @PostMapping("/info")
    public Account getUserInfo(@RequestBody Account account) {
        System.out.println("/info");
        Account dbAccount=null;
        System.out.println("/info"+account.getRole());
        if ("ROLE_ADMIN".equals(account.getRole())) {
            dbAccount=adminService.getUserInfo(account.getName());
        } else if ("ROLE_USER".equals(account.getRole())) {
            dbAccount=userService.getUserInfo(account.getName());
            System.out.println("dbA的总余额"+dbAccount.getTotalMoney());
        }
        return dbAccount;
    }


    @PostMapping("/changePassword")
//    public boolean changePassword(@RequestBody Account account) {
    public Result changePassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        String role = request.get("role");
        System.out.println("旧密码"+oldPassword);
        System.out.println("新密码"+newPassword);
        System.out.println("身份"+role);
//        Boolean success=false;
        Result success = null;
        if ("ROLE_ADMIN".equals(role)) {
            if(adminService.checkPassword(username,oldPassword)) {
               adminService.updatePassword(username, newPassword);
                success=Result.success("修改成功");
                System.out.println(success);
            }
        } else if ("ROLE_USER".equals(role)) {
            if(userService.checkPassword(username,oldPassword)) {
               userService.updatePassword(username, newPassword);
                success=Result.success("修改成功");
                System.out.println(success);
            }
        }
        else{
            return success=Result.error("姓名密码不正确");
        }
        return success;
    }

//        String oldPassword = account.getOldpassword(); // 假设前端传递原密码
//        String newPassword = account.getPassword();
//        System.out.println("旧密码"+oldPassword);
//        System.out.println("新密码"+newPassword);
//
//        if ("ROLE_ADMIN".equals(account.getRole())) {
//            if(adminService.checkPassword(account.getName(),oldPassword)) {
//                success = adminService.changePassword(account.getName(), account.getPassword());
//            }
//        } else if ("ROLE_USER".equals(account.getRole())) {
//            if(userService.checkPassword(account.getName(),oldPassword)) {
//                success = userService.changePassword(account.getName(), account.getPassword());
//            }
//        }
//        return success;
//    }
}
