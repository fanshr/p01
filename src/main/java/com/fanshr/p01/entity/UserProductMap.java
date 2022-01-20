package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 10:26
 * @date : Modified at 2021/11/17 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductMap {
    private Long userProductId;
    private Long userId;
    private Long productId;
    private Long shopId;
    private String userName;
    private String productName;
    private Date createTime;
    private Integer point;
    private PersonInfo user;
    private Product product;
    private Shop shop;
}
