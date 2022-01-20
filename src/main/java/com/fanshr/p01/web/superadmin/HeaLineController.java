package com.fanshr.p01.web.superadmin;

import com.fanshr.p01.dto.HeadLineExecution;
import com.fanshr.p01.dto.Result;
import com.fanshr.p01.entity.GeneralConstant;
import com.fanshr.p01.entity.HeadLine;
import com.fanshr.p01.enums.HeadLineStateEnum;
import com.fanshr.p01.service.HeadLineService;
import com.fanshr.p01.util.ParamUtil;
import com.fanshr.p01.util.ResultUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 11:29
 * @date : Modified at 2021/11/16 11:29
 */
@Controller
@RequestMapping("/sa/")
public class HeaLineController {
    @Autowired
    private HeadLineService headLineService;

    @RequestMapping(value = "listHeadLines", method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> listHeadLines(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        List<HeadLine> list = new ArrayList<>();

        try {
            int enableStatus = ParamUtil.getInt(request, "enableStatus");
            HeadLine headLine = new HeadLine();
            if (enableStatus > -1) {
                headLine.setEnableStatus(enableStatus);
            }

            list = headLineService.getHeadLineList(headLine);
            modelMap.put(GeneralConstant.PAGE_SIZE, list);
            modelMap.put(GeneralConstant.TOTAL, list.size());
        } catch (Exception e) {

            return ResultUtil.error(e.toString());
        }

        return ResultUtil.success(modelMap);

    }


    @RequestMapping(value = "addHeadLine", method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> addheadLine(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        HeadLine headLine = null;
        String headLineStr = ParamUtil.getString(request, "headLineStr");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile thumbnail = (CommonsMultipartFile) multipartRequest.getFile(
                "headTitleManagementAdd_lineImg");

        try {
            headLine = mapper.readValue(headLineStr, HeadLine.class);

        } catch (IOException e) {
            return ResultUtil.error(e.toString());
        }

        if (headLine != null && thumbnail != null) {
            try {
                headLine.setLineName((headLine.getLineName() == null) ? null : URLDecoder.decode(headLine.getLineName(),
                        "UTF-8"));
                HeadLineExecution he = headLineService.addHeadLine(headLine, thumbnail);
                if (he.getState() == HeadLineStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(he.getStateInfo());
                }
            } catch (UnsupportedEncodingException e) {
                return ResultUtil.error(e.toString());
            }

        } else {
            return ResultUtil.error("请输入头条信息");
        }


    }

    @RequestMapping(value = "modifyHeadLine", method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> modifyHeadLine(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        HeadLine headLine = null;
        String headLineStr = ParamUtil.getString(request, "headLineStr");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile thumbnail = (CommonsMultipartFile) multipartRequest.getFile(
                "headTitleManagementEdit_lineImg");

        try {
            headLine = mapper.readValue(headLineStr, HeadLine.class);
        } catch (IOException e) {
            return ResultUtil.error(e.toString());
        }

        if (headLine != null && headLine.getLineId() != null) {
            try {
                headLine.setLineName((headLine.getLineName() == null) ? null :
                        URLDecoder.decode(headLine.getLineName(), "UTF-8"));
                HeadLineExecution he = headLineService.modifyHeadline(headLine, thumbnail);
                if (he.getState() == HeadLineStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(he.getStateInfo());
                }
            } catch (UnsupportedEncodingException e) {
                return ResultUtil.error(e.toString());
            }
        } else {
            return ResultUtil.error("请输入头条信息");
        }
    }

    @RequestMapping(value = "removeHeadLines", method = RequestMethod.POST)
    @ResponseBody
    private Result<Map<String, Object>> removeHeadLines(String headLineIdListStr) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Long.class);
        List<Long> headLineIdList = null;

        try {
            headLineIdList = mapper.readValue(headLineIdListStr, javaType);
        } catch (IOException e) {
            return ResultUtil.error(e.toString());
        }

        if (headLineIdList != null && headLineIdList.size() > 0) {
            try {
                HeadLineExecution he = headLineService.removeHeadLineList(headLineIdList);
                if (he.getState() == HeadLineStateEnum.SUCCESS.getState()) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(he.getStateInfo());
                }
            } catch (Exception e) {
                return ResultUtil.error(e.toString());
            }
        }else{
            return ResultUtil.error("请输入区域信息");
        }
    }
}
