package com.fanshr.p01.service;

import com.fanshr.p01.dto.ProductCategoryExecution;
import com.fanshr.p01.entity.ProductCategory;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 10:40
 * @date : Modified at 2021/11/17 10:40
 */
public interface ProductCategoryService {
    List<ProductCategory> getByShopId(long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList);

    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId);
}
