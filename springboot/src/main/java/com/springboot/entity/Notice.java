package com.springboot.entity;

import cn.hutool.core.annotation.Alias;

import javax.persistence.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */

@Table(name="notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //该注解使得导入excel表中的数据映射为type的name，decription
    //@Alias("分类名称")中的中文需要与excel的表头一致，否则无法注入数据
    @Alias("通知名称")
    @Column(name = "name")
    private String name;

    @Alias("分类描述")
    @Column(name = "content")
    private String content;

    @Alias("发表时间")
    @Column(name = "time")
    private String time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}



