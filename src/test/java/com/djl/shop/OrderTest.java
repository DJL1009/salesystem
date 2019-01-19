package com.djl.shop;

import com.djl.shop.dao.CommodityRepo;
import com.djl.shop.dao.OrderRepo;
import com.djl.shop.dao.SysUserRepo;
import com.djl.shop.dao.entity.SysOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    CommodityRepo commodityRepo;
    @Autowired
    SysUserRepo userRepo;

    @Test
    public void addOrder(){
        SysOrder order = new SysOrder();
        order.setId(1);
        order.setSysUser(userRepo.getOne(2L));
        order.setCommodity(commodityRepo.getOne(5L));
        order.setPrice(88);
        order.setQuantity(12);
        //order.setDone(true);
        orderRepo.save(order);
    }
}
