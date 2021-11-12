package com.fanshr.p01.dao;

import com.fanshr.p01.BaseTest;
import com.fanshr.p01.entity.Area;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/11 16:03
 * @date : Modified at 2021/11/11 16:03
 */

public class ShopDaoTest extends BaseTest {

    @Autowired
    ShopDao shopDao;

    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        shop.setOwnerId(8L);
        Area area = new Area();
        area.setAreaId(4L);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(11L);
        shop.setShopName("mytest1");
        shop.setShopDesc("mytest1");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
        shop.setShopImg("test1");
        shop.setLongitude(1D);
        shop.setLatitude(1D);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setShopCategory(sc);
        int effectNum = shopDao.insertShop(shop);
        Assert.assertEquals(1,effectNum);

    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(31L);
        shop.setOwnerId(8L);
        Area area = new Area();
        area.setAreaId(4L);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(11L);
        shop.setShopName("mytest2");
        shop.setShopDesc("mytest2");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
        shop.setShopImg("test1");
        shop.setLongitude(1D);
        shop.setLatitude(1D);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("成功");
        shop.setArea(area);
        shop.setShopCategory(sc);

        int effectNum = shopDao.updateShop(shop);
        Assert.assertEquals(1,effectNum);

    }
}
