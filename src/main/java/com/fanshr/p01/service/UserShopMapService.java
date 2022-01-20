package com.fanshr.p01.service;

import com.fanshr.p01.dto.UserShopMapExecution;
import com.fanshr.p01.entity.UserShopMap;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:21
 * @date : Modified at 2021/11/23 10:21
 */
public interface UserShopMapService {

    UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition,
                                         int pageIndex, int pageSize);
}
