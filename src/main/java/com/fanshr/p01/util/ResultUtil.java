package com.lyj.assist.utils.web;


import com.lyj.assist.utils.bean.ErrorCode;
import com.lyj.assist.utils.bean.ResponseInfo;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2020/11/16 01:55
 * @date : Modified at 2020/11/16 01:55
 */
public class ResponseUtils {

    /**
     * 默认成功
     *
     * @return 消息
     */
    public static <T> ResponseInfo<T> success() {
        return success(null, null);
    }

    /**
     * 成功且带数据
     *
     * @param payload 负载
     * @param <T> 类型
     * @return 消息
     */
    public static <T> ResponseInfo<T> success(T payload) {
        return success(null, payload);
    }

    /**
     * 成功且带信息
     *
     * @param msg 消息
     * @param <T> 泛型
     * @return 消息
     */
    public static <T> ResponseInfo<T> success(String msg) {
        return success(msg, null);
    }

    /**
     * 成功且带信息带数据
     *
     * @param msg 消息
     * @param payload 负载
     * @param <T> 泛型
     * @return 消息
     */
    public static <T> ResponseInfo<T> success(String msg, T payload) {
        ResponseInfo<T> resultInfo = new ResponseInfo<>();
        resultInfo.setStatus(true);
        resultInfo.setErrorCode(ErrorCode.SUCCESS.getCode());
        msg = msg == null ? ErrorCode.SUCCESS.getMsg() : msg;
        resultInfo.setError(msg);
        resultInfo.setPayload(payload);
        return resultInfo;
    }

    /**
     * 默认失败
     *
     * @param <T> 泛型
     * @return 消息
     */
    public static <T> ResponseInfo<T> error() {
        return error(ErrorCode.ERROR);
    }

    /**
     * 失败且返回状态码
     *
     * @param code 错误码
     * @param <T> 泛型
     * @return 消息
     */
    public static <T> ResponseInfo<T> error(ErrorCode code) {
        return error(code, null, null);
    }

    /**
     * 失败且带详细错误信息
     *
     * @param code 错误码
     * @param msg 消息
     * @return 消息
     */
    public static <T> ResponseInfo<T> error(ErrorCode code, String msg) {
        return error(code, msg, null);
    }

    /**
     * 失败且带数据
     *
     * @param payload 负载
     * @param <T> 泛型
     * @return 消息
     */
    public static <T> ResponseInfo<T> error(ErrorCode code, T payload) {
        return error(code, null, payload);
    }

    /**
     * 失败且带数据
     *
     * @param code 状态码
     * @param msg 消息
     * @param payload 负载
     * @param <T> 泛型
     * @return 消息
     */
    public static <T> ResponseInfo<T> error(ErrorCode code, String msg, T payload) {
        ResponseInfo<T> resultInfo = new ResponseInfo<>();
        resultInfo.setStatus(false);
        resultInfo.setErrorCode(code.getCode());
        msg = msg == null ? code.getMsg() : msg;
        resultInfo.setError(msg);
        resultInfo.setPayload(payload);
        return resultInfo;
    }

}
