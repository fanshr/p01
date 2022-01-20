package com.fanshr.p01.web.superadmin;

import com.fanshr.p01.dto.AreaExecution;
import com.fanshr.p01.entity.Area;
import com.fanshr.p01.entity.GeneralConstant;
import com.fanshr.p01.enums.AreaStateEnum;
import com.fanshr.p01.service.AreaService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 22:40
 * @date : Modified at 2021/11/6 22:40
 */

@Controller
@RequestMapping("/sa/")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "listAreas", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listAreas() {
        Map<String, Object> modelMap = new HashMap<>();
        List<Area> list = new ArrayList<>();
        try {
            list = areaService.getAreaList();
            modelMap.put(GeneralConstant.PAGE_SIZE, list);
            modelMap.put(GeneralConstant.TOTAL, list.size());
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());

        }

        return modelMap;
    }

    @RequestMapping(value = "addArea", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addArea(String areaStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        Area area = null;

        try {
            area = mapper.readValue(areaStr, Area.class);
            area.setAreaName(area.getAreaName() == null ? null : URLDecoder.decode(area.getAreaName(), "UTF-8"));
            area.setAreaDesc(area.getAreaDesc() == null ? null : URLDecoder.decode(area.getAreaDesc(), "UTF-8"));

        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (area != null && area.getAreaName() != null) {
            try {
                AreaExecution ae = areaService.addArea(area);
                if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());

                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域信息");
        }
        return modelMap;
    }


    @RequestMapping(value = "modifyArea", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyArea(String areaStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        Area area = null;
        try {
            area = mapper.readValue(areaStr, Area.class);
            area.setAreaName(area.getAreaName() == null ? null : URLDecoder.decode(area.getAreaName(), "UTF-8"));
            area.setAreaDesc(area.getAreaDesc() == null ? null : URLDecoder.decode(area.getAreaDesc(), "UTF-8"));

        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (area != null && area.getAreaId() != null) {
            try {
                AreaExecution ae = areaService.modifyArea(area);

                if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "removeArea",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeArea(Long areaId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (areaId != null && areaId > 0) {
            try {
                AreaExecution ae = areaService.removeArea(areaId);
                if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);


                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域主键");
        }
        return modelMap;
    }


    @RequestMapping(value = "removeAreas",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeAreas(String areaIdListStr) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Long.class);
        List<Long> areaIdList = null;

        try {
            areaIdList = mapper.readValue(areaIdListStr, javaType);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (areaIdList != null && areaIdList.size()>0){
            try {
                AreaExecution ae = areaService.removeAreaList(areaIdList);

                if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                }else{
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域主键");
        }

        return modelMap;
    }

}
