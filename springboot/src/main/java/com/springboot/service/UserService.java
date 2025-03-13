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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.common.JwtTokenUtils;
import com.springboot.common.Params;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.springboot.entity.User;
import com.springboot.exception.CustomException;
import com.springboot.mapper.AdminMapper;
import com.springboot.mapper.UserMapper;
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
public class UserService {



@Resource
    private UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.getUser();
    }


    public PageInfo<User> findbySearch(Params params) {
//        public List<User> findbysearch(Params params) {
//        PageHelper.startPage(params.getPageNum(),params.getPageSize());
//        PageHelper.startPage(params.getPageNum(),params.getPageSize(),true);
            //接下来的查询会自动安装当前开启的分页设置来查询
//        List<User> list=adminMapper.findbysearch(params);
//        return PageInfo.of(list);
//        return Mapper.findbysearch(params);
          //  开启分页查询, 当执行查询时，插件进行相关的sql拦截进行分页操作，返回一个page对象
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            List<User> list=userMapper.findbySearch(params);
         return PageInfo.of(list);
//            return adminMapper.findbysearch(params);
        }



    public void add(User user) {
        //1、用户名不能为空
        if(user.getName()==null || "".equals(user.getName())){
            throw new CustomException("用户名不能为空，请重新输入");
        }
        if(user.getPassword()==null || "".equals(user.getPassword())){
            throw new CustomException("密码不能为空，请重新输入");
        }
        //2、年龄不能小于18大于100
//        if((admin.getAge()!=null ||"".equals(admin.getAge()))&(admin.getAge()<18 || admin.getAge()>100)){
//            throw new CustomException("用户的年龄有误，请重新输入");
//        }
        if(user.getAge() == null || user.getAge() < 18 || user.getAge() > 50){
            throw new CustomException("年龄不能为空且必须在18到50岁之间");
        }
        //3、进行重复判断，同一名字不允许重复新增：依据用户名去数据库查询
        User users= userMapper.findByName(user.getName());
        if(users!=null){
            //有相同姓名用户，需要提示不允许新增
            throw new CustomException("该用户已存在，请重新输入");
        }
        //初始化密码
        if(users.getPassword()==null){
            users.setPassword("123456");
        }

        userMapper.insertSelective(users);
    }

    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User login(Account user) {
        //1、进行一些非空判断
        if(user.getName()==null || "".equals(user.getName())){
            throw new CustomException("用户名不能为空，请重新输入");
        }
        if(user.getPassword()==null || "".equals(user.getPassword())){
            throw new CustomException("密码不能为空，请重新输入");
        }
        //2、比对数据库中的用户名密码
        User users=userMapper.findByNameAndPassword(user.getName(),user.getPassword());
        if(users==null){
            //3、若不存在此用户，则提示且不允许登录
            throw new CustomException("用户名或密码错误");
        }
        //4、用户名密码无误
        //生成该登录用户对应token，与user一起返回到前端
        String token=JwtTokenUtils.genToken(users.getId().toString(),users.getPassword());
        users.setToken(token);
        return users;
    }

    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }


    public Account getUserInfo(String username) {
        return userMapper.findByName(username);
    }

    public boolean changePassword(String username, String newPassword) {
        User user = userMapper.findByName(username);
        if (user != null) {
            user.setPassword(newPassword);
            userMapper.save(user.getName(),user.getPassword());
            return true;
        }
        return false;
    }
}
