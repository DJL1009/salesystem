package com.djl.shop.dao.entity;

import javax.persistence.*;
import java.util.List;


/***
 * 正向工程，通过实体类生成表结构
 */
@Entity
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;//不能使用封装类型

    private String title;
    private double price;
    private String image;
    private String summary;
    private String detail;
    private int quantity;

    //商品相对于订单是被控方，被控方需要写mappedby，其值为主控方中引用的外键对象的名称
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE},mappedBy = "commodity")
    private List<SysOrder> sysOrders;

    public long getId(){ return this.id; }
    public void setId(long id){
        this.id = id;
    }
    public String getTitle(){ return this.title; }
    public void setTitle(String title){
        this.title = title;
    }
    public double getPrice(){
        return this.price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String getImage(){
        return this.image;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getSummary(){
        return this.summary;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getDetail(){
        return this.detail;
    }
    public void setDetail(String detail){
        this.detail = detail;
    }
    public int getQuantity(){ return this.quantity;}
    public void setQuantity(int quantity){ this.quantity = quantity; }
    public List<SysOrder> getSysOrders() { return this.sysOrders; }
    public void setOrders(List<SysOrder> sysOrders) { this.sysOrders = sysOrders; }

    @Override
    public String toString(){
        return this.getTitle()+":    ¥"+this.getPrice();
    }
}
