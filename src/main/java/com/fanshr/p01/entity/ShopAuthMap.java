package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 20:57
 * @date : Modified at 2021/11/6 20:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopAuthMap {
    private Long shopAuthId;
    private Long employeeId;
    private Long shopId;
    private String name;
    private String title;
    private Integer titleFlag;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
    private PersonInfo emplyee;
    private Shop shop;
}
