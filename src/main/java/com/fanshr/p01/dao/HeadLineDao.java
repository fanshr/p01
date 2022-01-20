package com.fanshr.p01.dao;

import com.fanshr.p01.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:22
 * @date : Modified at 2021/11/23 10:22
 */
public interface HeadLineDao {

    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

    HeadLine queryHeadLineById(long lineId);

    List<HeadLine> queryHeadLineByIds(List<Long> lineIdList);

    int insertHeadLine(HeadLine headLine);

    int updateHeadLine(HeadLine headLine);

    int deleteHeadLine(long headLineId);

    int batchDeleteHeadLine(List<Long> lineIdList);
}
