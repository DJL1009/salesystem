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
    private long id;

    private String username;
    private String password;

    //配置用户和角色的多对多关系
    //FetchType.EAGER 预先抓取，与延迟加载相对，在一次操作时将数据全部从数据库中取出
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysRole> roles;

    //FetchType.Lazy 延迟加载，允许只在需要的时候获取相关联的数据
    //CascadeType.REMOVE 级联删除
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE},mappedBy = "sysUser")
    private List<SysOrder> sysOrders;

    public void setId(long id){ this.id = id; }

    public long getId(){ return this.id; }

    public void setUsername(String username){ this.username = username; }

    public String getUsername(){ return this.username; }

    public void setPassword(String password){ this.password = password; }

    public String getPassword(){ return this.password; }

    public void setRoles(List<SysRole> roles){ this.roles = roles; }

    public List<SysRole> getRoles(){ return this.roles; }

    public List<SysOrder> getSysOrders() { return this.sysOrders; }

    public void setOrders(List<SysOrder> sysOrders) { this.sysOrders = sysOrders; }


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
