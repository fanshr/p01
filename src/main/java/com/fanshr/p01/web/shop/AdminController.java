package com.fanshr.p01.web.shop;

import com.fanshr.p01.entity.Shop;
import com.fanshr.p01.util.ParamUtil;
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



    @RequestMapping(value = "/shopList",method = RequestMethod.GET)
    private String myList(){
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopManage",method = RequestMethod.GET)
    private String shopManage(HttpServletRequest request){
        long shopId = ParamUtil.getLong(request, "shopId");
        if (shopId<=0){
            Object currentShop = request.getSession().getAttribute("currentShop");
            if (currentShop == null) {
                return "shop/shoplist";
            }else{
                return "shop/shopmanage";
            }

        }else{
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            return "shop/shopmanage";
        }
    }

    @RequestMapping(value = "/shopEdit",method = RequestMethod.GET)
    private String shopEdit(){return "shop/shopedit";}



    @RequestMapping(value = "/ownerlogin")
    public String ownerLogin(HttpServletRequest request) {
        return "shop/ownerlogin";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    private String register() {
        return "shop/register";
    }

    @RequestMapping(value = "/changepsw", method = RequestMethod.GET)
    private String changePsw() {
        return "shop/changepsw";
    }

    @RequestMapping(value = "/ownerbind", method = RequestMethod.GET)
    private String ownerBind() {
        return "shop/ownerbind";
    }


    @RequestMapping(value = "/productManage", method = RequestMethod.GET)
    private String productManage() {
        return "shop/productmanage";
    }

    @RequestMapping(value = "/productEdit", method = RequestMethod.GET)
    private String productEdit() {
        return "shop/productedit";
    }

    @RequestMapping(value = "/productCategoryManage", method = RequestMethod.GET)
    private String productCategoryManage() {
        return "shop/productcategorymanage";
    }

    @RequestMapping(value = "/shopAuthManage", method = RequestMethod.GET)
    private String shopAuthManage() {
        return "shop/shopauthmanage";
    }

    @RequestMapping(value = "/shopAuthEdit", method = RequestMethod.GET)
    private String shopAuthEdit() {
        return "shop/shopauthedit";
    }

    @RequestMapping(value = "/productBuyCheck", method = RequestMethod.GET)
    private String productBuyCheck() {
        return "shop/productbuycheck";
    }

    @RequestMapping(value = "/awardDeliverCheck", method = RequestMethod.GET)
    private String awardDeliverCheck() {
        return "shop/awarddelivercheck";
    }

    @RequestMapping(value = "/userShopCheck", method = RequestMethod.GET)
    private String userShopCheck() {
        return "shop/usershopcheck";
    }

    @RequestMapping(value = "/awardManage", method = RequestMethod.GET)
    private String awardManage() {
        return "shop/awardmanage";
    }

    @RequestMapping(value = "/awardEdit", method = RequestMethod.GET)
    private String awardEdit() {
        return "shop/awardedit";
    }

    @RequestMapping(value = "/customerManage", method = RequestMethod.GET)
    private String customerManage() {
        return "shop/customermanage";
    }




}
