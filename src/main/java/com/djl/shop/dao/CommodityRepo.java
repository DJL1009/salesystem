package com.djl.shop.dao;

import com.djl.shop.dao.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface CommodityRepo extends JpaRepository<Commodity, Long> {

    //更新卖出数量，成功返回影响行数：1，失败返回0
    @Modifying
    @Transactional
    @Query(value = "UPDATE Commodity c SET c.selled =c.selled + :quantity WHERE c.id=:id AND c.quantity> c.selled+:quantity")
    int updateSelled(@Param("id") long id,@Param("quantity") int quantity);
}
