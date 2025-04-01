package com.springboot.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.springboot.service.AdminService;
import com.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
//工具类，直接使用类名.的形式调用类里面的static方法
public class JwtTokenUtils {
    private static AdminService staticAdminService;
    private static UserService staticUserService;
    //打印日志
    private static final Logger log= LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    //该注解理解为在spring容器初始化时执行该方法
    @PostConstruct
    public void setUserService(){
        //将adminService赋值为静态变量
        staticAdminService=adminService;
        staticUserService=userService;
    }

    /*
    生成token
     */
    //JWT由三部分组成：JWT头 有效载荷 签名
//    public static String genToken(String userId,String password){
    public static String genToken(String userId,String password,String role){
        return JWT.create()
                .withAudience(userId,role) //将user id保存到token里面，作为载荷
//                .withAudience(userId)
//                .withClaim("role",role)
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小时后token过期
                .sign(Algorithm.HMAC256(password));//以password作为token的密钥，即就password作为签名
    }

    /*
    获取当前登录的用户信息
     */
    public static Account getCurrentUser(){
        String token=null;
        try{
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token=request.getHeader("token");
            if(StrUtil.isBlank(token)){
                token=request.getParameter("token");
            }
            if(StrUtil.isBlank(token)){
                log.error("获取当前登录的token失败，token：{}",token);
                return null;
            }
            //解析token，获取用户id
            String adminId=JWT.decode(token).getAudience().get(0);
            String adminRole=JWT.decode(token).getAudience().get(1);
            System.out.println("Utils"+adminId);
//            if ("ROLE_ADMIN".equals(adminRole)) {
//                return staticAdminService.findById(Integer.valueOf(adminId));
//            }
            if ("ROLE_ADMIN".equals(adminRole)) {
                return staticAdminService.findById(Integer.valueOf(adminId));
            } else if ("ROLE_USER".equals(adminRole)) {
                return staticUserService.findById(Integer.valueOf(adminId));
            } else {
                log.error("不支持的角色类型，token={}", token);
                return null;
            }

        }catch (Exception e){
            log.error("获取当前登录的管理员信息失败，token={}",token,e);
            return null;
        }
    }
}
