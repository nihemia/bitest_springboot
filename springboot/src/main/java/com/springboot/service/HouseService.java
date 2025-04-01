package com.springboot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.entity.CarSpace;
import com.springboot.entity.House;
import com.springboot.entity.Payment;
import com.springboot.mapper.CarSpaceMapper;
import com.springboot.mapper.HouseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HouseService {

    private static final Logger logger = LoggerFactory.getLogger(HouseService.class);
    @Resource
    private HouseMapper houseMapper;

    public List<House> findAll() {
        List<House> houses = houseMapper.findAll();
        logger.info("查询到的车位信息: {}", houses);
        System.out.println(houses);
//        return carSpaceMapper.selectAll();
        return houses;
    }

    public List<House> findAllName() {
        return findAll();
    }

    public House findById(Integer id) {
        return houseMapper.findById(id);
    }

    public void save(House houses) {
        houseMapper.insertHouse(houses);
    }

    public void update(House houses) {
        houseMapper.updateeHouse(houses);
    }

    public void delete(Integer id) {
        houseMapper.delete(id);
    }

    public List<House> findByOwnerId(Integer ownerId) {
        return houseMapper.findByOwnerId(ownerId);
    }

    public PageInfo<House> findbySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<House> list=houseMapper.findbySearch(params);
        return PageInfo.of(list);
    }
}