package com.springboot.controller;


//import com.github.pagehelper.PageInfo;
//import com.springboot.common.Params;
//import com.springboot.common.Result;
//import com.springboot.entity.Admin;
//import com.springboot.service.AdminService;
//import org.springframework.web.bind.annotation.*;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.springboot.common.FileController;
import com.springboot.common.Params;
import com.springboot.common.Result;
import com.springboot.entity.Admin;
import com.springboot.entity.Book;
import com.springboot.entity.Type;
import com.springboot.exception.CustomException;
import com.springboot.service.BookService;
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
@RequestMapping("/type")
public class TypeController {
@Resource
    private TypeService typeService;

    @GetMapping
    public Result findAll(){
        return Result.success(typeService.findAll());
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){

        PageInfo<Type> info=typeService.findbySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Type type){
        if(type.getId()==null){
            typeService.add(type);
        }
        else {
            typeService.update(type);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        typeService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result deleteBatch(@RequestBody List<Type> list){
        for(Type type:list){
            typeService.delete(type.getId());
        }
        return Result.success();
    }

    //批量文件导入导出接口
    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws IOException {

        //要一行一行组装数据，再放到list集合中
        //每一行数据，起始对应数据库中的一行数据，也就是对应Java中的实体类
        //怎么找到某一列对应某表头？ 通过键值对的形式，即Map<key,value>，把Map塞入list里

        //1、从数据库中查询所有数据
        List<Type> all=typeService.findAll();
        if(CollUtil.isEmpty(all)){
            throw new CustomException("未找到数据");
        }
        //2、定义List，存储处理后的数据，用于塞入list中
        List<Map<String,Object>> list=new ArrayList<>(all.size());
        //3、定义Map<key,value>，遍历每一条数据，然后封装到Map<key,value>，再把map塞入list
        for (Type type:all ) {
            Map<String,Object> row=new HashMap<>();
            row.put("分类名称",type.getName());
            row.put("分类描述",type.getDescription());
            list.add(row);
        }
        //4、创建一个ExcelWriter，把list数据用这个writer写出来(生成出来)
        ExcelWriter writer= ExcelUtil.getWriter(true);
        writer.write(list,true);
        //5、把excel下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=type.xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        IoUtil.close(System.out);

       return Result.success();
    }

    // /api/type/upload接口处理excel批量文件上传功能
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException{
        //将excel中的数据转为list对象
            List<Type> infoList=ExcelUtil.getReader(file.getInputStream()).readAll(Type.class);
            if(!CollectionUtil.isEmpty(infoList)) {
                for (Type type : infoList) {
                    try {
                        //将excel中的数据进行添加到数据库中
                        typeService.add(type);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        return Result.success();
    }

}
