package com.fanshr.p01.dao;

import com.fanshr.p01.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/11 15:27
 * @date : Modified at 2021/11/11 15:27
 */
public interface ShopDao {

    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    int queryShopCount(@Param("shopCondition") Shop shopCondition);
    List<Shop> queryByEmployeeId(long employeeId);

    Shop queryByShopId(long shopId);


    /**
     * 新增店铺信息
     * @param shop 店铺对象
     * @return effectedRows 受影响的行数
     */
    int insertShop(Shop shop);

    int updateShop(Shop shop);

    int deleteShopByName(String shopName);


}
