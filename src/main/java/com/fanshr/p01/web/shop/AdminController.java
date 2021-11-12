package com.fanshr.p01.web.shop;

import com.google.code.kaptcha.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/12 14:48
 * @date : Modified at 2021/11/12 14:48
 */
@Controller
@RequestMapping(value = "/shop",method = {RequestMethod.GET,RequestMethod.POST})
public class AdminController {

    @RequestMapping("/test")
    public Map<String, Object> productCategory(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        String kaptchaExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println(kaptchaExpected);
        modelMap.put("verifyCode",kaptchaExpected);
        return modelMap;
    }

    @RequestMapping(value = "/shopedit",method = RequestMethod.GET)
    private String shopEdit(){return "shop/index";}



}
