package com.djl.shop.service;

import com.djl.shop.dao.OrderRepo;
import com.djl.shop.dao.entity.SysOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    public void save(SysOrder order){
        orderRepo.save(order);
    }

    public void delete(long id){
        orderRepo.deleteById(id);
    }
}
