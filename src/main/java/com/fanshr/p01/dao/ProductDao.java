package com.fanshr.p01.dao;

import com.fanshr.p01.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 22:58
 * @date : Modified at 2021/11/16 22:58
 */
public interface ProductDao {

    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    int queryProductCount(@Param("productCondition") Product productCondition);

    Product queryProductByProductId(long productId);

    int insertProduct(Product product);

    int updateProduct(Product product);

    int updateProductCategoryToNULL(long productCategoryId);

    int deleteProduct(@Param("productId") long productId,
                      @Param("shopId") long shopId);
}
