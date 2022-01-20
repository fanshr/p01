package com.fanshr.p01.service;

import com.fanshr.p01.dto.UserProductMapExecution;
import com.fanshr.p01.entity.UserProductMap;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:21
 * @date : Modified at 2021/11/23 10:21
 */
public interface UserProductMapService {
    /**
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    UserProductMapExecution listUserProductMap(
            UserProductMap userProductCondition, Integer pageIndex,
            Integer pageSize);

    /**
     *
     * @param userProductMap
     * @return
     * @throws RuntimeException
     */
    UserProductMapExecution addUserProductMap(UserProductMap userProductMap)
            throws RuntimeException;
}
