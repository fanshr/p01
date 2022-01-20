package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/17 10:27
 * @date : Modified at 2021/11/17 10:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardMap {
    private Long userAwardId;
    private Long userId;
    private Long awardId;
    private Long shopId;
    private String userName;
    private String awardName;
    private Date expireTime;
    private Date createTime;
    private Integer usedStatus;
    private Integer point;
    private PersonInfo user;
    private Award award;
    private Shop shop;
}
