package com.fanshr.p01.dto;

import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.ShopCategoryStateEnum;
import com.fanshr.p01.service.ShopCategoryService;
import lombok.Data;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/15 11:22
 * @date : Modified at 2021/11/15 11:22
 */

@Data
public class ShopCategoryExecution {

    private int state;
    private String stateInfo;
    private ShopCategory shopCategory;
    private List<ShopCategory> shopCategoryList;
    private int count;

    private ShopCategoryExecution() {

    }

    public ShopCategoryExecution(ShopCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public ShopCategoryExecution(ShopCategoryStateEnum stateEnum,ShopCategory shopCategory){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopCategory = shopCategory;
    }

    public ShopCategoryExecution(ShopCategoryStateEnum stateEnum, List<ShopCategory> shopCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopCategoryList = shopCategoryList;
        this.count = shopCategoryList.size();
    }
}
