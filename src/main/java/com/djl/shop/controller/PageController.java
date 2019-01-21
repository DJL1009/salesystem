package com.djl.shop.controller;

import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.dao.entity.SysOrder;
import com.djl.shop.dao.entity.SysUser;
import com.djl.shop.service.CommodityService;
import com.djl.shop.service.OrderService;
import com.djl.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    CommodityService commodityService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService user;

    @GetMapping({"/","/index"})
    public String index(Model model){
        List<Commodity> commodities = commodityService.findAll();
        model.addAttribute("commodities",commodities);
        if(user.isLogin()){
            if(user.isSeller()){
                List<Commodity> selled = commodityService.selled();
                model.addAttribute("selled",selled);
            }else{
                List<Commodity> purchased = commodityService.purchased(user.whoAmI());
                model.addAttribute("purchased",purchased);
            }
        }
        return "index";
    }

    @GetMapping(value = {"/","/index"},params = "type")
    @Secured("ROLE_CUSTOMER")
    public String indexCustomer(Model model){
        List<Commodity> purchased = commodityService.purchased(user.whoAmI());
        List<Commodity> notPurchased = commodityService.notPurchased(user.whoAmI());
        model.addAttribute("commodities",notPurchased);
        model.addAttribute("purchased",purchased);
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/show")
    public String show(HttpServletRequest request,Model model){
        int id = Integer.valueOf(request.getParameter("id"));
        Commodity commodity = commodityService.findById(id);
        model.addAttribute("commodity",commodity);
        if(user.isLogin() && !user.isSeller()){
            model.addAttribute("recentPrice",orderService.recentPrice(user.whoAmI(),commodity));
        }
        return "show";
    }

    @GetMapping("/publish")
    @Secured("ROLE_SELLER")
    public String publish(){
        return "publish";
    }

    @GetMapping("/edit")
    @Secured("ROLE_SELLER")
    public String edit(HttpServletRequest request,Model model){
        int id = Integer.valueOf(request.getParameter("id"));
        model.addAttribute("commodity",commodityService.findById(id));
        return "edit";
    }

    @PostMapping(value = "/editSubmit")
    @Secured("ROLE_SELLER")
    public String editSubmit(Commodity commodity,Model model){
        long id = commodity.getId();
        //编辑后库存量
        int newStock = commodity.getQuantity();
        //已售数量
        int selledQuantity = commodityService.findById(id).getSelled();
        //设置新的总量
        commodity.setQuantity(newStock+selledQuantity);
        commodity.setSelled(selledQuantity);
        commodityService.save(commodity);
        model.addAttribute("id",commodity.getId());
        return "publishSubmit";
    }

    @PostMapping(value = "/publishSubmit")
    @Secured("ROLE_SELLER")
    public String publishSubmit(Commodity commodity,Model model){
        commodityService.save(commodity);
        model.addAttribute("id",commodity.getId());
        return "publishSubmit";
    }

    @GetMapping("/settleAccount")
    @Secured("ROLE_CUSTOMER")
    public String settleAccount(){
        return "settleAccount";
    }

    @GetMapping("/account")
    @Secured("ROLE_CUSTOMER")
    public String account(Model model){
        List<SysOrder> orders = user.whoAmI().getSysOrders();
        model.addAttribute("orders",orders);
        model.addAttribute("total",orderService.calTotalPrice(orders));
        return "account";
    }
}
