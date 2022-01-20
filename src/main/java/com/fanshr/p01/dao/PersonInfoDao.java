package com.fanshr.p01.dao;

import com.fanshr.p01.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:23
 * @date : Modified at 2021/11/23 10:23
 */
public interface PersonInfoDao {
    List<PersonInfo> queryPersonInfoList(@Param("personInfoCondition")PersonInfo personInfoCondition,
                                         @Param("rowIndex") int rowIndex,
                                                 @Param("pageSize") int pageSize);

    int queryPersonInfoCount(@Param("personInfoCondition") PersonInfo personInfoCondition);

    PersonInfo queryPersonInfoById(long userId);

    int insertPersonInfo(PersonInfo personInfo);

    int updatePersonInfo(PersonInfo personInfo);

    int deletePersonInfo(long userId);

}
