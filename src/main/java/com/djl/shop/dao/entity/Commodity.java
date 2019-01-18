package com.djl.shop.dao.entity;

import javax.persistence.*;


/***
 * 正向工程，通过实体类生成表结构
 */
@Entity
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commodityId;//不能使用封装类型

    private String title;
    private double price;
    private String image;
    private String summary;
    private String detail;
    private int quantity;
    public Commodity(){
        super();
    }
    public Commodity(String title,double price,String image,String summary,String detail){
        super();
        this.title = title;
        this.price = price;
        this.image = image;
        this.summary =summary;
        this.detail = detail;
    }
    public long getCommodityId(){ return this.commodityId; }
    public void setCommodityId(long commodityId){
        this.commodityId = commodityId;
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

    @Override
    public String toString(){
        return this.getTitle()+":    ¥"+this.getPrice();
    }
}
