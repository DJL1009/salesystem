package com.djl.shop.dao;

import com.djl.shop.dao.entity.SysOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<SysOrder, Long> {
}
