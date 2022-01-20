package com.fanshr.p01.dao;

import com.fanshr.p01.entity.Award;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:22
 * @date : Modified at 2021/11/23 10:22
 */
public interface AwardDao {
    List<Award> queryAwardList(@Param("awardCondition") Award awardCondition, @Param("rowIndex") int rowIndex,
                               @Param("pageSize") int pageSize);

    int queryAwardCount(@Param("awardCondition") Award awardCondition);

    Award queryAwardByAwardId(long awardId);

    int insertAward(Award award);

    int updateAward(Award award);

    int deleteAward(long awardId);

}
