package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.UserShopMapDao;
import com.fanshr.p01.dto.UserShopMapExecution;
import com.fanshr.p01.entity.UserShopMap;
import com.fanshr.p01.enums.UserShopMapStateEnum;
import com.fanshr.p01.service.UserShopMapService;
import com.fanshr.p01.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:27
 * @date : Modified at 2021/11/23 10:27
 */
@Service
public class UserShopMapServiceImpl implements UserShopMapService {
    @Autowired
    private UserShopMapDao userShopMapDao;
    @Override
    public UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int pageIndex, int pageSize) {

        if (userShopMapCondition != null && pageIndex != -1 && pageSize != -1) {
            int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMapCondition, rowIndex,
                    pageSize);
            int count = userShopMapDao.queryUserShopMapCount(userShopMapCondition);
            UserShopMapExecution ue = new UserShopMapExecution();
            ue.setUserShopMapList(userShopMapList);
            ue.setCount(count);
            ue.setState(UserShopMapStateEnum.SUCCESS.getState());
            return ue;
        }else{
            return new UserShopMapExecution(UserShopMapStateEnum.INNER_ERROR);
        }
    }
}
