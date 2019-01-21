package com.djl.shop.dao;

import com.djl.shop.dao.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<SysUser,Long> {
    SysUser findByUsername(String username);
}
