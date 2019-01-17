package com.djl.shop.service;

import com.djl.shop.dao.CommodityRepo;
import com.djl.shop.dao.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommodityService {
    @Autowired
    private CommodityRepo commodityRepo;

    @CachePut(value = "commodity",key = "#commodity.id")
    public void save(Commodity commodity){
        commodityRepo.save(commodity);
    }

    @Cacheable(value = "commodity")
    public List findAll(){
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
    public void remove(Long id){
        commodityRepo.deleteById(id);
    }

    public void update(Commodity commodity){

    }
}
