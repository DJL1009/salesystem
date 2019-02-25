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

    /**
     * 返回当前用户
     * @return SysUser
     */
    public SysUser whoAmI(){
        return  (SysUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    /**
     * 判断是否已登录
     * @return boolean
     */
    public boolean isLogin(){
        return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

    /**
     * 判断当前用户是否为买家？
     * @return boolean
     */
    public boolean isSeller(){
        return myRoles().contains("ROLE_SELLER");
    }

    /**
     * 返回当前用户角色名称列表
     * @return List<String>
     */
    private List<String> myRoles(){
        SysUser me = whoAmI();
        return  me.getRoles()
                .stream().map(SysRole::getName)
                .collect(Collectors.toList());
    }
}
