package com.fanshr.p01.web.frontend;

import com.fanshr.p01.dto.Result;
import com.fanshr.p01.entity.HeadLine;
import com.fanshr.p01.entity.ShopCategory;
import com.fanshr.p01.enums.HeadLineStateEnum;
import com.fanshr.p01.enums.ShopCategoryStateEnum;
import com.fanshr.p01.service.HeadLineService;
import com.fanshr.p01.service.ShopCategoryService;
import com.fanshr.p01.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:13
 * @date : Modified at 2021/11/23 10:13
 */
@Controller
@RequestMapping("/frontend/")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

    @RequestMapping(value = "/listMainPageInfo", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> list1stShopCategory(){
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();

        try {
            shopCategoryList = shopCategoryService.getFirstLevelShopCategoryList();
            modelMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {

            return ResultUtil.error(ShopCategoryStateEnum.INNER_ERROR.getStateInfo());
        }

        List<HeadLine> headLineList = new ArrayList<>();

        try {
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList", headLineList);
        } catch (Exception e) {

            return ResultUtil.error(HeadLineStateEnum.INNER_ERROR.getStateInfo());
        }

        return ResultUtil.success(modelMap);


    }



}
