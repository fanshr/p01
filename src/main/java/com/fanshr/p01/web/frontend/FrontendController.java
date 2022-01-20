package com.fanshr.p01.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/12/27 09:20
 * @date : Modified at 2021/12/27 09:20
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    private String showMainPage() {
        return "frontend/mainpage";
    }

    @RequestMapping(value = "/productDetail", method = RequestMethod.GET)
    private String showProductDetail() {
        return "frontend/productdetail";
    }

    @RequestMapping(value = "/shopDetail", method = RequestMethod.GET)
    private String showShopDetail() {
        return "frontend/shopdetail";
    }

    @RequestMapping(value = "/shopList", method = RequestMethod.GET)
    private String showShopList() {
        return "frontend/shoplist";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index() {
        return "frontend/index";
    }

    @RequestMapping(value = "/myPoint", method = RequestMethod.GET)
    private String myPoint() {
        return "frontend/mypoint";
    }

    @RequestMapping(value = "/myRecord", method = RequestMethod.GET)
    private String myRecord() {
        return "frontend/myrecord";
    }

    @RequestMapping(value = "/pointRecord", method = RequestMethod.GET)
    private String pointRecord() {
        return "frontend/pointrecord";
    }

    @RequestMapping(value = "/awardDetail", method = RequestMethod.GET)
    private String awardDetail() {
        return "frontend/awarddetail";
    }

    @RequestMapping(value = "/customerBind", method = RequestMethod.GET)
    private String customerBind() {
        return "frontend/customerbind";
    }
}
