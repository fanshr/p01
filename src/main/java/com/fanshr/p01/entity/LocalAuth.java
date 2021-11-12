package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 20:50
 * @date : Modified at 2021/11/6 20:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalAuth {
    private Long localAuthId;
    private String userName;
    private String password;
    private Long userId;
    private Date createTime;
    private Date lastEditTime;
    private PersonInfo personInfo;
}
