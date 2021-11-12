package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 21:06
 * @date : Modified at 2021/11/6 21:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImg {
    private Long productImgId;
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
}
