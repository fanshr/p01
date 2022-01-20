package com.fanshr.p01.util.wechat;

import com.fanshr.p01.dto.UserAccessToken;
import com.fanshr.p01.dto.WechatUser;
import com.fanshr.p01.entity.PersonInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 *
 * 微信工具类
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/12/29 13:47
 * @date : Modified at 2021/12/29 13:47
 */
public class WechatUtil {
    private static final Logger log = LoggerFactory.getLogger(WechatUtil.class);


    public static UserAccessToken getUserAccessToken(String code){
        String appId = "wxdab349d13a5b6bcc";
        log.debug("appid{}",appId);
        String appsecret = "89f6cdf97ae15c3c8d6e485cc2eb3480";
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret
                + "&code=" + code + "&grant_type=authorization_code";

        String tokenStr = httpsRequest(url,"GET",null);
        log.debug("successToken-->{}",tokenStr);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper mapper = new ObjectMapper();
        try {
            token = mapper.readValue(tokenStr,UserAccessToken.class);
        } catch (JsonParseException e) {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        }

        if (token == null) {
            log.error("获取用户accessToken失败");
            return null;
        }
        return token;

    }

    /**
     * 获取Wechat用户实体类
     * @param accessToken
     * @param openId
     * @return
     */
    public static WechatUser getUserInfo(String accessToken, String openId) {
        // 根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                + "&lang=zh_CN";
        // 访问该URL获取用户信息json 字符串
        String userStr = httpsRequest(url, "GET", null);
        log.debug("user info :" + userStr);
        WechatUser user = new WechatUser();

        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(userStr, WechatUser.class);
        } catch (JsonParseException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        }
        if (user == null) {
            log.error("获取用户信息失败。");
            return null;
        }
        return user;
    }

    public static String createMenus(String accessToken){
        String outputStr = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"扫码\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"scancode_waitmsg\", \n" +
                "                    \"name\": \"扫码带提示\", \n" +
                "                    \"key\": \"rselfmenu_0_0\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"scancode_push\", \n" +
                "                    \"name\": \"扫码推事件\", \n" +
                "                    \"key\": \"rselfmenu_0_1\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }, \n" +
                "        {\n" +
                "            \"name\": \"发图\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"pic_sysphoto\", \n" +
                "                    \"name\": \"系统拍照发图\", \n" +
                "                    \"key\": \"rselfmenu_1_0\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"pic_photo_or_album\", \n" +
                "                    \"name\": \"拍照或者相册发图\", \n" +
                "                    \"key\": \"rselfmenu_1_1\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"pic_weixin\", \n" +
                "                    \"name\": \"微信相册发图\", \n" +
                "                    \"key\": \"rselfmenu_1_2\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }, \n" +
                "        {\n" +
                "            \"name\": \"发送位置\", \n" +
                "            \"type\": \"location_select\", \n" +
                "            \"key\": \"rselfmenu_2_0\"\n" +
                "        }, \n" +
                "        {\n" +
                "            \"type\": \"media_id\", \n" +
                "            \"name\": \"图片\", \n" +
                "            \"media_id\": \"MEDIA_ID1\"\n" +
                "        }, \n" +
                "        {\n" +
                "            \"type\": \"view_limited\", \n" +
                "            \"name\": \"图文消息\", \n" +
                "            \"media_id\": \"MEDIA_ID2\"\n" +
                "        }, \n" +
                "        {\n" +
                "            \"type\": \"article_id\", \n" +
                "            \"name\": \"发布后的图文消息\", \n" +
                "            \"article_id\": \"ARTICLE_ID1\"\n" +
                "        }, \n" +
                "        {\n" +
                "            \"type\": \"article_view_limited\", \n" +
                "            \"name\": \"发布后的图文消息\", \n" +
                "            \"article_id\": \"ARTICLE_ID2\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        // 根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
        // 访问该URL获取用户信息json 字符串
        String result = httpsRequest(url, "GET", outputStr);
        log.debug("menu-->{}",result);
        return result;

    }


    /**
     * 发起https请求并获取结果
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    private static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();

        try {
            // 创建SSLContext对象，并使用我们制定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null,tm,new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(ssf);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                connection.connect();

            }

            // 存在需要提交的数据
            if (null != outputStr) {
                OutputStream os = connection.getOutputStream();
                os.write(outputStr.getBytes(StandardCharsets.UTF_8));
                os.close();

            }

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            while ((str = br.readLine()) != null) {
                buffer.append(str);
            }
            br.close();
            isr.close();
            is.close();
            is = null;
            connection.disconnect();
            log.debug("https buffer:{}", buffer.toString());


        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return buffer.toString();


    }

    public static PersonInfo getPersonInfoFromRequest(WechatUser user) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(user.getNickName());
        personInfo.setGender(user.getSex()+"");
        personInfo.setProfileImg(user.getHeadimgurl());
        personInfo.setEnableStatus(1);
        return personInfo;
    }
}
