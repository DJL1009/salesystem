package com.djl.shop.dao;

import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.dao.entity.SysOrder;
import com.djl.shop.dao.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<SysOrder, Long> {

    List<SysOrder> findBySysUserOrderByTimeDesc(SysUser user);
    List<SysOrder> findBySysUser(SysUser user);
    List<SysOrder> findByCommodity(Commodity commodity);
    List<SysOrder> findBySysUserAndCommodity(SysUser user,Commodity commodity);

}
