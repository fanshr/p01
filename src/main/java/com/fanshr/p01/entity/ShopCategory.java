package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 20:51
 * @date : Modified at 2021/11/6 20:51
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategory {
    private Long shopCategoryId;
    private String shopCategoryName;
    private String shopCategoryDesc;
    private String shopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Long parentId;
}
