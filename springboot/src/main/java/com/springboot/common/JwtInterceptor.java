package com.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springboot.entity.Admin;
import com.springboot.exception.CustomException;
import com.springboot.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.HandshakeResponse;
import java.util.Collections;

/*
后端校验前端发来的token是否合法
拦截器jwt
 */

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger log= LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Override
    //该方法是处理一些事情放行否则不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        // 放行OPTIONS预检请求
        //此代码视频没有，deepskear添加，不添加则报错
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        //1、从http的请求的header中获取token
        String token=request.getHeader("token");
        if(StrUtil.isBlank(token)){
            //若是没拿到，则去参数中获取 例如/api/admin?token=xxxxx&pageNum=1
            token=request.getParameter("token");
        }

        //2、开始执行认证，到此代码表明无token
        if(StrUtil.isBlank(token)){
            log.warn("请求未携带token: {}", request.getRequestURI());
            throw new CustomException("无token,请重新登录");
        }
        //获取token 中的userId
        String userId;
        Admin admin;
        try{
            //将token进行解码，获得userId
            userId= JWT.decode(token).getAudience().get(0);
            //获得userId从数据库查询，获得真正的admin
            admin=adminService.findById(Integer.parseInt(userId));
        }catch (Exception e){
            String errMsg="token验证失败，请重新登录";
            log.error(errMsg+",token="+token,e);
            throw  new CustomException(errMsg);
        }
        if(admin==null){
            throw  new CustomException("用户不存在,请重新登录");
        }
        try{
            //用户密码加签验证token
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token); //验证token
        }catch (JWTVerificationException e){
            throw  new CustomException("token验证失败,请重新登录");
        }
        log.info("token验证success");
        return true;
    }

















}
