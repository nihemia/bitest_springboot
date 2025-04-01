package com.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.common.Result;
import com.springboot.entity.CarSpace;
import com.springboot.entity.House;
import com.springboot.entity.Payment;
import com.springboot.service.CarSpaceService;
import com.springboot.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Resource
    private HouseService houseService;

    //查询业主姓名
    @GetMapping
    public Result findAllName(){
        return Result.success(houseService.findAllName());
    }

    //利用业主姓名统计所房屋的房屋数量，柱状图统计
    @GetMapping("/echarts/bar")
    public Result getAllbtu() {
        List<House> list = houseService.findAll();
        //x是list集合中的某个，相当于for循环的i
        //。stream打散——>.filter再过滤空数据——>再组合，依据OwnerName进行分组作为key，再计算数量作为value，返回类型是Map集合
        Map<String,Long> collect=list.stream()
                .filter(x-> ObjectUtil.isNotEmpty(x.getOwnerName()))
                .collect(Collectors.groupingBy(House::getOwnerName,Collectors.counting()));
        //最后返回前端的数据结构
        List<String> xAxis=new ArrayList<>();
        List<Long> yAxis=new ArrayList<>();
        if(CollectionUtil.isNotEmpty(collect)){
            for (String key:collect.keySet()){
                xAxis.add(key);
                yAxis.add(collect.get(key));
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("xAxis",xAxis);
        map.put("yAxis",yAxis);
        System.out.println(map.get("xAxis"));
        System.out.println(map.get("yAxis"));
        return Result.success(map);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        System.out.println(params.getPageNum());
        PageInfo<House> info=houseService.findbySearch(params);
        return Result.success(info);
    }

    // 管理员获取所有房屋信息
    @GetMapping("/admin/all")
    public Result getAllHouses() {
        List<House> house = houseService.findAll();
        return Result.success(house);
    }

    // 业主获取自己的车位信息
    @GetMapping("/owner/{ownerId}")
    public Result getHouseByOwnerId(@PathVariable Integer ownerId) {
        System.out.println(ownerId);
        List<House> houses = houseService.findByOwnerId(ownerId);
        return Result.success(houses);
    }

    // 管理员添加车位信息
    @PostMapping("/admin/add")
    public Result addHouse(@RequestBody House houses) {
        houseService.save(houses);
        return Result.success();
    }

    // 管理员修改车位信息
    @PutMapping("/admin/update")
    public Result updateHouse(@RequestBody House houses) {
        houseService.update(houses);
        return Result.success();
    }

    // 管理员删除车位信息
    @DeleteMapping("/admin/delete/{id}")
    public Result deleteHouse(@PathVariable Integer id) {
        houseService.delete(id);
        return Result.success();
    }
}