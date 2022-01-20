package com.fanshr.p01.service;

import com.fanshr.p01.dto.ShopAuthMapExecution;
import com.fanshr.p01.entity.ShopAuthMap;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:20
 * @date : Modified at 2021/11/23 10:20
 */
public interface ShopAuthMapService {
    /**
     *
     * @param shopId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopAuthMapExecution listShopAuthMapByShopId(Long shopId,
                                                 Integer pageIndex, Integer pageSize);

    /**
     *
     * @param shopAuthMap
     * @return
     * @throws RuntimeException
     */
    ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap)
            throws RuntimeException;

    /**
     * 更新授权信息，包括职位等
     *
     * @return
     * @throws RuntimeException
     */
    ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException;

    /**
     *
     * @param shopAuthMapId
     * @return
     * @throws RuntimeException
     */
    ShopAuthMapExecution removeShopAuthMap(Long shopAuthMapId)
            throws RuntimeException;

    /**
     *
     * @param shopAuthId
     * @return
     */
    ShopAuthMap getShopAuthMapById(Long shopAuthId);
}
