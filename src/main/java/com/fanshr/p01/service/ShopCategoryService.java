package com.fanshr.p01.service;

import com.fanshr.p01.dto.ShopCategoryExecution;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.ShopCategoryStateEnum;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/15 11:13
 * @date : Modified at 2021/11/15 11:13
 */
public interface ShopCategoryService {
    List<ShopCategory> getFirstLevelShopCategoryList();

    List<ShopCategory> getShopCategoryList(Long parentId);

    List<ShopCategory> getAllSecondLevelShopCategory();

    ShopCategory getShopCategoryById(Long shopCategoryId);


    ShopCategoryExecution addShopCategory(ShopCategory shopCategory, CommonsMultipartFile thumbnail);

    ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, CommonsMultipartFile thumbnail,
                                             boolean thumbnailChange);

    ShopCategoryExecution removeShopCategory(long shopCategoryId);

    ShopCategoryExecution removeShopCategoryList(List<Long> shopCategoryIdList);

}
