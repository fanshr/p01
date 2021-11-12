package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 20:53
 * @date : Modified at 2021/11/6 20:53
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    private Long shopId;
    private Long ownerId;
    private Long shopCategoryId;
    private String shopName;
    private String shopDesc;
    private String shopAddr;
    private String phone;
    private String shopImg;
    private Double longitude;
    private Double latitude;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;
    private String advice;

    private List<ShopAuthMap> staffList;
    private Area area;
    private ShopCategory shopCategory;
    private ShopCategory parentCategory;
}
