package com.fanshr.p01.web.superadmin;

import com.fanshr.p01.entity.Area;
import com.fanshr.p01.service.AreaService;
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
 * @date : Created at 2021/11/6 22:40
 * @date : Modified at 2021/11/6 22:40
 */

@Controller
@RequestMapping("/superadmin")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listareas",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listAreas(){
        Map<String, Object> modelMap = new HashMap<>();
        List<Area> list = new ArrayList<>();
        try {
            list = areaService.getAreaList();
            modelMap.put("rows",list);
            modelMap.put("size",list.size());
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());

        }

        return  modelMap;
    }
}
