package com.springboot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.entity.CarSpace;
import com.springboot.entity.MaintenanceRequest;
import com.springboot.entity.Payment;
import com.springboot.mapper.MaintenanceRequestMapper;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MaintenanceRequestService {
    @Resource
    private MaintenanceRequestMapper maintenanceRequestMapper;

    public PageInfo<MaintenanceRequest> findbySearch(Params params) {
        //  开启分页查询, 当执行查询时，插件进行相关的sql拦截进行分页操作，返回一个page对象
        System.out.println("搜索名字："+params.getName());
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<MaintenanceRequest> list=maintenanceRequestMapper.findbySearch(params);
        return PageInfo.of(list);
//            return adminMapper.findbysearch(params);
    }

    //提交维修信息
    public void submitRequest(MaintenanceRequest maintenance,Integer uid) {
        maintenanceRequestMapper.addMaintenance(maintenance,uid);
    }

    /**
     * 更新维修请求状态
     */
    public void updateStatus(Integer id, String status,Integer adminId) {
        MaintenanceRequest request = maintenanceRequestMapper.findRequestById(id);
        request.setAdminId(adminId);
        if (request != null) {
            maintenanceRequestMapper.updateMaintenance(request);
        }
    }

    /**
     * 删除维修请求
     *
     * @param id 维修请求 ID
     */
    public void deleteRequest(Integer id) {
        maintenanceRequestMapper.deleteByMainId(id);
    }



    //
    public MaintenanceRequest findRequestById(Integer id) {
        return maintenanceRequestMapper.findRequestById(id);
    }

    //通业主id查询所有信息
    public List<MaintenanceRequest> findByOwnerId(Integer ownerId) {
        List<MaintenanceRequest> list=maintenanceRequestMapper.getByOwnerName(ownerId);
        System.out.println(list.get(0).getUserId()+list.get(0).getStatus());
        return list;

    }


    public List<MaintenanceRequest> findAll() {
        return maintenanceRequestMapper.findAll();
    }
}