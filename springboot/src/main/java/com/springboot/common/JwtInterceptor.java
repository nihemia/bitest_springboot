package com.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springboot.entity.Account;
import com.springboot.entity.Admin;
import com.springboot.entity.User;
import com.springboot.exception.CustomException;
import com.springboot.service.AdminService;
import com.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 放行 OPTIONS 预检请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        // 1、从 http 的请求的 header 中获取 token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // 若是没拿到，则去参数中获取 例如/api/admin?token=xxxxx&pageNum=1
            token = request.getParameter("token");
        }

        // 2、开始执行认证，到此代码表明无 token
        if (StrUtil.isBlank(token)) {
            log.warn("请求未携带 token: {}", request.getRequestURI());
            System.out.println("token1111"+request.getRequestURI());
            throw new CustomException("无 token,请重新登录");
        }
        // 获取 token 中的 userId 和 role
        String userId;
        String role;
        Account user;
        try {
            // 将 token 进行解码，获得 userId 和 role
            userId = JWT.decode(token).getAudience().get(0);
            role = JWT.decode(token).getAudience().get(1);

            System.out.println("JInt121212"+userId);
            System.out.println("JInt121212"+role);
            if ("ROLE_ADMIN".equals(role)) {
                user = adminService.findById(Integer.parseInt(userId));
            } else if ("ROLE_USER".equals(role)) {
//                user = userService.findById(Integer.valueOf(userId));
                user = userService.findById(Integer.parseInt(userId));

            } else {
                throw new CustomException("不支持的角色类型");
            }
            System.out.println("JIUser"+user.getId()+user.getName());
        } catch (Exception e) {
            String errMsg = "token 验证失败，请重新登录";
            log.error(errMsg + ",token=" + token, e);
            throw new CustomException(errMsg);
        }
        if (user == null) {
            throw new CustomException("用户不存在,请重新登录");
        }

        String password;
        if (user instanceof Admin) {
            password = ((Admin) user).getPassword();
        } else if (user instanceof User) {
            password = ((User) user).getPassword();
        } else {
            throw new CustomException("不支持的用户类型");
        }

        try {
            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
            jwtVerifier.verify(token); // 验证 token
        } catch (JWTVerificationException e) {
            throw new CustomException("token验证失败,请重新登录");
        }
        log.info("token 验证 success");
        return true;
    }
}