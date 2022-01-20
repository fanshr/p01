package com.fanshr.p01.web.superadmin;

import com.fanshr.p01.dto.Result;
import com.fanshr.p01.dto.ShopExecution;
import com.fanshr.p01.entity.GeneralConstant;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.ErrorCode;
import com.fanshr.p01.enums.ShopStateEnum;
import com.fanshr.p01.service.ShopCategoryService;
import com.fanshr.p01.service.ShopService;
import com.fanshr.p01.util.ParamUtil;
import com.fanshr.p01.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 11:31
 * @date : Modified at 2021/11/16 11:31
 */

@Controller
@RequestMapping("/sa/")
public class ShopController {

    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "listShop", method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> list(HttpServletRequest request) {
        ShopExecution shops = null;
        int pageIndex = ParamUtil.getInt(request, GeneralConstant.PAGE_NO);
        int pageSize = ParamUtil.getInt(request, GeneralConstant.PAGE_SIZE);
        if (pageIndex > 0 && pageSize > 0) {

            Shop shopCondition = new Shop();
            int enableStatus = ParamUtil.getInt(request, "enableStatus");
            if (enableStatus > 0) {
                shopCondition.setEnableStatus(enableStatus);

            }


            long shopCategoryId = ParamUtil.getLong(request, "shopCategoryId");
            if (shopCategoryId > 0) {
                ShopCategory sc = new ShopCategory();
                sc.setShopCategoryId(shopCategoryId);
                shopCondition.setShopCategory(sc);
            }


            String shopName = ParamUtil.getString(request, "shopName");
            if (shopName != null) {
                try {
                    shopCondition.setShopName(URLDecoder.decode(shopName, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return ResultUtil.error(ErrorCode.INVALID_PARAM);
                }
            }

            try {
                 shops = shopService.getShopList(shopCondition, pageIndex, pageSize);
            } catch (Exception e) {
               return ResultUtil.error(e.toString());
            }

            Map<String,Object> data = new HashMap<>();
            if (shops.getShopList() != null) {
                data.put(GeneralConstant.PAGE_SIZE, shops.getShopList());
                data.put(GeneralConstant.TOTAL, shops.getCount());

            }else{
                data.put(GeneralConstant.PAGE_SIZE, new ArrayList<Shop>());
                data.put(GeneralConstant.TOTAL,0);
            }
            return ResultUtil.success(data);
        }else{
            return ResultUtil.error(ErrorCode.INDEX_IS_NULL);
        }
    }


    @RequestMapping(value ="searchShop",method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> searchaShopById(HttpServletRequest request) {
        Shop shop = null;
        int pageIndex = ParamUtil.getInt(request, GeneralConstant.PAGE_NO);
        int pageSize = ParamUtil.getInt(request, GeneralConstant.PAGE_SIZE);
        long shopId = ParamUtil.getLong(request, "shopId");
        if (pageIndex>0&& pageSize>0&&shopId>0){

            try {
                shop = shopService.getByShopId(shopId);
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }


            Map<String,Object> data = new HashMap<>();
            if (shop != null) {
                List<Shop>shopList =new ArrayList<>();
                shopList.add(shop);
                data.put(GeneralConstant.PAGE_SIZE,shopList);
                data.put(GeneralConstant.TOTAL, 1);

            }else{
                data.put(GeneralConstant.PAGE_SIZE, new ArrayList<Shop>());
                data.put(GeneralConstant.TOTAL,0);
            }

            return ResultUtil.success(data);

        }else{
            return ResultUtil.error(ErrorCode.INDEX_IS_NULL);
        }
    }


    @RequestMapping(value = "modifyShop",method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> modifyShop(String shopStr, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {

            return ResultUtil.error(ErrorCode.INVALID_PARAM);
        }

        if (shop!=null&&shop.getShopId()!=null){

            try {
                shop.setShopName((shop.getShopName() == null) ? null
                        : (URLDecoder.decode(shop.getShopName(), "UTF-8")));
                shop.setShopDesc((shop.getShopDesc() == null) ? null
                        : (URLDecoder.decode(shop.getShopDesc(), "UTF-8")));
                shop.setShopAddr((shop.getShopAddr() == null) ? null
                        : (URLDecoder.decode(shop.getShopAddr(), "UTF-8")));
                shop.setAdvice((shop.getAdvice() == null) ? null : (URLDecoder
                        .decode(shop.getAdvice(), "UTF-8")));

                if (shop.getShopCategoryId() != null) {
                    ShopCategory sc = shopCategoryService
                            .getShopCategoryById(shop.getShopCategoryId());
                    if (sc != null) {
                        ShopCategory parentCategory = new ShopCategory();
                        parentCategory.setShopCategoryId(sc.getParentId());
                        shop.setParentCategory(parentCategory);
                    } else {
                        return ResultUtil.error("输入了非法的子类别");

                    }
                }

                ShopExecution se = shopService.modifyShop(shop, null);
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                }else{
                    return ResultUtil.error(se.getStateInfo());
                }
            } catch (UnsupportedEncodingException e) {
               return ResultUtil.error(ErrorCode.INVALID_PARAM);
            }

        }else{
            return ResultUtil.error(ErrorCode.DATA_IS_NULL);
        }
    }
}
