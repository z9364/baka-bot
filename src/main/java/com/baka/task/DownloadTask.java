package com.baka.task;

import com.baka.utils.DownloadUtil;
import net.mamoe.mirai.utils.MiraiLogger;

import java.util.concurrent.Callable;

/**
 * 下载任务，下载网络文件，并返回本地地址
 */
public class DownloadTask implements Callable<String> {

    static MiraiLogger logger = MiraiLogger.create(DownloadUtil.class.getName());

    private final String url;

    public DownloadTask(String url){
        this.url = url;
    }

    @Override
    public String call() throws Exception {
//        String fileName = url.substring(url.lastIndexOf("/"));
//        String savePath = Constants.SAVE_ROOT_PATH;
//
//        if(url.contains("pixiv")){ // P站图
//            savePath += Constants.PIXIV_PATH + fileName.replace("_", "/"); // _替换/ 分pid存储
//        }else if(url.contains("wallhaven")){ // 壁纸
//            savePath += Constants.WALLPAPER_PATH + fileName;
//        }else if(url.contains("fox")){ // 猫图
//            savePath += Constants.CAT_PATH + fileName;
//        }else if(url.contains("fanyi")){
//            fileName = url.substring(url.lastIndexOf("?")).replace("?", "/");
//            savePath += Constants.VOICE_PATH + fileName;
//        }
//
//        File file = new File(savePath);
//        if(file.exists()){
//            logger.info("文件已存在 ==> " + savePath);
//            return savePath;
//        }
//
//        String dirpath = savePath.substring(0, savePath.lastIndexOf("/"));
//        File dir = new File(dirpath);
//        if(!dir.exists()){
//            boolean flag = dir.mkdir();
//            if (flag)
//                logger.info("创建目录：" + dirpath);
//        }
//
//        InputStream inputStream = DownloadUtil.openUrl(url, "GET", "");
//
//        FileOutputStream outputStream = new FileOutputStream(file);
//        int len;
//        byte[] bytes = new byte[2048];
//        while ((len = inputStream.read(bytes)) != -1) {
//            outputStream.write(bytes, 0, len);
//        }
//        inputStream.close();
//        logger.info("文件保存至 ==> " + savePath);
//        return savePath
        return null;
    }

}
