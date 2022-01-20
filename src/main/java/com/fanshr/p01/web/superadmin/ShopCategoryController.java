package com.fanshr.p01.web.superadmin;

import com.fanshr.p01.dto.Result;
import com.fanshr.p01.dto.ShopCategoryExecution;
import com.fanshr.p01.entity.GeneralConstant;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.ErrorCode;
import com.fanshr.p01.enums.ShopCategoryStateEnum;
import com.fanshr.p01.service.ShopCategoryService;
import com.fanshr.p01.util.ParamUtil;
import com.fanshr.p01.util.ResultUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 11:30
 * @date : Modified at 2021/11/16 11:30
 */

@Controller
@RequestMapping(value = "/sa")
public class ShopCategoryController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @RequestMapping(value = "/listShopCategories", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopCategories() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> list = new ArrayList<>();
        try {
            list = shopCategoryService.getShopCategoryList(null);
            modelMap.put(GeneralConstant.PAGE_SIZE, list);
            modelMap.put(GeneralConstant.TOTAL, list.size());
        } catch (Exception e) {

            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }


    @RequestMapping(value = "/list1stLevelShopCategories", method = RequestMethod.POST)
    @ResponseBody
    private Result<List<ShopCategory>> list1stLevelShopCategories() {
        List<ShopCategory> list = new ArrayList<>();
        try {
            list = shopCategoryService.getFirstLevelShopCategoryList();
        } catch (Exception e) {
            return ResultUtil.error(ErrorCode.INNER_ERROR);
        }

        return ResultUtil.success(list);
    }

    private Result addShopCategory(HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        ShopCategory shopCategory = null;
        String shopCategoryStr = ParamUtil.getString(request, "shopCategoryStr");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile thumbnail = (CommonsMultipartFile) multipartRequest.getFile(
                "shopCategoryManagementAdd_shopCategoryImg");

        try {
            shopCategory = mapper.readValue(shopCategoryStr, ShopCategory.class);
        } catch (IOException e) {

            return ResultUtil.error(ErrorCode.INVALID_PARAM, e.toString());
        }

        if (shopCategory != null && thumbnail != null) {
            try {
                shopCategory.setShopCategoryName(shopCategory.getShopCategoryName() == null ? null :
                        URLDecoder.decode(shopCategory.getShopCategoryName(), "UTF-8"));
                shopCategory.setShopCategoryDesc(shopCategory.getShopCategoryDesc() == null ? null :
                        URLDecoder.decode(shopCategory.getShopCategoryDesc(), "UTF-8"));
                ShopCategoryExecution sce = shopCategoryService.addShopCategory(shopCategory,
                        thumbnail);
                if (sce.getState() == ShopCategoryStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(sce.getStateInfo());
                }

            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }
        } else {
            return ResultUtil.error(ErrorCode.DATA_IS_NULL);
        }
    }


    @RequestMapping(value = "/modifyShopCategory", method = RequestMethod.POST)
    private Result modifyShopCategory(HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        ShopCategory shopCategory = null;
        String shopCategoryStr = ParamUtil.getString(request, "shopCategoryStr");
        MultipartHttpServletRequest multiparatRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile thumbnail = (CommonsMultipartFile) ((MultipartHttpServletRequest) request).getFile(
                "shopCategoryManagementEdit_shopCategoryImg");

        try {
            shopCategory = mapper.readValue(shopCategoryStr, ShopCategory.class);
        } catch (Exception e) {
            return ResultUtil.error(ErrorCode.INVALID_PARAM, e.toString());
        }

        if (shopCategory != null && shopCategory.getShopCategoryId() != null) {

            try {
                boolean thumbnailChange = ParamUtil.getBoolean(request, "thumbnailChange");
                shopCategory.setShopCategoryName(shopCategory.getShopCategoryName() == null ? null :
                        URLDecoder.decode(shopCategory.getShopCategoryName(), "UTF-8"));
                shopCategory.setShopCategoryDesc(shopCategory.getShopCategoryDesc() == null ? null :
                        URLDecoder.decode(shopCategory.getShopCategoryDesc(), "UTF-8"));

                ShopCategoryExecution sce = shopCategoryService
                        .modifyShopCategory(shopCategory, thumbnail,
                                thumbnailChange);
                if (sce.getState() == ShopCategoryStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(sce.getStateInfo());
                }
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }

        } else {
            return ResultUtil.error(ErrorCode.ID_IS_NULL);
        }


    }


    @RequestMapping(value = "/removeShopCategory", method = RequestMethod.POST)
    @ResponseBody
    private Result removeShopCategory(Long shopCategoryId) {

        if (shopCategoryId != null && shopCategoryId > 0) {
            ShopCategoryExecution sce = shopCategoryService.removeShopCategory(shopCategoryId);
            if (sce.getState() == ShopCategoryStateEnum.SUCCESS.getState()) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(sce.getStateInfo());
            }
        } else {
            return ResultUtil.error(ErrorCode.ID_IS_NULL);
        }
    }

    @RequestMapping(value = "removeShopCategories", method = RequestMethod.POST)
    @ResponseBody
    private Result removeShopCategories(String shopCategoryIdListStr) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Long.class);
        List<Long> shopCategoryIdList = null;
        try {
            shopCategoryIdList = mapper.readValue(shopCategoryIdListStr, javaType);
        } catch (IOException e) {
            return ResultUtil.error(ErrorCode.INVALID_PARAM, e.toString());

        }

        if (shopCategoryIdList != null && shopCategoryIdList.size() > 0) {

            ShopCategoryExecution sce =
                    shopCategoryService.removeShopCategoryList(shopCategoryIdList);
            if (sce.getState() == ShopCategoryStateEnum.SUCCESS.getState()) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(sce.getStateInfo());
            }
        } else {
            return ResultUtil.error(ErrorCode.EMPTY);
        }

    }
}
