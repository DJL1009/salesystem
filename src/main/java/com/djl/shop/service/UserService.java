package com.djl.shop.service;

import com.djl.shop.dao.entity.SysRole;
import com.djl.shop.dao.entity.SysUser;
import com.djl.shop.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

@Component("user")
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username){
        SysUser user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    //返回当前登录用户
    public SysUser whoAmI(){
        SysUser me = (SysUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return me;
    }

    public boolean isLogin(){
        return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

    //返回当前用户角色
    public List<String> myRoles(){
        SysUser me = whoAmI();
        List<String> roles = me.getRoles().stream().map(SysRole::getName).collect(Collectors.toList());
        return roles;
    }

    //当前用户是否为卖家
    public boolean isSeller(){
        return myRoles().contains("ROLE_SELLER");
    }
}
