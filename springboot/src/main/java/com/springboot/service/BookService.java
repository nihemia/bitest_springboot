package com.springboot.service;


//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.springboot.common.Params;
//import com.springboot.entity.Admin;
//import com.springboot.mapper.AdminMapper;
////import lombok.experimental.Helper;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.common.JwtTokenUtils;
import com.springboot.common.Params;
import com.springboot.entity.Admin;
import com.springboot.entity.Book;
import com.springboot.entity.Type;
import com.springboot.exception.CustomException;
import com.springboot.mapper.AdminMapper;
import com.springboot.mapper.BookMapper;
import com.springboot.mapper.TypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {



@Resource
    private BookMapper bookMapper;
    private TypeMapper typeMapper;


    public PageInfo<Book> findBySearch(Params params) {
        //视频里的
        //  开启分页查询, 当执行查询时，插件进行相关的sql拦截进行分页操作，返回一个page对象
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Book> list=bookMapper.findBySearch(params);
        //不用sql语句查询关联表数据，则用Java代码实现
//        if(CollectionUtil.isEmpty(list)){
//            return PageInfo.of(new ArrayList<>());
//        }
//        for (Book book:list) {
//            if(ObjectUtil.isNotEmpty(book.getTypeId())){
//                Type type=typeMapper.selectByPrimaryKey(book.getTypeId());
//                if(ObjectUtil.isNotEmpty(type)){
//                    book.setName(type.getName());
//                }
//            }
//        }
//        return PageInfo.of(list);
        return PageInfo.of(list);
    }

    public void add(Book book) {
        //1、用户名不能为空
        if(book.getName()==null || "".equals(book.getName())){
            throw new CustomException("图书名称不能为空，请重新输入");
        }
        if(book.getAuthor()==null || "".equals(book.getAuthor())){
            throw new CustomException("图书作者不能为空，请重新输入");
        }

        //3、进行重复判断，同一名字不允许重复新增：依据用户名去数据库查询
        Book books= bookMapper.findByName(book.getName());
        if(books!=null){
            //有相同姓名用户，需要提示不允许新增
            throw new CustomException("该图书已存在，请重新输入");
        }


        bookMapper.insertSelective(book);
    }

    public void update(Book book) {
        bookMapper.updateByPrimaryKeySelective(book);
    }

    public void delete(Integer id) {
        bookMapper.deleteByPrimaryKey(id);
    }
}
