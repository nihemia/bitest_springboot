package com.springboot.entity;

import javax.persistence.*;

@Table(name="carsties")
public class Carsites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "csNum")
    private Integer csNum;


    @Column(name = "csStatus")
    private Integer csStatus;

    @Column(name = "csCarNum")
    private Integer csCarNum;

    @Column(name = "userid")
    private Integer userid;

    @Transient
    //用于映射typeId，通typeId得到typeName(类别名称)
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCsNum() {
        return csNum;
    }

    public void setCsNum(Integer csNum) {
        this.csNum = csNum;
    }

    public Integer getCsStatus() {
        return csStatus;
    }

    public void setCsStatus(Integer csStatus) {
        this.csStatus = csStatus;
    }

    public Integer getCsCarNum() {
        return csCarNum;
    }

    public void setCsCarNum(Integer csCarNum) {
        this.csCarNum = csCarNum;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
