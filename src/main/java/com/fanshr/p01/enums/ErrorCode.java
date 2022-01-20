package com.fanshr.p01.enums;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 14:11
 * @date : Modified at 2021/11/16 14:11
 */

public enum ErrorCode {

    /**
     * -1***，身份及权限类型异常
     * -2***，
     * -3***，
     * -4***，web层参数校验异常
     * -5***，网络链接类型异常
     * -6***，数据库操作异常
     * -7***，
     * -8***,
     * -9***,
     *
     */
    INNER_ERROR(-6000, "数据库操作失败"),

    UNSUPPORTED_METHOD(-5002,"请求类型不支持"),
    NO_SERVICE(-5001, "网络异常, 服务器熔断"),
    UNSPECIFIED(-5000, "网络异常，请稍后再试"),

    INVALID_PARAM(-4008,"参数格式异常"),
    MISTYPE_PARAM(-4007,"参数类型异常"),
    MISSING_PARAM(-4006,"参数缺失异常"),
    ROWS_IS_NULL(-4005,"页数不能为空"),
    INDEX_IS_NULL(-4004,"页码不能为空"),
    SEARCH_IS_NULL(-4003,"搜索条件不能为空"),
    ID_IS_NULL(-4002,"ID不能为空"),
    EMPTY(-4001, "参数为空字符串"),
    DATA_IS_NULL(-4000,"参数为空"),

    DELETE_REFUSED(-1003,"不允许删除"),
    USER_NOT_EXIST(-1000,"用户不存在"),
    ERROR(-1, "未知错误"),
    SUCCESS(0, "操作成功");



    private int state;
    private String msg;


    private ErrorCode(int state, String stateInfo) {
        this.state = state;
        this.msg = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public static ErrorCode stateOf(int index) {
        for (ErrorCode state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
