package com.fanshr.p01.dao;

import com.fanshr.p01.entity.ProductImg;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 22:58
 * @date : Modified at 2021/11/16 22:58
 */
public interface ProductImgDao {
    List<ProductImg> queryProductImgList(long productId);

    int batchInsertProductImg(List<ProductImg> productImgList);

    int deleteProductImgByProductId(long productId);
}
