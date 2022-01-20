package com.fanshr.p01.web.frontend;

import com.fanshr.p01.dto.Result;
import com.fanshr.p01.dto.UserProductMapExecution;
import com.fanshr.p01.entity.PersonInfo;
import com.fanshr.p01.entity.UserProductMap;
import com.fanshr.p01.service.UserProductMapService;
import com.fanshr.p01.util.ParamUtil;
import com.fanshr.p01.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:14
 * @date : Modified at 2021/11/23 10:14
 */
@Controller
@RequestMapping("/fronted/")
public class MyProductController {

    @Autowired
    private UserProductMapService userProductMapService;



    @RequestMapping(value = "/listUserProductMapsByCustomer", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> listUserProductMapsByCustomer(
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = ParamUtil.getInt(request, "pageIndex");
        int pageSize = ParamUtil.getInt(request, "pageSize");
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (pageIndex > -1 && pageSize > -1 && user != null && user.getUserId() != -1) {
            UserProductMap userProductMapCondition = new UserProductMap();
            userProductMapCondition.setUserId(user.getUserId());
            long shopId = ParamUtil.getLong(request, "shopId");
            if (shopId > -1) {
                userProductMapCondition.setShopId(shopId);
            }

            String productName = ParamUtil.getString(request, "productName");
            if (productName != null) {
                userProductMapCondition.setProductName(productName);
            }

            UserProductMapExecution ue =
                    userProductMapService.listUserProductMap(userProductMapCondition, pageIndex, pageSize);
            modelMap.put("userProductMapList", ue.getUserProductMapList());
            modelMap.put("count", ue.getCount());
            return ResultUtil.success(modelMap);
        }else{
            return ResultUtil.error("empty pageSize or pageIndex or shopId");
        }


    }
}