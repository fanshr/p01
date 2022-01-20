package com.fanshr.p01.web.shop;

import com.fanshr.p01.dto.ProductExecution;
import com.fanshr.p01.dto.Result;
import com.fanshr.p01.entity.Product;
import com.fanshr.p01.entity.ProductCategory;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.enums.ErrorCode;
import com.fanshr.p01.enums.ProductStateEnum;
import com.fanshr.p01.service.ProductCategoryService;
import com.fanshr.p01.service.ProductService;
import com.fanshr.p01.util.CodeUtil;
import com.fanshr.p01.util.ParamUtil;
import com.fanshr.p01.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
 * @date : Created at 2021/11/17 15:24
 * @date : Modified at 2021/11/17 15:24
 */

@Controller
@RequestMapping("/shop/")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "listProductsByShop", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> listProdcutByShop(HttpServletRequest request) {
        int pageIndex = ParamUtil.getInt(request, "pageIndex");
        int pageSize = ParamUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if (pageIndex > -1 && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            long productCategoryId = ParamUtil.getLong(request, "productCategoryId");
            String productName = ParamUtil.getString(request, "productName");
            Product productCondition = compactProductCondition4Search(currentShop.getShopId(), productCategoryId,
                    productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            Map<String, Object> data = new HashMap<>();
            data.put("productList", pe.getProductList());
            data.put("count", pe.getCount());
            return ResultUtil.success(data);
        } else {
            return ResultUtil.error("empty pageSize or pageIndex or shopId");
        }

    }

    private Product compactProductCondition4Search(Long shopId, long productCategoryId, String productName) {
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

        return productCondition;
    }


    @RequestMapping(value = "getProductById", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> getProductById(@RequestParam Long productId) {
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList =
                    productCategoryService.getByShopId(product.getShop().getShopId());
            Map<String, Object> data = new HashMap<>();
            data.put("product", product);
            data.put("productCategoryList", productCategoryList);
            return ResultUtil.success(data);

        } else {
            return ResultUtil.error(ErrorCode.MISTYPE_PARAM);
        }

    }

    @RequestMapping(value = "getProductCategoryListByShopId", method = RequestMethod.GET)
    @ResponseBody
    private Result<ModelMap> getProductCategoryListByShopId(HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() != null) {
            List<ProductCategory> productCategoryList = productCategoryService.getByShopId(currentShop.getShopId());
            ModelMap data = new ModelMap();

            data.put("productCategoryList", productCategoryList);
            return ResultUtil.success(data);
        } else {
            return ResultUtil.error(ErrorCode.DATA_IS_NULL);
        }


    }

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    @ResponseBody
    private Result addProduct(HttpServletRequest request) {
        if (!CodeUtil.checkVerifyCode(request)) {
            return ResultUtil.error("验证码校验失败");
        }

        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = ParamUtil.getString(request, "productStr");

        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile thumnail = null;
        List<CommonsMultipartFile> productImgs = new ArrayList<>();
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                thumnail = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImg != null) {
                        productImgs.add(productImg);
                    }
                }
            } else {
                return ResultUtil.error("上传图片不可为空");
            }
        } catch (Exception e) {
            return ResultUtil.error(e.toString());
        }

        try {
            product = mapper.readValue(productStr,Product.class);
        } catch (IOException e) {
            return ResultUtil.error(ErrorCode.INVALID_PARAM);
        }

        if (product != null && thumnail != null && productImgs.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.addProduct(product, thumnail, productImgs);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(pe.getStateInfo());
                }
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }
        } else {
            return ResultUtil.error("请输入商品信息");
        }
    }


    @RequestMapping(value = "modifyProduct", method = RequestMethod.POST)
    @ResponseBody
    private Result modifyProduct(HttpServletRequest request) {

        boolean statusChange = ParamUtil.getBoolean(request, "statusChange");
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            return ResultUtil.error("验证码校验失败");
        }

        String productStr = ParamUtil.getString(request, "productStr");
        Product product = null;
        ObjectMapper mapper = new ObjectMapper();

        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile thumbnail = null;
        List<CommonsMultipartFile> productImgs = new ArrayList<>();
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            thumbnail = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");

            for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                if (productImg != null) {
                    productImgs.add(productImg);
                }

            }
        }


        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            return ResultUtil.error(ErrorCode.INVALID_PARAM);
        }

        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgs);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(pe.getStateInfo());
                }
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }
        } else {
            return ResultUtil.error(ErrorCode.DATA_IS_NULL);
        }

    }


}
