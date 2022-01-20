package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.ProductCategoryDao;
import com.fanshr.p01.dao.ProductDao;
import com.fanshr.p01.dto.ProductCategoryExecution;
import com.fanshr.p01.entity.ProductCategory;
import com.fanshr.p01.enums.ProductCategoryStateEnum;
import com.fanshr.p01.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 10:51
 * @date : Modified at 2021/11/17 10:51
 */

@Service
public class ProductCategoryImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;
    @Override
    public List<ProductCategory> getByShopId(long shopId) {
        return productCategoryDao.queryByShopId(shopId);
    }

    @Transactional
    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) {
        try {
            int effectedRows = productCategoryDao.batchInsertProduct(productCategoryList);
            if(effectedRows <= 0){
                throw new RuntimeException("店铺类别新增失败");

            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new RuntimeException("batchAddProductCategory error:" + e.toString());
        }
    }

    @Transactional
    @Override
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) {
        try {
            int effectedRows = productDao.updateProductCategoryToNULL(productCategoryId);
            if (effectedRows<0){
                throw new RuntimeException("关联商品类别更新失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("deleteProductCategory error:" + e.toString());
        }
        try {
            int effectedRows =productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if (effectedRows<=0){
                throw new RuntimeException("商品类型删除失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new RuntimeException("deleteProductCategory error:" + e.getMessage());
        }


    }
}
