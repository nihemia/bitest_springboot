package com.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.common.Result;
import com.springboot.entity.CarSpace;
import com.springboot.entity.Payment;
import com.springboot.service.CarSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car-space")
public class CarSpaceController {
    @Autowired
    private CarSpaceService carSpaceService;

    //查询业主姓名
    @GetMapping
    public Result findAllName(){
        return Result.success(carSpaceService.findAllName());
    }


    //通业主姓名统计车位数量，利用饼图统计
    @GetMapping("/echarts/btu")
    public Result getAllbtu() {
        List<CarSpace> list = carSpaceService.findAll();
        //x是list集合中的某个，相当于for循环的i
        //。stream打散——>.filter再过滤空数据——>再组合，依据OwnerName进行分组作为key，再计算数量作为value，返回类型是Map集合
        Map<String,Long> collect=list.stream()
                .filter(x-> ObjectUtil.isNotEmpty(x.getOwnerName()))
                .collect(Collectors.groupingBy(CarSpace::getOwnerName,Collectors.counting()));
        //最后返回前端的数据结构
        List<Map<String,Object>> maplist=new ArrayList<>();
        if(CollUtil.isNotEmpty(collect)){
            for (String key: collect.keySet()){
                Map<String,Object> map=new HashMap<>();
                map.put("name",key);
                map.put("value",collect.get(key));
                maplist.add(map);
            }
        }
        return Result.success(maplist);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<CarSpace> info=carSpaceService.findbySearch(params);
        return Result.success(info);
    }

    // 管理员获取所有车位信息
    @GetMapping("/admin/all")
    public Result getAllCarSpaces() {
        List<CarSpace> carSpaces = carSpaceService.findAll();
        return Result.success(carSpaces);
    }

    // 业主获取自己的车位信息
    @GetMapping("/owner/{ownerId}")
    public Result getCarSpacesByOwnerId(@PathVariable Integer ownerId) {
        System.out.println(ownerId);
        List<CarSpace> carSpaces = carSpaceService.findByOwnerId(ownerId);
        return Result.success(carSpaces);
    }

    // 管理员添加车位信息
    @PostMapping("/admin/add")
    public Result addCarSpace(@RequestBody CarSpace carSpace) {
        carSpaceService.save(carSpace);
        return Result.success();
    }

    // 管理员修改车位信息
    @PutMapping("/admin/update")
    public Result updateCarSpace(@RequestBody CarSpace carSpace) {
        carSpaceService.update(carSpace);
        return Result.success();
    }

    // 管理员删除车位信息
    @DeleteMapping("/admin/delete/{id}")
    public Result deleteCarSpace(@PathVariable Integer id) {
        carSpaceService.delete(id);
        return Result.success();
    }
}