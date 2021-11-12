package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 20:49
 * @date : Modified at 2021/11/6 20:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WechatAuth {
    private Long wechatAuthId;
    private Long userId;
    private String openId;
    private String createTime;
    private PersonInfo personInfo;
}
