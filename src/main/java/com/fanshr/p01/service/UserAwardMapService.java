package com.fanshr.p01.service;

import com.fanshr.p01.dto.UserAwardMapExecution;
import com.fanshr.p01.entity.UserAwardMap;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:20
 * @date : Modified at 2021/11/23 10:20
 */
public interface UserAwardMapService {

    /**
     *
     * @param userAwardCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardCondition,
                                           Integer pageIndex, Integer pageSize);

    /**
     *
     * @param userAwardMapId
     * @return
     */
    UserAwardMap getUserAwardMapById(long userAwardMapId);

    /**
     *
     * @param userAwardMap
     * @return
     * @throws RuntimeException
     */
    UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap)
            throws RuntimeException;

    /**
     *
     * @param userAwardMap
     * @return
     * @throws RuntimeException
     */
    UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap)
            throws RuntimeException;
}
