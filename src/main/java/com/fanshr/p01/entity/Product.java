package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 21:07
 * @date : Modified at 2021/11/6 21:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    public static final Long serialVersionUID = 349433539553804024L;
    private Long productId;
    private String productName;
    private String productDesc;
    private String imgAddr;
    private String normalPrice;
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;
    private Integer point;

    private List<ProductImg> productImgList;
    private ProductCategory productCategory;
    private Shop shop;
}
