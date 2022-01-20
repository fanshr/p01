package com.fanshr.p01.web.frontend;

import com.fanshr.p01.dto.AwardExecution;
import com.fanshr.p01.dto.Result;
import com.fanshr.p01.entity.Award;
import com.fanshr.p01.service.AwardService;
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
@RequestMapping("/frontend")
public class ShopAwardController {
    @Autowired
    private AwardService awardService;

    @RequestMapping(value = "/getAwardById", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> getAwardbyId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long awardId = ParamUtil.getLong(request, "awardId");
        if (awardId > -1) {
            Award award = awardService.getAwardById(awardId);
            modelMap.put("award", award);
            return ResultUtil.success(modelMap);
        } else {
            return ResultUtil.error("empty awardId");
        }

    }


    @RequestMapping(value = "/listAwardsByShop", method = RequestMethod.GET)
    @ResponseBody
    private Result<Map<String, Object>> listAwardsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = ParamUtil.getInt(request, "pageIndex");
        int pageSize = ParamUtil.getInt(request, "pageSize");
        long shopId = ParamUtil.getLong(request, "shopId");
        if (pageIndex > -1 && pageSize > -1 && shopId > -1) {
            String awardName = ParamUtil.getString(request, "awardName");
            Award awardCondition = compactAwardCondition4Search(shopId,awardName);
            AwardExecution ae = awardService.getAwardList(awardCondition, pageIndex, pageSize);
            modelMap.put("awardList", ae.getAwardList());
            modelMap.put("count", ae.getCount());
            return ResultUtil.success(modelMap);
        }else{
           return ResultUtil.error("empty pageSize or pageIndex or shopId");
        }


    }

    private Award compactAwardCondition4Search(long shopId, String awardName) {
        Award awardCondition = new Award();
        awardCondition.setShopId(shopId);
        if (awardName != null) {
            awardCondition.setAwardName(awardName);
        }
        return awardCondition;
    }

}
