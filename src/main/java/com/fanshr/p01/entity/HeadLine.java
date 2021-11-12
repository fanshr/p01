package com.fanshr.p01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 21:03
 * @date : Modified at 2021/11/6 21:03
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadLine {
    private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
}
