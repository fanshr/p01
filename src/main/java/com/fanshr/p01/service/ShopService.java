package com.fanshr.p01.service;

import com.fanshr.p01.dto.ShopExecution;
import com.fanshr.p01.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/11 17:08
 * @date : Modified at 2021/11/11 17:08
 */
public interface ShopService {
     ShopExecution getShopList(Shop shopCondition, int rowIndex, int pageSize);

     ShopExecution getByEmployeeId(long employeedId);

     Shop getByShopId(long shopId);


     ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);

     ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg);
}
