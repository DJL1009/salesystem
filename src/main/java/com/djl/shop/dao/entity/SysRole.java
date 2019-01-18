package com.djl.shop.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    private String name;
    public void setRoleId(long roleId){
        this.roleId = roleId;
    }
    public long getRoleId(){
        return this.roleId;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
