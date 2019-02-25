package com.djl.shop;

import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.service.CommodityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@EnableTransactionManagement
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

    @Test
    public void updateTest(){
        commodityService.updateSelled(2,12);
    }

    @Test
    public void concurrentTest(){
        ExecutorService executors = Executors.newFixedThreadPool(4);
        for(int i=0;i<4;i++){
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        commodityService.updateSelled(1,2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }
                }
            });
        }
        System.out.println("+++++++++++++++++++++++");
        System.out.println();
    }


}
