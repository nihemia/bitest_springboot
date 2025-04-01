package com.springboot.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name="maintenance_request")
public class MaintenanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="room_number")
    private String roomNumber;
    @Column(name="description")
    private String description;
    @Column(name="request_time")
    private Date requestTime;
    @Column(name="status")
    private String status;
    @Column(name="userid")
    private Integer userId;
    @Column(name="adminid")
    private Integer adminId;
    @Column(name="complate_time")
    private  Date complateTime;

    @Transient
    private String ownerName;

    @Transient
    private String adminName;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getComplateTime() {
        return complateTime;
    }

    public void setComplateTime(Date complateTime) {
        this.complateTime = complateTime;
    }
}
