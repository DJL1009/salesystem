package com.djl.shop.service;

import com.djl.shop.dao.entity.SysUser;
import com.djl.shop.dao.SysUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SysUserService implements UserDetailsService {
    @Autowired
    SysUserRepo sysUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username){
        SysUser user = sysUserRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
