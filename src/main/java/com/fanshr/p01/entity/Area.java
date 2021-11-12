package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 20:42
 * @date : Modified at 2021/11/6 20:42
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    private Long areaId;
    private String areaName;
    private String areaDesc;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;




}
