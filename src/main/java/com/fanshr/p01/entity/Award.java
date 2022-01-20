package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 10:16
 * @date : Modified at 2021/11/17 10:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Award {
    private Long awardId;
    private String awardName;
    private String awardDesc;
    private String awardImg;
    private Integer point;
    private Integer priority;
    private Date createTime;
    private Date expireTime;
    private Date lastEditTime;
    private Integer enableStatus;
    private Long shopId;

}
