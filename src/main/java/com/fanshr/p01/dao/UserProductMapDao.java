package com.fanshr.p01.dao;

import com.fanshr.p01.dto.UserProductMapExecution;
import com.fanshr.p01.entity.UserProductMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:24
 * @date : Modified at 2021/11/23 10:24
 */
public interface UserProductMapDao {

    /**
     *
     * @param userProductCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<UserProductMap> queryUserProductMapList(
            @Param("userProductCondition") UserProductMap userProductCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     *
     * @param userProductCondition
     * @return
     */
    int queryUserProductMapCount(
            @Param("userProductCondition") UserProductMap userProductCondition);

    /**
     *
     * @param userProductMap
     * @return
     */
    int insertUserProductMap(UserProductMap userProductMap);
}
