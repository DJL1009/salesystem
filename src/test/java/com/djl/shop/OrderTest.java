package com.djl.shop;

import com.djl.shop.dao.CommodityRepo;
import com.djl.shop.dao.UserRepo;
import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.dao.entity.SysOrder;
import com.djl.shop.dao.entity.SysUser;
import com.djl.shop.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {
    @Autowired
    OrderService orderService;
    @Autowired
    CommodityRepo commodityRepo;
    @Autowired
    UserRepo userRepo;

    @Test
    public void addOrder(){
        SysOrder order = new SysOrder();
        //order.setId(1);
        order.setSysUser(userRepo.getOne(2L));
        order.setCommodity(commodityRepo.getOne(2L));
        order.setPrice(44);
        order.setQuantity(5);
        //order.setDone(true);
        orderService.save(order);
    }

    @Test
    public void orderList(){
        SysUser user = userRepo.getOne(2L);
        Commodity commodity = commodityRepo.getOne(2L);
        List<SysOrder> orders = orderService.findByUandC(user,commodity);
        System.out.println(orders);
    }

    @Test
    public void getOrders(){
        System.out.println(orderService.findByUserId(2));
        System.out.println("---------------------------------------------------------------");
        System.out.println(orderService.findByCommodityId(4));
        System.out.println("---------------------------------------------------------------");
        System.out.println(orderService.findByCommodityId(5));
        //System.out.println(orderRepo.findOrderByMulId(2,5));
    }
}
