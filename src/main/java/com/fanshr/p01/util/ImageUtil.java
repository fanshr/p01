package com.fanshr.p01.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/11 17:10
 * @date : Modified at 2021/11/11 17:10
 */
public class ImageUtil {

    public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
        // 1.生成全局唯一随机文件名
        String realFileName = PathUtil.getRandomFileName();
        // 2.获取文件扩展名
        String extension = getFileExtension(thumbnail);
        // 3.创建不存在的目录
        makeDirPath(targetAddr);
        // 4.绝对uri 创建文件
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        // 5.根据文件创建缩略图
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200, 200).outputQuality(0.25f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }

        return relativeAddr;


    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }

    }

    private static String getFileExtension(CommonsMultipartFile cFile) {
        String originalFilename = cFile.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

}
