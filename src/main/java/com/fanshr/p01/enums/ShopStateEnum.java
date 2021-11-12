package com.fanshr.p01.enums;

/**
 *
 * 状态枚举常量
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/12 09:54
 * @date : Modified at 2021/11/12 09:54
 */
public enum ShopStateEnum {


    CHECK(0,"审核中"),
    OFFLINEES(-1,"非法商铺"),
    SUCCESS(1,"操作成功"),
    PASS(2,"通过认证"),
    INNER_ERROR(-1001,"操作失败"),
    NULL_SHOPID(-1002,"shopId为空"),
    NULL_SHOP_INFO(-1003,"传入信息为空");

    private int state;
    private String stateInfo;

    private ShopStateEnum(int state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ShopStateEnum stateOf(int index){
        for (ShopStateEnum state : values()) {
            if (state.getState() == index){
                return state;
            }
        }

        return null;
    }
}
