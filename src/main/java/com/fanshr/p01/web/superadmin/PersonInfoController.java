package com.fanshr.p01.web.superadmin;

import com.fanshr.p01.dto.PersonInfoExecution;
import com.fanshr.p01.dto.Result;
import com.fanshr.p01.entity.GeneralConstant;
import com.fanshr.p01.entity.PersonInfo;
import com.fanshr.p01.enums.PersonInfoStateEnum;
import com.fanshr.p01.service.PersonInfoService;
import com.fanshr.p01.util.ParamUtil;
import com.fanshr.p01.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 11:29
 * @date : Modified at 2021/11/16 11:29
 */
@Controller
@RequestMapping("/sa/")
public class PersonInfoController {
    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "listPersonInfos", method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> listPersonInfos(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        PersonInfoExecution pe = null;
        int pageIndex = ParamUtil.getInt(request, GeneralConstant.PAGE_NO);
        int pageSize = ParamUtil.getInt(request, GeneralConstant.PAGE_SIZE);
        if (pageIndex > 0 && pageSize > 0) {

            try {
                PersonInfo personInfo = new PersonInfo();
                int enableStatus = ParamUtil.getInt(request, "enableStatus");
                if (enableStatus > -1) {
                    personInfo.setEnableStatus(enableStatus);
                }

                String name = ParamUtil.getString(request, "name");
                if (name != null) {
                    personInfo.setName(URLDecoder.decode(name, "UTF-8"));
                }
                pe = personInfoService.getPersonInfoList(personInfo, pageIndex,
                        pageSize);
            } catch (UnsupportedEncodingException e) {
                return ResultUtil.error(e.toString());
            }

            if (pe.getPersonInfoList() != null) {
                modelMap.put(GeneralConstant.PAGE_SIZE, pe.getPersonInfoList());
                modelMap.put(GeneralConstant.TOTAL, pe.getCount());

            }else{
                modelMap.put(GeneralConstant.PAGE_SIZE, pe.getPersonInfoList());
                modelMap.put(GeneralConstant.TOTAL, 0);
            }
            return ResultUtil.success(modelMap);
        }else{
            return ResultUtil.error("空的查询信息");
        }
    }

    @RequestMapping(value = "modifyPersonInfo", method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> modifyPersonInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long userId = ParamUtil.getLong(request, "userId");
        int enableStatus = ParamUtil.getInt(request, "enableStatus");
        if (userId > 0 && enableStatus > 0) {
            try {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setUserId(userId);
                personInfo.setEnableStatus(enableStatus);
                PersonInfoExecution pe = personInfoService.modifyPersonInfo(personInfo);
                if (pe.getState() == PersonInfoStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                }else{
                    return ResultUtil.error(pe.getStateInfo());
                }
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }
        }else{
            return ResultUtil.error("请输入需要修改的信息");
        }
    }
}
