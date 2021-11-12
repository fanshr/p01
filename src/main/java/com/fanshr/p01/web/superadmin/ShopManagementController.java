package com.fanshr.p01.web.superadmin;

import com.fanshr.p01.dto.ShopExecution;
import com.fanshr.p01.entity.Area;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.ShopStateEnum;
import com.fanshr.p01.service.ShopService;
import com.fanshr.p01.util.CodeUtil;
import com.fanshr.p01.util.ParamUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/12 14:56
 * @date : Modified at 2021/11/12 14:56
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopManagementController {


    @Autowired
    ShopService shopService;

    @RequestMapping(value = "/getShopInitInfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();

        // TODO: 填充实际逻辑
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(31L);
        shopCategory.setShopCategoryName("ds");
        ShopCategory shopCategory1 = new ShopCategory();
        shopCategory1.setShopCategoryId(32L);
        shopCategory1.setShopCategoryName("ewr");
        shopCategoryList.add(shopCategory);
        shopCategoryList.add(shopCategory1);

        Area area = new Area();
        Area area1 = new Area();
        area.setAreaId(3L);
        area1.setAreaId(4L);
        area.setAreaName("东边");
        area1.setAreaName("西边");
        areaList.add(area1);
        areaList.add(area);

        modelMap.put("shopCategoryList", shopCategoryList);
        modelMap.put("areaList", areaList);
        modelMap.put("success", true);
        return modelMap;

    }

    @RequestMapping(value = "/registerShop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // TODO: 校验验证码

        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }

        String shopStr = ParamUtil.getString(request, "shopStr");
        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());


        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不可为空");
            return modelMap;
        }

        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (shop != null && shopImg != null) {
            try {
                shop.setOwnerId(8L);
                // TODO: 替换为从Session获取当前用户标识

                ShopExecution se = shopService.addShop(shop, shopImg);
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);

                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
        }

        return modelMap;

    }


}
