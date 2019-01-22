package com.djl.shop.config;

import com.djl.shop.handler.LoginSuccessHandler;
import com.djl.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebMvcSecurity                                       //EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)          //对方法进行权限控制（需要控制权限的方法使用@Secured注解）
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService userService(){
        return new UserService();
    }

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    //启用方法权限控制所需要的bean
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    //配置user-detail服务
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService());
    }

    //配置如何通过拦截器保护请求
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers("/css/**", "/js/**","/images/**","/img/**","/index","/","/show","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()                               //定义登录方式为form表单登录
                    .loginPage("/login")                   //自定义登录页面
                    .successHandler(loginSuccessHandler)
                    //.failureUrl("/error")                //登录失败页面,默认跳转回登录界面
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll();
    }
}
