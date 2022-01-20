package com.fanshr.p01.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信用户实体类
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/12/29 13:26
 * @date : Modified at 2021/12/29 13:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WechatUser implements Serializable {

    private static final long serialVersionUID = -2661065752539756747L;

    // 标识该公众号下面，该用户的唯一ID
    @JsonProperty("openid")
    private String openId;
    // 用户昵称
    @JsonProperty("nickname")
    private String nickName;

    // 性别
    @JsonProperty("sex")
    private int sex;
    // 省份
    @JsonProperty("province")
    private String province;
    // 城市
    @JsonProperty("city")
    private String city;
    // 区
    @JsonProperty("country")
    private String country;
    // 头像图片地址
    @JsonProperty("headimgurl")
    private String headimgurl;
    // 语言
    @JsonProperty("language")
    private String language;
    // 用户权限
    @JsonProperty("privilege")
    private String[] privilege;


}
