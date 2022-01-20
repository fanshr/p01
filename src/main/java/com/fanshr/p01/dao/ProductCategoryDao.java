package com.fanshr.p01.dao;

import com.fanshr.p01.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 22:45
 * @date : Modified at 2021/11/16 22:45
 */
public interface ProductCategoryDao {
    List<ProductCategory> queryByShopId(long shopId);

    int batchInsertProduct(List<ProductCategory> productCategoryList);

    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,
                              @Param("shopId") long shopId);

}
