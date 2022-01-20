package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.PersonInfoDao;
import com.fanshr.p01.dto.PersonInfoExecution;
import com.fanshr.p01.entity.PersonInfo;
import com.fanshr.p01.enums.PersonInfoStateEnum;
import com.fanshr.p01.enums.ProductStateEnum;
import com.fanshr.p01.service.PersonInfoService;
import com.fanshr.p01.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:26
 * @date : Modified at 2021/11/23 10:26
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo getPersonInfoById(Long userId) {

        return personInfoDao.queryPersonInfoById(userId);
    }

    @Override
    public PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<PersonInfo> personInfoList = personInfoDao.queryPersonInfoList(personInfoCondition, rowIndex, pageSize);
        int count = personInfoDao.queryPersonInfoCount(personInfoCondition);
        PersonInfoExecution pe = new PersonInfoExecution();
        if (personInfoList != null) {
            pe.setPersonInfoList(personInfoList);
            pe.setCount(count);
            pe.setState(ProductStateEnum.SUCCESS.getState());
        }else{
            pe.setState(PersonInfoStateEnum.INNER_ERROR.getState());
        }
        return pe;
    }

    @Override
    @Transactional
    public PersonInfoExecution addPersonInfo(PersonInfo personInfo) {
        if (personInfo == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
        }else{

            try {
                int effectedRows = personInfoDao.insertPersonInfo(personInfo);

                if (effectedRows>0){
                    personInfo  = personInfoDao.queryPersonInfoById(personInfo.getUserId());
                    return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS, personInfo);
                }else{
                    return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("addPersonInfo error：" +
                        "" + e.toString());
            }

        }
    }

    @Override
    @Transactional
    public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) {
        if (personInfo == null || personInfo.getUserId() == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);

        }else{

            try {
                int effectedRows = personInfoDao.updatePersonInfo(personInfo);
                if (effectedRows <= 0) {
                    return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
                }else{
                    personInfo = personInfoDao.queryPersonInfoById(personInfo.getUserId());
                    return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS, personInfo);
                }
            } catch (Exception e) {
                throw new RuntimeException("updatePersonInfo error：" + e.toString());
            }
        }
    }
}
