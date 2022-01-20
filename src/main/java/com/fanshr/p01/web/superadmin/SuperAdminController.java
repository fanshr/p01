package com.fanshr.p01.web.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/16 11:31
 * @date : Modified at 2021/11/16 11:31
 */
@Controller
@RequestMapping(value = "/sa/",method = {RequestMethod.GET,RequestMethod.POST})
public class SuperAdminController {

    @RequestMapping(value = "login",method = RequestMethod.GET)
    private String login(){
        return "superadmin/login";
    }

    @RequestMapping(value = "personInfoManage",method = RequestMethod.GET)
    private String personInfoManage(){
        return "superadmin/personinfomanage";
    }

    @RequestMapping(value = "shopManage",method = RequestMethod.GET)
    private String shopManage(){
        return "superadmin/shopmanage";
    }

    @RequestMapping(value = "areaManage",method = RequestMethod.GET)
    private String areaManage(){
        return "superadmin/areamanage";
    }

    @RequestMapping(value = "shopCategoryManage",method = RequestMethod.GET)
    private String shopCategoryManage(){
        return "superadmin/shopcategorymanage";
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    private String main() {
        return "superadmin/main";
    }
    @RequestMapping(value = "top", method = RequestMethod.GET)
    private String top() {
        return "superadmin/top";
    }


}
