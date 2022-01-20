package com.fanshr.p01.web.wechat;

import com.fanshr.p01.dto.ShopExecution;
import com.fanshr.p01.dto.UserAccessToken;
import com.fanshr.p01.dto.WechatAuthExecution;
import com.fanshr.p01.dto.WechatUser;
import com.fanshr.p01.entity.PersonInfo;
import com.fanshr.p01.entity.WechatAuth;
import com.fanshr.p01.enums.WechatAuthStateEnum;
import com.fanshr.p01.service.PersonInfoService;
import com.fanshr.p01.service.ShopAuthMapService;
import com.fanshr.p01.service.ShopService;
import com.fanshr.p01.service.WechatAuthService;
import com.fanshr.p01.util.wechat.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxdab349d13a5b6bcc&redirect_uri=http://fanshr.cn/p01/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/12/29 11:28
 * @date : Modified at 2021/12/29 11:28
 */
@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {
    private static final Logger log = LoggerFactory.getLogger(WechatLoginController.class);
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private WechatAuthService wechatAuthService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopAuthMapService shopAuthMapService;

    public static final String FRONTEND = "1";
    public static final String SHOPEND = "2";

    @RequestMapping(value = "logincheck",method = {RequestMethod.GET})
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");

        // 微信公众号传输的授权码，通过code获取access_token,进而获取用户信息
        String code = request.getParameter("code");

        // 可以用于传输自定义的信息，方便程序调用，这里也可以不用
        String roleType = request.getParameter("state");
        log.debug("login code-->{}",code);

        WechatAuth auth = null;
        WechatUser user = null;
        String openId = null;
        if (code != null) {
            UserAccessToken token;
            try {
                token = WechatUtil.getUserAccessToken(code);
                log.debug("login token：{}", token.toString());
                String accessToken = token.getAccessToken();
                openId = token.getOpenId();
                user = WechatUtil.getUserInfo(accessToken, openId);
                // TODO: 创建菜单
                WechatUtil.createMenus(accessToken);
                log.debug("login user:{}",user.toString());
                request.getSession().setAttribute("openId", openId);
                auth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (Exception e) {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId：{}", toString());
                e.printStackTrace();
            }

        }

        log.debug("login success");
        log.debug("login role_type:"+roleType);
        if (FRONTEND.equals(roleType)) {
            PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
            if (auth == null) {
                personInfo.setCustomerFlag(1);
                auth = new WechatAuth();
                auth.setOpenId(openId);
                auth.setPersonInfo(personInfo);
                WechatAuthExecution we = wechatAuthService.register(auth, null);
                if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
                    return null;
                }
            }
            personInfo = personInfoService.getPersonInfoById(auth.getUserId());
            request.getSession().setAttribute("user",personInfo);
            return "frontend/index";
        }

        if (SHOPEND.equals(roleType)) {
            PersonInfo personInfo = null;
            WechatAuthExecution we = null;
            if (auth == null) {
                auth = new WechatAuth();
                auth.setOpenId(openId);
                personInfo = WechatUtil.getPersonInfoFromRequest(user);
                personInfo.setShopOwnerFlag(1);
                auth.setPersonInfo(personInfo);
                we = wechatAuthService.register(auth, null);
                if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
                    return null;
                }
            }
            personInfo = personInfoService.getPersonInfoById(auth.getUserId());
            request.getSession().setAttribute("user", personInfo);
            ShopExecution se = shopService.getByEmployeeId(personInfo.getUserId());
            request.getSession().setAttribute("user",personInfo);
            if (se.getShopList() == null || se.getShopList().size() <= 0) {
                return "shop/shopEdit";
            }else{
                return "shop/shopList";
            }
        }

        return null;

    }


}
