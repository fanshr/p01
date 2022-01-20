package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 10:12
 * @date : Modified at 2021/11/17 10:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShopMap {
    private Long userShopId;
    private Long userId;
    private Long shopId;
    private String userName;
    private String shopName;
    private Date createTime;
    private Integer point;
    private PersonInfo user;
    private Product product;
    private Shop shop;
}
