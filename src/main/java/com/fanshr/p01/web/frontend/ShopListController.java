package com.fanshr.p01.web.frontend;

import com.fanshr.p01.dto.Result;
import com.fanshr.p01.dto.ShopExecution;
import com.fanshr.p01.entity.Area;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.service.AreaService;
import com.fanshr.p01.service.ShopCategoryService;
import com.fanshr.p01.service.ShopService;
import com.fanshr.p01.util.ParamUtil;
import com.fanshr.p01.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:14
 * @date : Modified at 2021/11/23 10:14
 */
@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/listShopsPageInfo", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long parentId = ParamUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(parentId);
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }

        } else {
            try {
                shopCategoryList = shopCategoryService.getFirstLevelShopCategoryList();
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }

        }

        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            return ResultUtil.success(modelMap);
        } catch (Exception e) {
            return ResultUtil.error(e.toString());
        }

    }


    @RequestMapping(value = "/listShops", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = ParamUtil.getInt(request, "pageIndex");
        int pageSize = ParamUtil.getInt(request, "pageSize");
        if (pageIndex > -1 && pageSize > -1) {
            long parentId = ParamUtil.getLong(request, "parentId");
            long shopCategoryId = ParamUtil.getLong(request, "shopCatetoryId");
            long areaId = ParamUtil.getLong(request, "areaId");
            String shopName = ParamUtil.getString(request, "shopName");
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            return ResultUtil.success(modelMap);
        } else {

            return ResultUtil.success("empty pageSize or pageIndex");

        }

    }

    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, long areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            shopCondition.setParentCategory(parentCategory);
        }

        if (shopCategoryId != -1L) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }

        if (areaId != -1L) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);

        }

        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        shopCondition.setEnableStatus(1);
        return shopCondition;

    }
}