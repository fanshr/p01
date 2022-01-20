package com.fanshr.p01.dao;

import com.fanshr.p01.BaseTest;
import com.fanshr.p01.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/15 10:58
 * @date : Modified at 2021/11/15 10:58
 */
public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
        ShopCategory shopCategory = new ShopCategory();
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategory);
        System.out.println(shopCategoryList);

        shopCategory.setParentId(11L);
        shopCategoryList = shopCategoryDao.queryShopCategory(shopCategory);
        System.out.println(shopCategoryList);

        shopCategory.setParentId(null);
        shopCategory.setShopCategoryId(11L);
        shopCategoryList = shopCategoryDao.queryShopCategory(shopCategory);
        System.out.println(shopCategoryList);


    }

    @Test
    public void testQueryShopCategoryById() {

        ShopCategory shopCategory = shopCategoryDao.queryShopCategoryById(11L);
        System.out.println(shopCategory);

    }

    @Test
    public void testInsertShopCategory() {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryName("测试分类1115");
        shopCategory.setShopCategoryDesc("第一条测试");
        shopCategory.setPriority(18);
        shopCategory.setCreateTime(new Date());
        shopCategory.setLastEditTime(new Date());
        shopCategory.setParentId(11L);

        int effectedRow = shopCategoryDao.insertShopCategory(shopCategory);
        Assert.assertEquals(1, effectedRow);
    }

    @Test
    public void testUpdateShopCategory() {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(33L);
        shopCategory.setShopCategoryName("测试分类1115");
        shopCategory.setShopCategoryDesc("第一条更新测试");
        shopCategory.setPriority(20);
        shopCategory.setLastEditTime(new Date());
        shopCategory.setParentId(11L);
        int effectedRow = shopCategoryDao.updateShopCategory(shopCategory);
        Assert.assertEquals(1, effectedRow);
    }

    @Test
    public void testDeleteShopCategory() {

        int effectedRow = shopCategoryDao.deleteShopCategory(33L);
        Assert.assertEquals(1, effectedRow);

    }

    @Test
    public void testBatchDeleteShopCategory() {
        List<Long> idList = new ArrayList<>();
        idList.add(33L);
        idList.add(34L);
        idList.add(35L);
        idList.add(36L);
        int effectedRow = shopCategoryDao.batchDeleteShopCategory(idList);
        Assert.assertEquals(1,effectedRow);
    }
}
