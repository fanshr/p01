package com.fanshr.p01.web.frontend;

import com.fanshr.p01.dto.Result;
import com.fanshr.p01.dto.UserShopMapExecution;
import com.fanshr.p01.entity.UserShopMap;
import com.fanshr.p01.service.UserShopMapService;
import com.fanshr.p01.util.ParamUtil;
import com.fanshr.p01.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/12/27 09:19
 * @date : Modified at 2021/12/27 09:19
 */
@Controller
@RequestMapping("/frontend/")
public class MyShopPointController {

    @Autowired
    private UserShopMapService userShopMapService;

    @RequestMapping(value = "/listUserShopMapsByCustomer", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> listUserShopMapsByCustomer(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        int pageIndex = ParamUtil.getInt(request, "pageIndex");
        int pageSize = ParamUtil.getInt(request, "pageSize");
        Long userId = 1L;
        if (pageIndex > -1 && pageSize > -1 && userId != null) {
            UserShopMap userShopMapCondition = new UserShopMap();
            userShopMapCondition.setUserId(userId);
            long shopId = ParamUtil.getLong(request, "shopId");
            if (shopId > -1) {
                userShopMapCondition.setShopId(shopId);

            }

            UserShopMapExecution ue = userShopMapService.listUserShopMap(userShopMapCondition, pageIndex, pageSize);
            modelMap.put("userShopMapList", ue.getUserShopMapList());
            modelMap.put("count",ue.getCount());
            return ResultUtil.success(modelMap);
        }else{
            return ResultUtil.error("empty pageSize or pageIndex or shopId");
        }
    }


}
