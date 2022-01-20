package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.UserAwardMapDao;
import com.fanshr.p01.dao.UserShopMapDao;
import com.fanshr.p01.dto.UserAwardMapExecution;
import com.fanshr.p01.entity.UserAwardMap;
import com.fanshr.p01.entity.UserShopMap;
import com.fanshr.p01.enums.UserAwardMapStateEnum;
import com.fanshr.p01.service.UserAwardMapService;
import com.fanshr.p01.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:26
 * @date : Modified at 2021/11/23 10:26
 */
@Service
public class UserAwardMapServiceImpl implements UserAwardMapService {
    @Autowired
    private UserAwardMapDao userAwardMapDao;
    @Autowired
    private UserShopMapDao userShopMapDao;
    @Override
    public UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardCondition, Integer pageIndex,
                                                  Integer pageSize) {
        if (userAwardCondition != null && pageIndex !=-1 && pageSize!=-1) {
            int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardCondition, rowIndex,
                    pageSize);
            int count = userAwardMapDao.queryUserAwardMapCount(userAwardCondition);
            UserAwardMapExecution ue = new UserAwardMapExecution();
            ue.setUserAwardMapList(userAwardMapList);
            ue.setCount(count);
            ue.setState(UserAwardMapStateEnum.SUCCESS.getState());
            return ue;
        }else{
            return new UserAwardMapExecution(UserAwardMapStateEnum.INNER_ERROR);
        }
    }

    @Override
    public UserAwardMap getUserAwardMapById(long userAwardMapId) {
        return userAwardMapDao.queryUserAwardMapById(userAwardMapId);
    }

    @Override
    @Transactional
    public UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap) throws RuntimeException {
        if (userAwardMap != null && userAwardMap.getUserId() != null && userAwardMap.getAwardId() != null) {
            userAwardMap.setCreateTime(new Date());
            try {
                int effectedRows = 0;
                if (userAwardMap.getPoint() != null && userAwardMap.getPoint() > 0) {
                    UserShopMap userShopMap = userShopMapDao.queryUserShopMap(userAwardMap.getUserId(),
                            userAwardMap.getShopId());
                    if (userShopMap != null) {
                        if (userShopMap.getPoint() >= userAwardMap.getPoint()) {
                            userShopMap.setPoint(userShopMap.getPoint() - userAwardMap.getPoint());
                            effectedRows = userShopMapDao.updateUserShopMapPoint(userShopMap);
                            if (effectedRows <= 0) {
                                throw new RuntimeException("更新积分信息失败");
                            }
                        }else{
                            throw new RuntimeException("积分不足无法领取");
                        }
                    }else{
                        throw new RuntimeException("在本店铺没有积分，无法兑换奖品");
                    }
                }

                effectedRows = userAwardMapDao.insertUserAwardMap(userAwardMap);
                if (effectedRows <= 0) {
                    throw new RuntimeException("领取奖励失败");
                }

                return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS);
            } catch (Exception e) {

                throw new RuntimeException("领取奖励失败：" + e.toString());
            }

        }else{
            return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_INFO);
        }
    }

    @Override
    public UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap) throws RuntimeException {
        if (userAwardMap != null && userAwardMap.getUserAwardId() != null && userAwardMap.getUsedStatus() != null) {

            try {
                int effectedRows = userAwardMapDao.updateUserAwardMap(userAwardMap);
                if (effectedRows <= 0) {
                    return new UserAwardMapExecution(UserAwardMapStateEnum.INNER_ERROR);
                }else{
                    return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
                }
            } catch (Exception e) {

                throw new RuntimeException("modifyUserAwardMap error:" + e.toString());
            }
        }else{
            return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_ID);
        }
    }
}
