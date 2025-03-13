package com.springboot.controller;


import com.springboot.common.Result;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.springboot.service.AdminService;
import com.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
//@RequestMapping("/web")
public class WebController {

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account dbAccount=null;
        if("ROLE_ADMIN".equals(account.getRole())){
            dbAccount=adminService.login(account);
        }
        else if("ROLE_USER".equals(account.getRole())){
            dbAccount=userService.login(account);
        }

        return Result.success(dbAccount);
    }
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();
    }
    @PostMapping("/info")
    public Account getUserInfo(@RequestBody Account account) {
        Account dbAccount=null;
        if ("ROLE_ADMIN".equals(account.getRole())) {
            dbAccount=adminService.getUserInfo(account.getName());
        } else if ("ROLE_USER".equals(account.getRole())) {
            dbAccount=userService.getUserInfo(account.getName());
        }
        return dbAccount;
    }


    @PostMapping("/changePassword")
    public boolean changePassword(@RequestBody Account account) {
        Boolean dbAccount=false;
        if ("ROLE_ADMIN".equals(account.getRole())) {
            dbAccount= adminService.changePassword(account.getName(), account.getPassword());
        } else if ("ROLE_USER".equals(account.getRole())) {
            dbAccount= userService.changePassword(account.getName(), account.getPassword());
        }
        return dbAccount;
    }
}
