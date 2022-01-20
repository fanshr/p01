package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.ShopAuthMapDao;
import com.fanshr.p01.dto.ShopAuthMapExecution;
import com.fanshr.p01.entity.ShopAuthMap;
import com.fanshr.p01.enums.ShopAuthMapStateEnum;
import com.fanshr.p01.service.ShopAuthMapService;
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
public class ShopAuthMapServiceImpl implements ShopAuthMapService {
    @Autowired
    private ShopAuthMapDao shopAuthMapDao;
    @Override
    public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
        if (shopId != null && pageIndex != null && pageSize != null) {
            int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId, rowIndex, pageSize);
            int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
            ShopAuthMapExecution se = new ShopAuthMapExecution();
            se.setShopAuthMapList(shopAuthMapList);
            se.setCount(count);
            return se;
        }
        return null;
    }

    @Override
    @Transactional
    public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException {
        if (shopAuthMap == null || shopAuthMap.getShopId() == null || shopAuthMap.getEmployeeId() == null) {
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
        }
        shopAuthMap.setCreateTime(new Date());
        shopAuthMap.setLastEditTime(new Date());
        shopAuthMap.setEnableStatus(1);

        try {
            int effectedRows = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
            if (effectedRows <= 0) {
                throw new RuntimeException("添加授权失败");
            }
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
        } catch (Exception e) {
            throw new RuntimeException("添加授权失败："+e.toString());
        }
    }

    @Override
    @Transactional
    public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException {
        if (shopAuthMap == null || shopAuthMap.getShopAuthId() == null) {
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
        }

        try {
            shopAuthMap.setLastEditTime(new Date());
            int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
            if (effectedNum <= 0) {
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
            }else{
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
            }
        } catch (Exception e) {
            throw new RuntimeException("updateShopByOwner error: " + e.toString());
        }
    }

    @Override
    public ShopAuthMapExecution removeShopAuthMap(Long shopAuthMapId) throws RuntimeException {
        // TODO: 删除功能
        return null;
    }

    @Override
    public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
        return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
    }
}
