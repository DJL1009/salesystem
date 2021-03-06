package com.djl.shop.service;

import com.djl.shop.dao.CommodityRepo;
import com.djl.shop.dao.OrderRepo;
import com.djl.shop.dao.UserRepo;
import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.dao.entity.SysOrder;
import com.djl.shop.dao.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommodityRepo commodityRepo;

    public void save(SysOrder order){
        orderRepo.save(order);
    }

    public void delete(long id){
        orderRepo.deleteById(id);
    }

    public List<SysOrder> findAll(){
        return orderRepo.findAll();
    }

    public List<SysOrder> findByUserId(long id){
        SysUser user = userRepo.getOne(id);
        return orderRepo.findBySysUser(user);
    }

    public List<SysOrder> findByUserOrderByTime(SysUser user){
        return orderRepo.findBySysUserOrderByTimeDesc(user);
    }

    public List<SysOrder> findByCommodityOrderByTime(Commodity commodity){ return orderRepo.findByCommodityOrderByTimeDesc(commodity); }

    public List<SysOrder> findByCommodityId(long id){
        Commodity commodity = commodityRepo.getOne(id);
        return orderRepo.findByCommodity(commodity);
    }


    /**
     * 用户user购买指定commodity最近的价格
     * @param user
     * @param commodity
     * @return
     */
    public double recentPrice(SysUser user,Commodity commodity){
        List<SysOrder> orders = orderRepo.findBySysUserAndCommodity(user,commodity);
        if(orders.size()!=0){
            return orders.get(orders.size()-1).getPrice();
        }
        return 0;
    }


    /**
     * 添加订单
     * @param commodity
     * @param quantity
     */
    public void addOrder(Commodity commodity,int quantity){
        SysUser user = (SysUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        SysOrder order = new SysOrder();
        order.setCommodity(commodity);
        order.setSysUser(user);
        order.setQuantity(quantity);
        order.setPrice(commodity.getPrice());
        order.setDone(true);
        orderRepo.save(order);
    }

    /**
     * 计算总金额
     * @param orders
     * @return
     */
    public BigDecimal calTotalPrice(List<SysOrder> orders){
        //使用double或float计算结果会出现一定的误差。因此使用BigDecimal进行精确计算=====> https://www.cnblogs.com/LeoBoy/p/6056394.html
        BigDecimal total = BigDecimal.ZERO;
        if(orders.size()==0)
            return total;
        for(SysOrder order:orders){
            BigDecimal quantity = BigDecimal.valueOf(order.getQuantity());
            BigDecimal price = BigDecimal.valueOf(order.getPrice());
            total = total.add(quantity.multiply(price));
        }
        return total;
    }

    //添加订单测试，直接指定默认买家
    public void addOrderTest(Commodity commodity,int quantity){
        SysUser user = userRepo.getOne(2L);
        SysOrder order = new SysOrder();
        order.setCommodity(commodity);
        order.setSysUser(user);
        order.setQuantity(quantity);
        order.setPrice(commodity.getPrice());
        order.setDone(true);
        orderRepo.save(order);
    }

}
