package com.springboot.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        //指定controller统一接口的前缀，即在url拼上了/api/xxx
        configurer.addPathPrefix("/api",clazz->clazz.isAnnotationPresent(RestController.class));
    }

    //使得拦截器JwtInterceptor生效
    //加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //拦截所有/api/下面的所有文件，但是排除拦截/api/admin/login
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
//                .excludePathPatterns("/api/admin/login")
//                .excludePathPatterns("/api/admin/register")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/register")
                .excludePathPatterns("/api/files/**")
                .excludePathPatterns("/api/type/upload");
    }

    //视频没有，deepskear添加，不添加则报错
    // 新增CORS配置
    //此方法解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // 使用新模式匹配
                .allowedMethods("*") // 允许所有方法
                .allowedHeaders("*")
                .exposedHeaders("token")
                .allowCredentials(true)
                .maxAge(3600);
    }















}
