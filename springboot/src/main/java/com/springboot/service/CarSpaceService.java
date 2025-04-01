package com.springboot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.entity.CarSpace;
import com.springboot.entity.Payment;
import com.springboot.mapper.CarSpaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarSpaceService {

    private static final Logger logger = LoggerFactory.getLogger(CarSpaceService.class);
    @Resource
    private CarSpaceMapper carSpaceMapper;

    public List<CarSpace> findAll() {
        List<CarSpace> carSpaces = carSpaceMapper.findAll();
        logger.info("查询到的车位信息: {}", carSpaces);
        System.out.println(carSpaces);
//        return carSpaceMapper.selectAll();
        return carSpaces;
    }

    public List<CarSpace> findAllName() {
        return findAll();
    }

    public CarSpace findById(Integer id) {
        return carSpaceMapper.findById(id);
    }

    public void save(CarSpace carSpace) {
        carSpaceMapper.insertSelective(carSpace);
    }

    public void update(CarSpace carSpace) {
        carSpaceMapper.updateByPrimaryKeySelective(carSpace);
    }

    public void delete(Integer id) {
        carSpaceMapper.delete(id);
    }

    public List<CarSpace> findByOwnerId(Integer ownerId) {
        return carSpaceMapper.findByOwnerId(ownerId);
    }

    public PageInfo<CarSpace> findbySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<CarSpace> list=carSpaceMapper.findbySearch(params);
        return PageInfo.of(list);
    }
}