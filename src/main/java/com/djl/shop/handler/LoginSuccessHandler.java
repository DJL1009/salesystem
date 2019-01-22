package com.djl.shop.handler;

import com.djl.shop.dao.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException,ServletException{
        SysUser user = (SysUser)authentication.getPrincipal();
        System.out.println("用户"+user.getUsername()+"登录成功");
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("products")){
                    /*
                    *
                    * 待扩展：
                    *   处理cookie
                    *   将cookie购物车数据持久化到数据库
                    *
                    * */
                    System.out.println("购物车不为空：");
                    System.out.println(cookie.getValue());
                }
            }
        }
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
