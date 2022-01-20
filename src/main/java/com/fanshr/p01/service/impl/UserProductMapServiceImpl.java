package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.PersonInfoDao;
import com.fanshr.p01.dao.ShopDao;
import com.fanshr.p01.dao.UserProductMapDao;
import com.fanshr.p01.dao.UserShopMapDao;
import com.fanshr.p01.dto.UserProductMapExecution;
import com.fanshr.p01.entity.PersonInfo;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.entity.UserProductMap;
import com.fanshr.p01.entity.UserShopMap;
import com.fanshr.p01.enums.UserProductMapStateEnum;
import com.fanshr.p01.service.UserProductMapService;
import com.fanshr.p01.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:27
 * @date : Modified at 2021/11/23 10:27
 */
@Service
public class UserProductMapServiceImpl implements UserProductMapService {
    @Autowired
    private UserProductMapDao userProductMapDao;
    @Autowired
    private UserShopMapDao userShopMapDao;
    @Autowired
    private PersonInfoDao personInfoDao;
    @Autowired
    private ShopDao shopDao;


    @Override
    public UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex,
                                                      Integer pageSize) {
        if (userProductCondition != null && pageIndex != null && pageSize != null) {
            int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapList(userProductCondition,
                    rowIndex, pageSize);
            int count = userProductMapDao.queryUserProductMapCount(userProductCondition);
            UserProductMapExecution ue = new UserProductMapExecution();
            ue.setUserProductMapList(userProductMapList);
            ue.setCount(count);
            ue.setState(UserProductMapStateEnum.SUCCESS.getState());
            return ue;

        }else{
            return new UserProductMapExecution(UserProductMapStateEnum.INNER_ERROR);
        }


    }

    @Override
    @Transactional
    public UserProductMapExecution addUserProductMap(UserProductMap userProductMap) throws RuntimeException {
        if (userProductMap != null && userProductMap.getUserId() != null && userProductMap.getShopId() != null) {
            userProductMap.setCreateTime(new Date());
            try {
                int effectedRows = userProductMapDao.insertUserProductMap(userProductMap);
                if (effectedRows <= 0) {
                    throw new RuntimeException("添加消费记录失败");
                }

                if (userProductMap.getPoint() != null&&userProductMap.getPoint()>0) {
                    UserShopMap userShopMap = userShopMapDao.queryUserShopMap(userProductMap.getUserId(),
                            userProductMap.getShopId());
                    if (userShopMap != null) {
                        if (userShopMap.getPoint()>=userProductMap.getPoint()) {
                            userShopMap.setPoint(userShopMap.getPoint() + userProductMap.getPoint());
                            effectedRows = userShopMapDao.updateUserShopMapPoint(userShopMap);
                            if (effectedRows <= 0) {
                                throw new RuntimeException("更新积分信息失败");
                            }

                        }
                    }else{
                        // 无消费记录的情况下，添加一条积分信息
                        userShopMap = compactUserShopMap4Add(userProductMap.getUserId(), userProductMap.getShopId(),
                                userProductMap.getPoint());
                        effectedRows = userShopMapDao.insertUserShopMap(userShopMap);
                        if (effectedRows <= 0) {
                            throw new RuntimeException("积分信息创建失败");
                        }
                    }

                }
                return new UserProductMapExecution(UserProductMapStateEnum.SUCCESS, userProductMap);
            } catch (Exception e) {
                throw new RuntimeException("添加授权失败：" + e.toString());

            }
        }else {
            return new UserProductMapExecution(UserProductMapStateEnum.NULL_USERPRODUCT_INFO);
        }
    }

    private UserShopMap compactUserShopMap4Add(Long userId, Long shopId, Integer point) {
        UserShopMap userShopMap = null;
        if (userId != null && shopId != null) {
            userShopMap = new UserShopMap();
            PersonInfo personInfo = personInfoDao.queryPersonInfoById(userId);
            Shop shop = shopDao.queryByShopId(shopId);
            userShopMap.setUserId(userId);
            userShopMap.setShopId(shopId);
            userShopMap.setUser(personInfo);
            userShopMap.setShop(shop);
            userShopMap.setUserName(personInfo.getName());
            userShopMap.setShopName(shop.getShopName());
            userShopMap.setCreateTime(new Date());
            userShopMap.setPoint(point);
        }

        return userShopMap;

    }
}
