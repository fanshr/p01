package com.fanshr.p01.service;

import com.fanshr.p01.dto.PersonInfoExecution;
import com.fanshr.p01.entity.PersonInfo;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:20
 * @date : Modified at 2021/11/23 10:20
 */
public interface PersonInfoService {

    PersonInfo getPersonInfoById(Long userId);

    PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition, int pageIndex, int pageSize);

    PersonInfoExecution addPersonInfo(PersonInfo personInfo);

    PersonInfoExecution modifyPersonInfo(PersonInfo personInfo);

}
