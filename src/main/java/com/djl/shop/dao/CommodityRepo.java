package com.djl.shop.dao;

import com.djl.shop.dao.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommodityRepo extends JpaRepository<Commodity, Long> {
}
