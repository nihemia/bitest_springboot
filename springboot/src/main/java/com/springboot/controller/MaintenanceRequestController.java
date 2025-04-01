package com.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.common.Result;
import com.springboot.entity.*;
import com.springboot.service.MaintenanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceRequestController {
    @Resource
    private MaintenanceRequestService maintenanceRequestService;

//    @GetMapping
//    public Result findAllName(){
//        return Result.success(maintenanceRequestService.findAllName());
//    }

    @GetMapping("/search")
    public Result findBySearch(Params params){

        PageInfo<MaintenanceRequest> info=maintenanceRequestService.findbySearch(params);
        return Result.success(info);
    }

//    @PostMapping("/submit")
    @PostMapping("/submit/{userId}")
//    public Result submitRequest(@RequestBody MaintenanceRequest request) {
    public Result submitRequest(@RequestBody MaintenanceRequest request,@PathVariable Integer userId) {
        System.out.println("/submit/{userId}"+userId);
        maintenanceRequestService.submitRequest(request,userId);
//        maintenanceRequestService.submitRequest(request);
        return Result.success("维修请求已提交");
    }

    @PutMapping("/update/admin/{id}")
//    public Result updateStatus(@PathVariable Integer id, @RequestBody MaintenanceRequest request) {
//    public Result updateStatus(@PathVariable Integer id,@PathVariable Integer uid) {
//    public Result updateStatus(@PathVariable Integer id) {
    public Result updateStatus(@PathVariable Integer id, @RequestBody Admin admin) {
        System.out.println("管理员id:"+admin.getId());
        System.out.println("要修改的id:"+id);
        MaintenanceRequest maintenance = maintenanceRequestService.findRequestById(id);
        String status = maintenance.getStatus();
        if (status != null || "未处理".equals(status)) {
//            maintenanceRequestService.updateStatus(id, status);
            maintenanceRequestService.updateStatus(id, status,admin.getId());
        }
//        String status = request.getStatus();
//        Integer userId=request.getOwnerId();
//        Integer adminId=request.getAdminId();
//        if (status != null) {
//            maintenanceRequestService.updateStatus(id, status,userId,adminId);
//            return Result.success("维修请求状态已更新");
//        } else {
//            return Result.error("缺少必要参数");
//        }
        return  Result.success();
    }

    /**
     * 删除维修请求
     * @param id 维修请求 ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteRequest(@PathVariable Integer id) {
        maintenanceRequestService.deleteRequest(id);
        return Result.success("维修请求已删除");
    }


    @GetMapping("/owner/{ownerId}")
    public Result getOwnerPayment(@PathVariable Integer ownerId) {
        List<MaintenanceRequest> maintenance = maintenanceRequestService.findByOwnerId(ownerId);
        System.out.println("/owner/{ownerId}"+maintenance.get(0).getUserId()+maintenance.get(0).getDescription());
        return Result.success(maintenance);
    }

    @GetMapping("/echarts/btu")
    public Result getAllbtu() {
        List<MaintenanceRequest> list = maintenanceRequestService.findAll();
        //x是list集合中的某个，相当于for循环的i
        //。stream打散——>.filter再过滤空数据——>再组合，依据OwnerName进行分组作为key，再计算数量作为value，返回类型是Map集合
        Map<String,Long> collect=list.stream()
                .filter(x-> ObjectUtil.isNotEmpty(x.getStatus()))
                .collect(Collectors.groupingBy(MaintenanceRequest::getStatus,Collectors.counting()));
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

}