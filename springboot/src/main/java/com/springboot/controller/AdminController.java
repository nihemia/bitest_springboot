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
import com.springboot.entity.Admin;
import com.springboot.entity.User;
import com.springboot.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log= LoggerFactory.getLogger(AdminService.class);

    @Resource
    private AdminService adminService;

//    @PostMapping("/login")
//    public Result login(@RequestBody Admin admin){
//        Admin loginUser=adminService.login(admin);
//        return Result.success(loginUser);
//    }
//    @PostMapping("/register")
//    public Result register(@RequestBody Admin admin){
//        adminService.add(admin);
//        return Result.success();
//    }
    @PostMapping
    public Result save(@RequestBody Admin admin){

        if(admin.getId()==null){
            adminService.add(admin);
        }
        else {
            adminService.update(admin);

        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
//    @PostMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.delete(id);
        return Result.success();
    }
    @GetMapping
    public Result findAll() {
        List<Admin> list=adminService.findAll();
        return Result.success(list);
    }
    @GetMapping("/search")
    public Result findBySearch(Params params){
        log.info("拦截器已放行，正式调用接口内部，查询管理员信息");
        PageInfo<Admin> info=adminService.findbySearch(params);
        return Result.success(info);
    }


    @GetMapping("/names")
    public Result findAllName() {
        List<Admin> list=adminService.findAllName();
        return Result.success(list);
    }




}
