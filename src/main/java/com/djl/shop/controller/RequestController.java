package com.djl.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.djl.shop.common.JsonResult;
import com.djl.shop.service.CommodityService;
import com.djl.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;



@RestController
@RequestMapping("/api")
public class RequestController {

    @Value("${web.upload-path}")
    private String imgPath;

    @Autowired
    CommodityService commodityService;
    @Autowired
    OrderService orderService;

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

    @PostMapping("/buy")
    @Secured("ROLE_CUSTOMER")
    public JsonResult buy(HttpServletRequest request){
        JsonResult result;
        StringBuffer msg = new StringBuffer();
        String lineString;
        try {
            BufferedReader reader = request.getReader();
            while((lineString = reader.readLine()) != null){
                msg.append(lineString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = JSON.parseArray(msg.toString());
        //事务与减库存===============================================待完成
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = Long.valueOf(jsonObject.getString("id"));
            int number = Integer.valueOf(jsonObject.getString("number"));
            orderService.addOrder(id,number);
        }
        result = new JsonResult((Object)"success");
        return result;
    }
}
