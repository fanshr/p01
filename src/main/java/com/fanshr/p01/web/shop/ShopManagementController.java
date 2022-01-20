package com.fanshr.p01.web.superadmin;

import com.fanshr.p01.dto.ShopExecution;
import com.fanshr.p01.entity.Area;
import com.fanshr.p01.entity.PersonInfo;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.ShopStateEnum;
import com.fanshr.p01.service.AreaService;
import com.fanshr.p01.service.ShopCategoryService;
import com.fanshr.p01.service.ShopService;
import com.fanshr.p01.util.CodeUtil;
import com.fanshr.p01.util.ParamUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    private ShopService shopService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> list(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        user = new PersonInfo();
        user.setUserId(8L);
        long employeeId = user.getUserId();

        // TODO: 权限验证及绑定

        List<Shop> list = null;
        try {
            ShopExecution se = shopService.getByEmployeeId(employeeId);
            list = se.getShopList();
            modelMap.put("user", user);
            modelMap.put("shopList", list);
            modelMap.put("success", true);
            request.getSession().setAttribute("shopList", list);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;

    }

    @RequestMapping(value = "getShopById",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(@RequestParam Long shopId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (shopId != null && shopId > -1) {
            Shop shop = shopService.getByShopId(shopId);
            shop.getShopCategory().setShopCategoryName(
                    shopCategoryService.getShopCategoryById(shop.getShopCategory().getShopCategoryId()).getShopCategoryName()
            );

            shop.getParentCategory().setShopCategoryName(
                    shopCategoryService.getShopCategoryById(shop.getParentCategory().getShopCategoryId()).getShopCategoryName()

            );
            modelMap.put("shop", shop);
            request.getSession().setAttribute("currentShop",shop);
            List<Area> areaList = new ArrayList<>();
            try {
                areaList = areaService.getAreaList();
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }

    @RequestMapping(value = "/getShopInitInfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();

        try {
            shopCategoryList = shopCategoryService.getAllSecondLevelShopCategory();
            areaList = areaService.getAreaList();
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMst", e.toString());
        }

        modelMap.put("shopCategoryList", shopCategoryList);
        modelMap.put("areaList", areaList);
        modelMap.put("success", true);
        return modelMap;

    }

    @RequestMapping(value = "/registerShop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        if (!CodeUtil.checkVerifyCode(request)) {
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

                PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
                user = new PersonInfo();
                user.setUserId(8L);
                shop.setOwnerId(user.getUserId());


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

    @RequestMapping(value = "/modifyShop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        boolean statusChange = ParamUtil.getBoolean(request, "statusChange");
        Map<String, Object> modelMap = new HashMap<>();
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }

        Shop shop = null;
        String shopStr = ParamUtil.getString(request, "shopStr");
        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");

        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        shop.setShopImg(currentShop.getShopImg());

        if (shop != null && shop.getShopId() != null) {
            filterAttribute(shop);

            try {
                ShopExecution se = shopService.modifyShop(shop, shopImg);
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
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

    private void filterAttribute(Shop shop) {
    }

}
