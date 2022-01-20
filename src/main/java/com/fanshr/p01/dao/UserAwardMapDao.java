package com.fanshr.p01.dao;

import com.fanshr.p01.entity.UserAwardMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:23
 * @date : Modified at 2021/11/23 10:23
 */
public interface UserAwardMapDao {


    /**
     *
     * @param userAwardCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<UserAwardMap> queryUserAwardMapList(
            @Param("userAwardCondition") UserAwardMap userAwardCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     *
     * @param userAwardCondition
     * @return
     */
    int queryUserAwardMapCount(
            @Param("userAwardCondition") UserAwardMap userAwardCondition);

    /**
     *
     * @param userAwardId
     * @return
     */
    UserAwardMap queryUserAwardMapById(long userAwardId);

    /**
     *
     * @param userAwardMap
     * @return
     */
    int insertUserAwardMap(UserAwardMap userAwardMap);

    /**
     *
     * @param userAwardMap
     * @return
     */
    int updateUserAwardMap(UserAwardMap userAwardMap);
}
