package com.djl.shop.controller;

import com.djl.shop.common.JsonResult;
import com.djl.shop.dao.entity.Commodity;
import com.djl.shop.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
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

    @PostMapping("/upload")
    @Secured("ROLE_SELLER")
    public JsonResult upload(@RequestParam("file") MultipartFile img){
        JsonResult result;
        try{
            String imgName = img.getOriginalFilename();
            File dest = new File(imgPath+imgName);
            img.transferTo(dest);
            result = new JsonResult((Object)imgName);
        }catch (IOException e){
            e.printStackTrace();
            result = new JsonResult(e);
        }
        return result;
    }

    @PostMapping("/delete")
    @Secured("ROLE_SELLER")
    public JsonResult delete(HttpServletRequest request){
        JsonResult result;
        try{
            long id= Long.valueOf(request.getParameter("id"));
            commodityService.delete(id);
            result = new JsonResult((Object)"delete success");
        }catch (EmptyResultDataAccessException exception){
            result = new JsonResult(exception);
        }
        return result;
    }

}
