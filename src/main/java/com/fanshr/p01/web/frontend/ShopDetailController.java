package com.fanshr.p01.web.frontend;

import com.fanshr.p01.dto.ProductExecution;
import com.fanshr.p01.dto.Result;
import com.fanshr.p01.entity.Product;
import com.fanshr.p01.entity.ProductCategory;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.service.ProductCategoryService;
import com.fanshr.p01.service.ProductService;
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
@RequestMapping("/frontend/")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;


    @RequestMapping(value = "listShopDetailPageInfo", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> listShopDetailPageInfo(
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = ParamUtil.getLong(request, "shopId");
        Shop shop = null;

        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            shop = shopService.getByShopId(shopId);
            productCategoryList = productCategoryService.getByShopId(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            return ResultUtil.success(modelMap);
        }else{
            return ResultUtil.error("empty shopId");
        }

    }

    @RequestMapping(value = "/listProductsByShop", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = ParamUtil.getInt(request, "pageIndex");
        int pageSize = ParamUtil.getInt(request, "pageSize");
        long shopId = ParamUtil.getLong(request, "shopId");
        if (pageIndex>-1 &&pageSize>-1 &&shopId>-1){
            long productCategoryId = ParamUtil.getLong(request, "productCategoryId");
            String productName = ParamUtil.getString(request, "productName");
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            return ResultUtil.success(modelMap);
        }else{
            return ResultUtil.error("empty pageSize or pageIndex or shopId");
        }


    }

    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }

        if (productName != null) {
            productCondition.setProductName(productName);
        }
        productCondition.setEnableStatus(1);
        return productCondition;
    }

}
