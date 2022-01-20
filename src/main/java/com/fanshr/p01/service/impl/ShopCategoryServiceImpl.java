package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.ShopCategoryDao;
import com.fanshr.p01.dto.ShopCategoryExecution;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.ShopCategoryStateEnum;
import com.fanshr.p01.service.ShopCategoryService;
import com.fanshr.p01.util.ImageUtil;
import com.fanshr.p01.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/15 11:13
 * @date : Modified at 2021/11/15 11:13
 */

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    public static final String SCLISTKEY = "shopCategoryList";

    @Override
    public List<ShopCategory> getFirstLevelShopCategoryList() {
        List<ShopCategory> shopCategoryList = null;
        // TODO: 缓存处理以及JSON转换
        ObjectMapper mapper = new ObjectMapper();
        ShopCategory shopCategoryCondition = new ShopCategory();
        shopCategoryCondition.setShopCategoryId(-1L);
        shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);


        return shopCategoryList;
    }

    @Override
    public List<ShopCategory> getShopCategoryList(Long parentId) {
        ShopCategory shopCategoryCondition = new ShopCategory();
        shopCategoryCondition.setParentId(parentId);
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);

        return shopCategoryList;
    }

    @Override
    public List<ShopCategory> getAllSecondLevelShopCategory() {
        ShopCategory shopCategoryCondition = new ShopCategory();
        shopCategoryCondition.setShopCategoryDesc("ALLSECOND");
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);

        return shopCategoryList;
    }

    @Override
    public ShopCategory getShopCategoryById(Long shopCategoryId) {
        // TODO: 存在缓存的情况下，可以考虑借助从内存中查询全量的数据，然后进行遍历比对

        ShopCategory shopCategory = shopCategoryDao.queryShopCategoryById(shopCategoryId);


        return shopCategory;
    }

    @Transactional
    @Override
    public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, CommonsMultipartFile thumbnail) {
        if (shopCategory != null) {
            shopCategory.setCreateTime(new Date());
            shopCategory.setLastEditTime(new Date());
            if (thumbnail != null) {
                addThumbnail(shopCategory, thumbnail);
            }

            try {
                int effectedRows = shopCategoryDao.insertShopCategory(shopCategory);
                if (effectedRows > 0) {
                    return new ShopCategoryExecution(
                            ShopCategoryStateEnum.SUCCESS, shopCategory);
                } else {
                    return new ShopCategoryExecution(
                            ShopCategoryStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("添加店铺类别信息失败:" + e.toString());

            }
        } else {
            return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
        }
    }

    private void addThumbnail(ShopCategory shopCategory, CommonsMultipartFile thumbnail) {
        String dest = FileUtil.getShopCategoryImgPath();
        String thumbnailAddr = ImageUtil.generateNomalImg(thumbnail, dest);
        shopCategory.setShopCategoryImg(thumbnailAddr);
    }

    @Transactional
    @Override
    public ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, CommonsMultipartFile thumbnail,
                                                    boolean thumbnailChange) {
        if (shopCategory.getShopCategoryId() != null && shopCategory.getShopCategoryId() > 0) {
            shopCategory.setLastEditTime(new Date());
            if (thumbnail != null && thumbnailChange == true) {
                ShopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());

                if (tempShopCategory.getShopCategoryImg() != null) {
                    FileUtil.deleteFile(tempShopCategory.getShopCategoryImg());

                }
                addThumbnail(shopCategory, thumbnail);
            }

            try {
                int effectedRows = shopCategoryDao.updateShopCategory(shopCategory);
                if (effectedRows > 0) {
                    return new ShopCategoryExecution(
                            ShopCategoryStateEnum.SUCCESS, shopCategory);
                } else {
                    return new ShopCategoryExecution(
                            ShopCategoryStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("更新店铺类别信息失败:" + e.toString());
            }
        } else {
            return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);

        }
    }

    @Transactional
    @Override
    public ShopCategoryExecution removeShopCategory(long shopCategoryId) {
        if (shopCategoryId > 0) {
            try {
                ShopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategoryId);
                if (tempShopCategory.getShopCategoryImg() != null) {
                    FileUtil.deleteFile(tempShopCategory.getShopCategoryImg());
                }
                int effectedRows = shopCategoryDao.deleteShopCategory(shopCategoryId);
                if (effectedRows > 0) {
                    return new ShopCategoryExecution(
                            ShopCategoryStateEnum.SUCCESS);
                } else {
                    return new ShopCategoryExecution(
                            ShopCategoryStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("删除店铺类别信息失败:" + e.toString());

            }

        } else {
            return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);

        }
    }

    @Transactional
    @Override
    public ShopCategoryExecution removeShopCategoryList(List<Long> shopCategoryIdList) {
        if (shopCategoryIdList != null && shopCategoryIdList.size() > 0) {

            try {

                List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategoryByIds(shopCategoryIdList);
                for (ShopCategory shopCategory : shopCategoryList) {
                    if (shopCategory.getShopCategoryImg() != null) {
                        FileUtil.deleteFile(shopCategory.getShopCategoryImg());
                    }
                }

                int effectedRows = shopCategoryDao.batchDeleteShopCategory(shopCategoryIdList);
                if (effectedRows > 0) {

                    return new ShopCategoryExecution(
                            ShopCategoryStateEnum.SUCCESS);
                } else {
                    return new ShopCategoryExecution(
                            ShopCategoryStateEnum.INNER_ERROR);
                }

            } catch (Exception e) {
                throw new RuntimeException("删除店铺类别信息失败:" + e.toString());


            }
        } else {
            return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
        }
    }
}
