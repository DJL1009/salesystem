package com.djl.shop.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SysOrder {
    @Id                                                  //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增长策略
    private long id;                                     //订单ID

    //主控方，对象名为被控表mappedby中的值
    @ManyToOne(optional = false)               //optional=false 表示用户id不能为空。删除订单，不影响用户
    @JoinColumn(name = "user_id")
    private SysUser sysUser;                   //外键-用户id

    @ManyToOne(optional = false)               //optional=false 表示商品id不能为空。删除订单，不影响商品
    @JoinColumn(name = "commodity_id")
    private Commodity commodity;               //外键-商品id

    private double price;                      //商品单价
    private int quantity;                      //商品数量

    //数据库自动添加、更新时间戳
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)          //Temporal用于映射java对象与SQL数据类型
    private Date time;                         //时间戳

    private boolean isDone;                    //待扩展：订单状态，用于区分持久化的购物车数据与已完成订单

    public long getId(){ return this.id; }

    public void setId(long id){ this.id = id; }

    public SysUser getSysUser(){ return this.sysUser; }

    @JsonBackReference
    public void setSysUser(SysUser sysUser){ this.sysUser = sysUser; }

    public Commodity getCommodity(){ return this.commodity; }

    @JsonBackReference
    public void setCommodity(Commodity commodity){ this.commodity = commodity; }

    public double getPrice() { return this.price;}

    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Date getTime() { return this.time; }

    public void setTime(Date time) { this.time = time; }

    public boolean isDone() { return this.isDone; }

    public void setDone(boolean done) { isDone = done; }

    @Override
    public String toString(){
        return "Order"+this.id+":"+this.sysUser.getUsername()+" buys "+this.quantity+"  "+this.commodity.getTitle()+" "+this.getTime();
    }
}
