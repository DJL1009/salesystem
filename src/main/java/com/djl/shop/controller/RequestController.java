package com.djl.shop.controller;

import com.djl.shop.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/api")
public class RequestController {

    @Value("${web.upload-path}")
    private String imgPath;

    @Autowired
    CommodityService commodityService;

    @RequestMapping("/upload")
    @Secured("ROLE_SELLER")
    public String upload(@RequestParam("file") MultipartFile img){

        String imgName = img.getOriginalFilename();
        File dest = new File(imgPath+imgName);
        try {
            img.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgName;
    }

    @PostMapping("/delete")
    @Secured("ROLE_SELLER")
    public void delete(HttpServletRequest request){
        long id= Long.valueOf(request.getParameter("id"));
//        commodityService.remove(id);
        System.out.println("delete=====>"+id);

    }
}
