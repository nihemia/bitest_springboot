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

@Table(name="type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //该注解使得导入excel表中的数据映射为type的name，decription
    //@Alias("分类名称")中的中文需要与excel的表头一致，否则无法注入数据
    @Alias("分类名称")
    @Column(name = "name")
    private String name;

    @Alias("分类描述")
    @Column(name = "description")
    private String description;






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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}



