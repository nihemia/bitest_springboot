package com.springboot.controller;


//import com.github.pagehelper.PageInfo;
//import com.springboot.common.Params;
//import com.springboot.common.Result;
//import com.springboot.entity.Admin;
//import com.springboot.service.AdminService;
//import org.springframework.web.bind.annotation.*;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.common.Result;
import com.springboot.entity.Notice;
import com.springboot.entity.Type;
import com.springboot.exception.CustomException;
import com.springboot.service.NoticeService;
import com.springboot.service.TypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/notice")
public class NoticeController {
@Resource
    private NoticeService noticeService;

    @GetMapping
    public Result findAll(){
        List<Notice> list=noticeService.findAll();
        return Result.success(list);
    }

@PostMapping
    public  Result update(@RequestBody Notice notice){
        if(ObjectUtil.isEmpty(notice.getId())){
            noticeService.add(notice);
        }
        else{
            noticeService.update(notice);
        }

        return Result.success();
}
    @GetMapping("/search")
    public Result findBySearch(Params params){

        PageInfo<Notice> info=noticeService.findbySearch(params);
        return Result.success(info);
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        noticeService.delete(id);
        return Result.success();
    }
}
