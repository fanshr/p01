package com.fanshr.p01.dao;

import com.fanshr.p01.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/12 16:39
 * @date : Modified at 2021/11/12 16:39
 */
public interface ShopCategoryDao {

    List<ShopCategory> queryShopCategory(
            @Param("shopCategoryCondition") ShopCategory shopCategoryCondition
    );

    ShopCategory queryShopCategoryById(Long shopCategoryId);
    List<ShopCategory> queryShopCategoryByIds(List<Long> shopCategoryIdList);

    int insertShopCategory(ShopCategory shopCategory);

    int updateShopCategory(ShopCategory shopCategory);

    int deleteShopCategory(long shopCategoryId);

    int batchDeleteShopCategory(List<Long> shopCategoryIdList);
}
