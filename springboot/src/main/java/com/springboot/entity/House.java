// Owner.java
package com.springboot.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name="house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "house_number")
    private String houseNumber;
    @Column(name = "status")
    private String status;

    @Column(name = "houseBuild")
    private String houseBuild;
    @Column(name = "houseDate")
    private Date houseDate;


    @Column(name = "owner_id")
    private Integer ownerId;

    @Transient
    //用于映射typeId，通typeId得到typeName(类别名称)
    private String ownerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHouseBuild() {
        return houseBuild;
    }

    public void setHouseBuild(String houseBuild) {
        this.houseBuild = houseBuild;
    }

    public Date getHouseDate() {
        return houseDate;
    }

    public void setHouseDate(Date houseDate) {
        this.houseDate = houseDate;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}