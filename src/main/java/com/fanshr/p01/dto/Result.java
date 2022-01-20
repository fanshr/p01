package com.fanshr.p01.dto;

import lombok.Data;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 14:03
 * @date : Modified at 2021/11/16 14:03
 */
@Data
public class Result<T> {
    private boolean success;
    private T data;

    private int errorCode;
    private String errMsg;

}
