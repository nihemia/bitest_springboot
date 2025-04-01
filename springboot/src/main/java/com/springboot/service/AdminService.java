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

import com.auth0.jwt.JWT;
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
public class AdminService {



    @Resource
    private AdminMapper adminMapper;
    private UserMapper userMapper;

    public List<Admin> findAll() {
        return adminMapper.getAdmin();
    }


    public PageInfo<Admin> findbySearch(Params params) {
//        public List<Admin> findbysearch(Params params) {
//        PageHelper.startPage(params.getPageNum(),params.getPageSize());
//        PageHelper.startPage(params.getPageNum(),params.getPageSize(),true);
        //接下来的查询会自动安装当前开启的分页设置来查询
//        List<Admin> list=adminMapper.findbysearch(params);
//        return PageInfo.of(list);
//        return adminMapper.findbysearch(params);
        //  开启分页查询, 当执行查询时，插件进行相关的sql拦截进行分页操作，返回一个page对象
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Admin> list=adminMapper.findbySearch(params);
        return PageInfo.of(list);
//            return adminMapper.findbysearch(params);
    }



    public void add(Admin admin) {
        //1、用户名不能为空
        if(admin.getName()==null || "".equals(admin.getName())){
            throw new CustomException("用户名不能为空，请重新输入");
        }
        if(admin.getPassword()==null || "".equals(admin.getPassword())){
            throw new CustomException("密码不能为空，请重新输入");
        }
        //2、年龄不能小于18大于100
//        if((admin.getAge()!=null ||"".equals(admin.getAge()))&(admin.getAge()<18 || admin.getAge()>100)){
//            throw new CustomException("用户的年龄有误，请重新输入");
//        }
        if(admin.getAge() == null || admin.getAge() < 18 || admin.getAge() > 50){
            throw new CustomException("年龄不能为空且必须在18到50岁之间");
        }
        //3、进行重复判断，同一名字不允许重复新增：依据用户名去数据库查询
        Admin user= adminMapper.findByName(admin.getName());
        if(user!=null){
            //有相同姓名用户，需要提示不允许新增
            throw new CustomException("该用户已存在，请重新输入");
        }
        //初始化密码
        if(admin.getPassword()==null){
            admin.setPassword("123456");
        }

        adminMapper.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    public Admin login(Account admin) {
        //1、进行一些非空判断
        if(admin.getName()==null || "".equals(admin.getName())){
            throw new CustomException("用户名不能为空，请重新输入");
        }
        if(admin.getPassword()==null || "".equals(admin.getPassword())){
            throw new CustomException("密码不能为空，请重新输入");
        }
        //2、比对数据库中的用户名密码
        Admin user=adminMapper.findByNameAndPassword(admin.getName(),admin.getPassword());
        if(user==null){
            //3、若不存在此用户，则提示且不允许登录
            throw new CustomException("用户名或密码错误");
        }
        //4、用户名密码无误
        //生成该登录用户对应token，与user一起返回到前端
//        String token=JwtTokenUtils.genToken(user.getId().toString(),user.getPassword());
        String token=JwtTokenUtils.genToken(user.getId().toString(),user.getPassword(),"ROLE_ADMIN");
        user.setToken(token);
        return user;
    }

    public Admin findById(Integer id) {

//        return adminMapper.selectByPrimaryKey(id);
        return adminMapper.selectUserById(id);
    }


    public Account getUserInfo(String username) {
        return adminMapper.findByName(username);
    }

    public boolean changePassword(String username, String newPassword) {
        Admin user = adminMapper.findByName(username);
        if (user != null) {
            user.setPassword(newPassword);
            adminMapper.save(user.getName(),user.getPassword());
            return true;
        }
        return false;
    }

    public Admin getUserByToken(String token) {
        try {
            // 解析token获取用户ID
            String userIdStr = JWT.decode(token).getAudience().get(0);
            Integer userId = Integer.valueOf(userIdStr);
            // 根据用户ID从数据库中查询用户信息
            return adminMapper.selectUserById(userId);
        } catch (Exception e) {
            // 处理解析token或查询用户信息时可能出现的异常
            return null;
        }
    }

    public List<Admin> findAllName() {
        return adminMapper.selectAllName();
    }

    //检查输入的旧密码是否与数据库中的密码一致
    public boolean checkPassword(String username, String oldPassword) {
        Admin admin = adminMapper.findByName(username);
        if (admin != null) {
            return admin.getPassword().equals(oldPassword);
        }
        return false;
    }

    public void updatePassword(String username, String newPassword) {
        Admin admin = adminMapper.findByName(username);
        if (admin != null) {
            admin.setPassword(newPassword);
            adminMapper.save(username,newPassword);
        }
    }

}
