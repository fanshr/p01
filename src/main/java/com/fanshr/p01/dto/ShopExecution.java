package com.fanshr.p01.dto;

import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.enums.ShopStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/12 09:52
 * @date : Modified at 2021/11/12 09:52
 */

@Data
public class ShopExecution {
    /**
     * 操作状态
     */
    private int state;
    /**
     * 状态描述
     */
    private String stateInfo;
    /**
     * 店铺数量
     */
    private int count;
    /**
     * 操作的shop，用于增删改操作
     */
    private Shop shop;
    /**
     * 获取的shop列表，用于查询
     */
    private List<Shop> shopList;

    public ShopExecution() {
    }

    /**
     * 失败的构造器
     * @param stateEnum
     */
    public ShopExecution(ShopStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 成功的构造器
     * @param stateEnum
     * @param shop
     */
    public ShopExecution(ShopStateEnum stateEnum,Shop shop){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    /**
     * 成功的构造器
     * @param stateEnum
     * @param shopList
     */
    public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }




}
