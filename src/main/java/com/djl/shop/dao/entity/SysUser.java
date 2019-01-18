package com.djl.shop.dao.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class SysUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String username;
    private String password;
    //配置用户和角色的多对多关系
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysRole> roles;

    //getter&setter
    public void setUserId(long userId){ this.userId = userId; }
    public long getUserId(){ return this.userId; }
    public void setUsername(String username){ this.username = username; }
    public String getUsername(){ return this.username; }
    public void setPassword(String password){ this.password = password; }
    public String getPassword(){ return this.password; }
    public void setRoles(List<SysRole> roles){ this.roles = roles; }
    private List<SysRole> getRoles(){ return this.roles; }


    //重写getAuthorities方法，将用户的角色作为权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<SysRole> roles = this.getRoles();
        for(SysRole role:roles){
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return auths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
