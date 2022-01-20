package com.fanshr.p01.web.shop;

import com.fanshr.p01.dto.ProductCategoryExecution;
import com.fanshr.p01.dto.Result;
import com.fanshr.p01.entity.ProductCategory;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.enums.ErrorCode;
import com.fanshr.p01.enums.ProductCategoryStateEnum;
import com.fanshr.p01.service.ProductCategoryService;
import com.fanshr.p01.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 15:24
 * @date : Modified at 2021/11/17 15:24
 */
@Controller
@RequestMapping("/shop/")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "listProductCategories", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> listProductCategories(HttpServletRequest request) {

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getByShopId(currentShop.getShopId());
            return ResultUtil.success(list);
        } else {
            return ResultUtil.error(ErrorCode.INNER_ERROR);
        }
    }

    @RequestMapping(value = "addProductCategories",method = RequestMethod.POST)
    @ResponseBody
    private Result addProductCategories(@RequestBody List<ProductCategory> productCategoryList,
                                        HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }

        if (productCategoryList != null && productCategoryList.size() > 0) {

            try {
                ProductCategoryExecution pce =
                        productCategoryService.batchAddProductCategory(productCategoryList);
                if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                }else{
                    return ResultUtil.error(pce.getStateInfo());
                }
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }

        }else{
            return ResultUtil.error("请至少输入一个商品类别");
        }
    }

    @RequestMapping(value = "removeProductCategory",method = RequestMethod.POST)
    @ResponseBody
    private Result removeProductCategory(Long productCategoryId,HttpServletRequest request) {
        if (productCategoryId!=null && productCategoryId>0){

            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pce =
                        productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                }else{
                    return ResultUtil.error(pce.getStateInfo());
                }
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }
        }else{
            return ResultUtil.error("请至少选择一个商品类别");
        }

    }
}