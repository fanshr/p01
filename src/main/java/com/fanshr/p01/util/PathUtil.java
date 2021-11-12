package com.fanshr.p01.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/11 17:10
 * @date : Modified at 2021/11/11 17:10
 */
public class PathUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static String separator = System.getProperty("file.separator");
    private static final Random r = new Random();

    /**
     * 生成随机文件名，规则如下：
     * 当前年月日时分秒+五位随机数
     * @return randomFileName
     */
    public static String getRandomFileName() {
        int randomNum = (int) (r.nextDouble()*(99999-10000+1))+10000;
        String timeStr = sdf.format(new Date());
        return timeStr+randomNum;
    }

    public static String getImgBasePath() {

        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")){
            basePath = "D:/project/image/p01/";
        }else{
            basePath = "/data/image/p01/";
        }
        basePath = basePath.replace("/",separator);
        return  basePath;
    }

    public static String getShopImagePath(Long shopId) {
        StringBuilder shopImgPathBuilder = new StringBuilder();
        shopImgPathBuilder.append("/upload/images/item/shop/");
        shopImgPathBuilder.append(shopId);
        String shopImgPath = shopImgPathBuilder.toString().replace("/", separator);
        return shopImgPath;
    }
}
