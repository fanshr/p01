package com.fanshr.p01.web.wechat;

import com.fanshr.p01.util.weixin.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/12/29 10:58
 * @date : Modified at 2021/12/29 10:58
 */
@Controller
@RequestMapping("wechat")
public class WechatController {

    private static final Logger log = LoggerFactory.getLogger(WechatController.class);

    @RequestMapping(method = {RequestMethod.GET})
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin get...");
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = null;
        log.debug("关键信息-->{}--{}--{}--{}",signature,timestamp,nonce,echostr);

        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                log.debug("weixin get success...");
                out.println(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
        }


    }
}
