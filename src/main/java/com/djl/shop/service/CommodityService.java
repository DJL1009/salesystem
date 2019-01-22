package com.djl.shop.service;

import com.djl.shop.dao.CommodityRepo;
import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.dao.entity.SysOrder;
import com.djl.shop.dao.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommodityService {
    @Autowired
    private CommodityRepo commodityRepo;
    @Autowired
    private OrderService orderService;

    @CachePut(value = "commodity",key = "#commodity.id")
    public void save(Commodity commodity){
        commodityRepo.save(commodity);
    }

    @Cacheable(value = "commodity")
    public List<Commodity> findAll(){
        return commodityRepo.findAll();
    }

    @Cacheable(value = "commodity")
    public Commodity findById(long id){
        Optional<Commodity> c = commodityRepo.findById(id);
        if(c.isPresent())
            return commodityRepo.findById(id).get();
        else
            return null;
    }

    @CacheEvict(value = "commodity")
    public void delete(Long id){
        commodityRepo.deleteById(id);
    }


    //购买商品
    public void buy(Long id,int quantity) throws Exception {
        Commodity commodity = commodityRepo.getOne(id);
        if(commodity == null){
            throw new Exception("商品已下架！");
        }
        //检查商品库存
        int stock = commodity.getQuantity() - commodity.getSelled();
        if(stock >= quantity){
            int selled = commodity.getSelled();
            //增加已售数量
            commodity.setSelled(selled+quantity);
            commodityRepo.save(commodity);
            //添加完成订单
            orderService.addOrder(commodity,quantity);
        }else{
            throw new Exception("商品库存不足！");
        }
    }

    //返回指定用户已购买商品列表（去重）
    public List<Commodity> purchased(SysUser user){
        List<SysOrder> orders = orderService.findByUserId(user.getId());
        List<Commodity> purchased = orders.stream().map(SysOrder::getCommodity).collect(Collectors.toList());
        Set<Commodity> purchasedCom = new HashSet<>(purchased);
        purchased.clear();
        purchased.addAll(purchasedCom);
        return purchased;
    }

    public List<Commodity> notPurchased(SysUser user){
        List<Commodity> commodities = commodityRepo.findAll();
        List<Commodity> purchased = purchased(user);
        commodities.removeAll(purchased);
        return commodities;
    }

    //已售商品列表
    public List<Commodity> selled(){
        List<SysOrder> orders = orderService.findAll();
        List<Commodity> selled = orders.stream().map(SysOrder::getCommodity).collect(Collectors.toList());
        return selled;
    }

}
