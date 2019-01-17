package com.djl.shop.controller;

import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    CommodityService commodityService;

    @RequestMapping({"/","/index"})
    public String index(Model model){
        List<Commodity> commodities = commodityService.findAll();
        model.addAttribute("commodities",commodities);
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/show")
    public String show(HttpServletRequest request,Model model){
        int id = Integer.valueOf(request.getParameter("id"));
        model.addAttribute("commodity",commodityService.findById(id));
        return "show";
    }

    @RequestMapping("/edit")
    @Secured("ROLE_SELLER")
    public String edit(HttpServletRequest request,Model model){
        int id = Integer.valueOf(request.getParameter("id"));
        model.addAttribute("commodity",commodityService.findById(id));
        return "edit";
    }

    @RequestMapping(value = "/editSubmit")
    public String editSubmit(Commodity commodity,Model model){
        commodityService.save(commodity);
        model.addAttribute("id",commodity.getId());
        return "publishSubmit";
    }

    @RequestMapping("/publish")
    @Secured("ROLE_SELLER")
    public String publish(){
        return "publish";
    }

    @RequestMapping(value = "/publishSubmit")
    public String publishSubmit(Commodity commodity,Model model){
        commodityService.save(commodity);
        model.addAttribute("id",commodity.getId());
        return "publishSubmit";
    }

}
