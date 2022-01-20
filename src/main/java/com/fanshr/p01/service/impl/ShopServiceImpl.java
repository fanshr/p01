package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.ShopCategoryDao;
import com.fanshr.p01.dao.ShopDao;
import com.fanshr.p01.dto.ShopExecution;
import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.ShopStateEnum;
import com.fanshr.p01.service.ShopService;
import com.fanshr.p01.util.ImageUtil;
import com.fanshr.p01.util.PageCalculator;
import com.fanshr.p01.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/11 17:08
 * @date : Modified at 2021/11/11 17:08
 */

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ShopCategoryDao shopCategoryDao;


    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);

        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {

            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return se;
    }

    @Override
    public ShopExecution getByEmployeeId(long employeedId) {
        List<Shop> shopList = shopDao.queryByEmployeeId(employeedId);
        ShopExecution se = new ShopExecution();
        se.setShopList(shopList);
        return se;
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    /**
     * 注解控制事务
     * 1. 团队内部达成一致，明确标注事务方法的编程风格
     * 2. 保证事务方法的执行时间尽可能短，不要穿插其他网路操作，RPC/HTTP请求应该考虑玻璃到事务方法之外
     * 3. 不是所有的方法都需要事务，比如只读操作，或者只有一条操作需要修改时
     *
     * @param shop    店铺基本信息
     * @param shopImg 店铺logo
     * @return
     */
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }


        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            if (shop.getShopCategory() != null) {
                Long shopCategoryId = shop.getShopCategory().getShopCategoryId();
                ShopCategory sc = shopCategoryDao.queryShopCategoryById(shopCategoryId);
                ShopCategory parentCategory = new ShopCategory();
                parentCategory.setShopCategoryId(sc.getParentId());
                shop.setParentCategory(parentCategory);

            }

            int effectedRows = shopDao.insertShop(shop);
            if (effectedRows <= 0) {
                throw new RuntimeException("店铺创建失败");
            } else {
                try {
                    if (shopImg != null) {
                        addShopImg(shop, shopImg);
                        effectedRows = shopDao.updateShop(shop);

                        if (effectedRows <= 0) {
                            throw new RuntimeException("追加图片地址失败");
                        }
                    }
                } catch (RuntimeException e) {
                    throw new RuntimeException("addShopImg error:" + e.getMessage());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("insertShop error:" + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK, shop);

    }

    @Transactional
    @Override
    public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) {

        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        } else {
            try {
                if (shopImg != null) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        FileUtil.deleteFile(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImg);
                }

                shop.setLastEditTime(new Date());
                int effectedRows = shopDao.updateShop(shop);
                if (effectedRows < 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {

                throw new RuntimeException("modifyShop error:" + e.getMessage());
            }
        }
    }

    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {

        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImgAddr);
    }
}
