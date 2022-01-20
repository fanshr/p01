package com.fanshr.p01.dao;

import com.fanshr.p01.entity.UserShopMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:24
 * @date : Modified at 2021/11/23 10:24
 */
public interface UserShopMapDao {

    List<UserShopMap> queryUserShopMapList(@Param("userShopCondition") UserShopMap userShopMap,
                                           @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    UserShopMap queryUserShopMap(@Param("userId") long userId, @Param("shopId") long shopId);

    int queryUserShopMapCount(@Param("userShopCondition") UserShopMap userShopMapCondition);

    int insertUserShopMap(UserShopMap userShopMap);

    int updateUserShopMapPoint(UserShopMap userShopMap);
}
