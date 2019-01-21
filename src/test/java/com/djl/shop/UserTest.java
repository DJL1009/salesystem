package com.djl.shop;

import com.djl.shop.dao.UserRepo;
import com.djl.shop.dao.entity.SysRole;
import com.djl.shop.dao.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private UserRepo userRepo;
    @Test
    public void getRoles(){
        SysUser user = userRepo.getOne(2L);
        List<SysRole> roles = user.getRoles();
        List<String> role = roles.stream().map(SysRole::getName).collect(Collectors.toList());
        System.out.println(role);
        System.out.println("=================================="+role.contains("ROLE_CUSTOMER"));
    }
    @Test
    public void xx(){
        BigDecimal x = BigDecimal.valueOf(0.27);
        BigDecimal y = BigDecimal.valueOf(6000.09);
        BigDecimal z = x.add(y);

        System.out.println("x+y="+z);
    }
}
