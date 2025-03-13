package com.springboot.service;






import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.entity.Type;
import com.springboot.exception.CustomException;
import com.springboot.mapper.TypeMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */
@Service
public class TypeService {



@Resource
    private TypeMapper typeMapper;


    public PageInfo<Type> findbySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Type> list=typeMapper.findbySearch(params);
        return PageInfo.of(list);
    }
    public void add(Type type) {
        //1、用户名不能为空
        if(type.getName()==null || type.getName().trim().isEmpty()){
            throw new CustomException("图书类别名称不能为空，请重新输入");
        }


        //3、进行重复判断，同一名字不允许重复新增：依据用户名去数据库查询
        Type existType= typeMapper.findByName(type.getName());
        if(existType!=null){
            //有相同姓名用户，需要提示不允许新增
            throw new CustomException("该图书类别已存在，请重新输入");
        }try{
            typeMapper.insertSelective(type);
        }catch (DuplicateKeyException e){
            throw new CustomException("该图书类别已存在，请重新输入");
        }



    }

    public void update(Type type) {
        typeMapper.updateByPrimaryKeySelective(type);
    }

    public void delete(Integer id) {
        typeMapper.deleteByPrimaryKey(id);
    }

    public List<Type> findAll() {
        return typeMapper.selectAll();
    }
}
