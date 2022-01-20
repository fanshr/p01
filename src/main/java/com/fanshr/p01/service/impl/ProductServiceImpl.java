package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.ProductDao;
import com.fanshr.p01.dao.ProductImgDao;
import com.fanshr.p01.dto.ProductExecution;
import com.fanshr.p01.entity.Product;
import com.fanshr.p01.entity.ProductImg;
import com.fanshr.p01.enums.ProductStateEnum;
import com.fanshr.p01.service.ProductService;
import com.fanshr.p01.util.FileUtil;
import com.fanshr.p01.util.ImageUtil;
import com.fanshr.p01.util.PageCalculator;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 11:00
 * @date : Modified at 2021/11/17 11:00
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductByProductId(productId);
    }

    @Transactional
    @Override
    public ProductExecution addProduct(Product product, CommonsMultipartFile thumnail, List<CommonsMultipartFile> productImgs) {
        if (product!=null && product.getShop()!=null &&product.getShop().getShopId()!=null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumnail != null) {
                addThumbnail(product, thumnail);
            }
            try {
                int effectedRows = productDao.insertProduct(product);
                if (effectedRows <= 0) {
                    throw new RuntimeException("创建商品失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("创建商品失败：" + e.toString());
            }

            if (productImgs != null && productImgs.size() > 0) {
                addProductImgs(product, productImgs);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    private void addProductImgs(Product product, List<CommonsMultipartFile> productImgs) {
        String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
        List<String> imgAddrList = ImageUtil.generateNormalImgs(productImgs,dest);

        if (imgAddrList!=null && imgAddrList.size()>0){
            List<ProductImg> productImgList = new ArrayList<>();
            for (String imgAddr : imgAddrList) {
                ProductImg productImg = new ProductImg();
                productImg.setProductId(product.getProductId());
                productImg.setImgAddr(imgAddr);
                productImg.setCreateTime(new Date());
                productImgList.add(productImg);

            }

            try {
                int effectedRows = productImgDao.batchInsertProductImg(productImgList);
                if (effectedRows <= 0) {
                    throw new RuntimeException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("创建商品详情图片失败：" + e.toString());

            }
        }
    }

    private void addThumbnail(Product product, CommonsMultipartFile thumnail) {

        String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    @Transactional
    @Override
    public ProductExecution modifyProduct(Product product, CommonsMultipartFile thumnail,
                                          List<CommonsMultipartFile> productImgs) {

        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            if (thumnail != null) {
                Product tempProduct = productDao.queryProductByProductId(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    FileUtil.deleteFile(tempProduct.getImgAddr());

                }
                addThumbnail(product,thumnail);
            }

            if (productImgs != null && productImgs.size() > 0) {
                deleteProductImgs(product.getProductId());
                addProductImgs(product,productImgs);
            }

            try {
                int effectedRows = productDao.updateProduct(product);
                if (effectedRows<=0){
                    throw new RuntimeException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (RuntimeException e) {
                throw new RuntimeException("更新商品信息失败："+e.toString());
            }
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }


    }

    private void deleteProductImgs(Long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg : productImgList) {
            FileUtil.deleteFile(productImg.getImgAddr());

        }
        productImgDao.deleteProductImgByProductId(productId);

    }
}
