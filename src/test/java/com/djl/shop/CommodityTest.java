package com.djl.shop;

import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.service.CommodityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommodityTest {
    @Autowired
    CommodityService commodityService;

    @Test
    public void findCommodity(){
        int id = 100;
        Commodity commodity = commodityService.findById(id);
        System.out.println(commodity);
        System.out.println(commodity == null);
//        System.out.println(commodity);
//        commodity.setPrice(1000);
//        commodityService.save(commodity);
//        System.out.println(commodity);
    }

    @Test
    public void deleteCommodity(){
        commodityService.delete(10L);
    }

    @Test
    public void commodityIsSelled(){
        List<Commodity> commodities = commodityService.selled();
        Commodity c = commodityService.findById(9);
        System.out.println(commodities);
        System.out.println("================"+commodities.contains(c));
    }
}
