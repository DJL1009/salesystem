package com.djl.shop.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

//@Entity
public class Order {
    @Id     //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增长策略
    private long id;           //订单ID

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false) //optional=false 表示用户id不能为空。删除订单，不影响用户
    @JoinColumn(name = "id")
    private SysUser sysUser;       //买家用户ID

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH}) //optional=false 表示用户id不能为空。删除订单，不影响用户
    @JoinColumn(name = "id")
    private Commodity commodity;  //商品ID

    private double price;      //商品单价
    private int quantity;      //商品数量
    private Timestamp time;    //处理时间戳
    private boolean isDone;    //订单是否已完成
}
