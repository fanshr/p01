package com.fanshr.p01.service;

import com.fanshr.p01.dto.ProductExecution;
import com.fanshr.p01.entity.Product;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 10:56
 * @date : Modified at 2021/11/17 10:56
 */
public interface ProductService {
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    Product getProductById(long productId);

    ProductExecution addProduct(Product product, CommonsMultipartFile thumnail, List<CommonsMultipartFile> productImgs);

    ProductExecution modifyProduct(Product product, CommonsMultipartFile thumnail,
                                   List<CommonsMultipartFile> productImgs);
}