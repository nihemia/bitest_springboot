package com.springboot.entity;

//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="User对象", description="")
@Table(name="admin")
//public class Admin implements Serializable {
public class User extends  Account {

//    private static final long serialVersionUID = 1L;


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

//    @ApiModelProperty(value = "姓名")
    @Column(name="name")
//    @JsonProperty("name")
    private String name;

//    @ApiModelProperty(value = "密码")
    @Column(name="password")
//    @JsonProperty("password")
    private String password;

//    @ApiModelProperty(value = "性别")
@Column(name="sex")
//@JsonProperty("sex")
    private String sex;

//    @ApiModelProperty(value = "年龄")
    @Column(name="age")
//    @JsonProperty("age")
    private Integer age;
    @Column(name="phone")
    private String phone;

    @Column(name="role")
    private String role;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "pay_way")
    private String payWay;

    @Column(name = "pay_money")
    private Double payMoney;


    //注解表明该字段不是数据库中的字段
    @Transient
    private String token;

    public User(){};
    public User(Integer id, String name, String password, String sex, Integer age, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }


}
