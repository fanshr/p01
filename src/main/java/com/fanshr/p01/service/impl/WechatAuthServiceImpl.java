package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.PersonInfoDao;
import com.fanshr.p01.dao.WechatAuthDao;
import com.fanshr.p01.dto.WechatAuthExecution;
import com.fanshr.p01.entity.PersonInfo;
import com.fanshr.p01.entity.WechatAuth;
import com.fanshr.p01.enums.WechatAuthStateEnum;
import com.fanshr.p01.service.WechatAuthService;
import com.fanshr.p01.util.FileUtil;
import com.fanshr.p01.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:27
 * @date : Modified at 2021/11/23 10:27
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService {

    private static final Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);
    @Autowired
    private WechatAuthDao wechatAuthDao;
    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public WechatAuth getWechatAuthByOpenId(String openId) {
        return wechatAuthDao.queryWechatInfoByOpenId(openId);
    }

    @Override
    public WechatAuthExecution register(WechatAuth wechatAuth, CommonsMultipartFile profileImg) throws RuntimeException {
        if (wechatAuth == null || wechatAuth.getOpenId() == null) {
            return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
        }
        try {
            wechatAuth.setCreateTime(new Date());
            if (wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() == null) {
                if (profileImg != null) {
                    try {
                        addProfileImg(wechatAuth, profileImg);
                    } catch (Exception e) {
                        log.debug("addUserProfileImg error：" + e.toString());
                        throw new RuntimeException("addUserProfileImg error：" + e.toString());

                    }

                }

                try {
                    wechatAuth.getPersonInfo().setCreateTime(new Date());
                    wechatAuth.getPersonInfo().setLastEditTime(new Date());
                    wechatAuth.getPersonInfo().setCustomerFlag(1);
                    wechatAuth.getPersonInfo().setShopOwnerFlag(1);
                    wechatAuth.getPersonInfo().setAdminFlag(0);
                    wechatAuth.getPersonInfo().setEnableStatus(1);
                    PersonInfo personInfo = wechatAuth.getPersonInfo();
                    int effectedRows = personInfoDao.insertPersonInfo(personInfo);
                    wechatAuth.setUserId(personInfo.getUserId());
                    if (effectedRows <= 0) {
                        throw new RuntimeException("添加用户信息失败");
                    }
                } catch (Exception e) {
                    log.debug("insertPersonInfo error:" + e.toString());
                    throw new RuntimeException("insertPersonInfo error: "
                            + e.getMessage());
                }

            }
            int effectedRows = wechatAuthDao.insertWechatAuth(wechatAuth);
            if (effectedRows <= 0) {
                throw new RuntimeException("账号创建失败");
            } else {
                return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, wechatAuth);
            }
        } catch (Exception e) {
            log.debug("insertWechatAuth error:" + e.toString());
            throw new RuntimeException("insertWechatAuth error: "
                    + e.getMessage());
        }


    }

    private void addProfileImg(WechatAuth wechatAuth, CommonsMultipartFile profileImg) {
        String dest = FileUtil.getPersonInfoImagePath();
        String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);
        wechatAuth.getPersonInfo().setProfileImg(profileImgAddr);

    }
}
