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
import com.auth0.jwt.interfaces.DecodedJWT;
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
            //接下来的查询会自动安装当前开启的分页设置来查询
//        List<User> list=adminMapper.findbysearch(params);
//        return PageInfo.of(list);
//        return Mapper.findbysearch(params);
          //  开启分页查询, 当执行查询时，插件进行相关的sql拦截进行分页操作，返回一个page对象
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            List<User> list=userMapper.findbySearch(params);
         return PageInfo.of(list);
        }


    public void add(User user) {
        System.out.println("用户名不能为空，请重新输入"+user.getName());
        //1、用户名不能为空
        if(user.getName()==null || "".equals(user.getName())){
            throw new CustomException("用户名不能为空，请重新输入");
        }
        if(user.getPassword()==null || "".equals(user.getPassword())){
            throw new CustomException("密码不能为空，请重新输入");
        }
        //2、年龄不能小于18大于100
//        if((user.getAge()!=null ||"".equals(user.getAge()))&(user.getAge()<18 || user.getAge()>100)){
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
        if(user.getPassword()==null){
            user.setPassword("123456");
        }

        userMapper.insertByowner(user);
    }
//    public void add(User user) {
//        //1、用户名不能为空
//        if(user.getName()==null || "".equals(user.getName())){
//            throw new CustomException("用户名不能为空，请重新输入");
//        }
//        if(user.getPassword()==null || "".equals(user.getPassword())){
//            throw new CustomException("密码不能为空，请重新输入");
//        }
//        //2、年龄不能小于18大于100
////        if((admin.getAge()!=null ||"".equals(admin.getAge()))&(admin.getAge()<18 || admin.getAge()>100)){
////            throw new CustomException("用户的年龄有误，请重新输入");
////        }
//        if(user.getAge() == null || user.getAge() < 18 || user.getAge() > 50){
//            throw new CustomException("年龄不能为空且必须在18到50岁之间");
//        }
//        //3、进行重复判断，同一名字不允许重复新增：依据用户名去数据库查询
//        User existUser= userMapper.findByName(user.getName());
//        if(existUser!=null){
//            //有相同姓名用户，需要提示不允许新增
//            throw new CustomException("该用户已存在，请重新输入");
//        }
//        //初始化密码
//        if(existUser.getPassword()==null){
//            existUser.setPassword("123456");
//        }
//
//        userMapper.insertSelective(user);  //插入传来的user对象
//    }

    //更新user的所有信息
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    //更新除了余额、密码、role的所有信息
    public void updateOwnerinfo(User user) {
        userMapper.updateOwnerinfo(user);
    }

    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public Account login(Account user) {
        System.out.println("userService1111"+user.getId()+user.getName()+user.getRole());
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
//        String token=JwtTokenUtils.genToken(users.getId().toString(),users.getPassword());
//        String token=JwtTokenUtils.genToken(users.getId().toString(),users.getPassword(),"ROLE_USER");
//        users.setToken(token);
//        return users;
        System.out.println("Users"+"————————"+users.getId()+users.getName()+users.getRole());
        String token=JwtTokenUtils.genToken(users.getId().toString(),users.getPassword(),"ROLE_USER");
        users.setToken(token);
        return users;
    }

    public User findById(Integer id) {
//        User user=userMapper.selectByPrimaryKey(id);  //此方法不行
        User user=userMapper.selectUserById(id);
        if (user == null) {
            throw new CustomException("用户不存在"); // 抛出业务异常
        }
        System.out.println("userservice22222222"+user.getName()+user.getId());
        return user;

    }


    //获得业主信息
    public Account getUserInfo(String username) {
        Account user=userMapper.findByName(username);
        return userMapper.findByName(username);
    }

    //修改业主密码
    public boolean changePassword(String username, String newPassword) {
        User user = userMapper.findByName(username);
        if (user != null) {
            user.setPassword(newPassword);
            userMapper.save(user.getName(),user.getPassword());
            return true;
        }
        return false;
    }

    //获得token
    public Account getUserByToken(String token) {
        try {
            // 解析token获取用户ID
//            String userIdStr = JWT.decode(token).getAudience().get(0);
//            Integer userId = Integer.valueOf(userIdStr);
            DecodedJWT jwt=JWT.decode(token);
            String userId= jwt.getAudience().get(0);
            // 根据用户ID从数据库中查询用户信息
//            return userMapper.selectUserById(userId);
            return userMapper.selectUserById(Integer.valueOf(userId));
        } catch (Exception e) {
            // 处理解析token或查询用户信息时可能出现的异常
            return null;
        }
    }

    public List<User> findAllName() {
        return userMapper.selectAllName();
    }


    public void insertPaymoney(User user) {
        userMapper.insertPaymoney(user);
    }

    public void payByowner(User user) {
                System.out.println(user.getTotalMoney());
        if(user.getPayWay()==null || "".equals(user.getPayWay())){
            throw new CustomException("支付方式不能为空，请重新输入");
        }
        else if(user.getPayMoney()==null || "".equals(user.getPayMoney())){
            throw new CustomException("支付金额不能为空，请重新输入");
        }

       else{
            System.out.println("充值前的余额是："+user.getTotalMoney());
           userMapper.payByowner(user);
            System.out.println("充值后的余额是："+user.getTotalMoney());
        }

    }

    //检查输入的旧密码是否与数据库中的密码一致
    public boolean checkPassword(String username, String oldPassword) {
        User user = userMapper.findByName(username);
        if (user != null) {
            return user.getPassword().equals(oldPassword);
        }
        return false;
    }

    //验证旧密码正确后更新密码
    public void updatePassword(String username, String newPassword) {
        User user = userMapper.findByName(username);
        if (user != null) {
            user.setPassword(newPassword);
            userMapper.save(username,newPassword);
        }
    }

}
