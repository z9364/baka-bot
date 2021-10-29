package com.baka.utils;

import com.baka.base.Constants;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DownloadUtil {

    static MiraiLogger logger = MiraiLogger.create(DownloadUtil.class.getName());

    public static void init() {
        //logger.info("初始化个der...");
    }
//
//    public static InputStream openUrl(String url, String method) throws Exception{
//        URL uri = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
//
//        connection.setConnectTimeout(5000);
//        connection.setInstanceFollowRedirects(false);
//        connection.setRequestMethod(method);
//
//        connection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
//
//        connection.connect();
//
//        int responseCode = connection.getResponseCode();
//        logger.info(url + "  ==>  " + responseCode);
//
//        InputStream inputStream = null;
//        if (responseCode == 200) {
//            inputStream = connection.getInputStream();//会隐式调用connect()
//        } else if (responseCode == 301 || responseCode == 302 ) {
//            inputStream = openUrl(connection.getHeaderField("Location"), method);
//        }
//        return inputStream;
//    }

    public static InputStream openUrl(@NotNull String url, @NotNull String method, @NotNull String param) throws Exception{

        if(param.length() > 0 && "GET".equalsIgnoreCase(method)){
            url += "?";
            url += param;
        }

        URL uri = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();

        connection.setConnectTimeout(Constants.CONNECT_OUT_TIME); // 设置超时时间
        connection.setInstanceFollowRedirects(true); // 自动跟随3xx请求
        connection.setRequestMethod(method);
        connection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 请求参数
        if(param.length() > 0 && "POST".equalsIgnoreCase(method)){
            OutputStream outStream = connection.getOutputStream();
            outStream.write(param.getBytes(StandardCharsets.UTF_8));
            outStream.flush();
        }

        connection.connect();

        int responseCode = connection.getResponseCode();
        logger.info(url + "  ==>  " + responseCode);

        InputStream inputStream = null;
        if (responseCode == 200) {
            inputStream = connection.getInputStream();//会隐式调用connect()
        // } else if (responseCode == 301 || responseCode == 302 ) {
        //    inputStream = openUrl(connection.getHeaderField("Location"), method);
        } else if (responseCode == 405){
            inputStream = openUrl(url, method, param);
        }
        return inputStream;
    }

    public static String saveFile(String url, String method, String param) throws Exception{
        String fileName = url.substring(url.lastIndexOf("/"));
        String savePath = Constants.SAVE_ROOT_PATH;

        if(url.contains("pixiv")){ // P站图
            savePath += Constants.PIXIV_PATH + fileName.replace("_", "/"); // _替换/ 分pid存储
        }else if(url.contains("wallhaven")){ // 壁纸
            savePath += Constants.WALLPAPER_PATH + fileName;
        }else if(url.contains("fox")){ // 猫图
            savePath += Constants.CAT_PATH + fileName;
        }else if(url.contains("fanyi")){
            fileName = url.substring(url.lastIndexOf("?")).replace("?", "/");
            savePath += Constants.VOICE_PATH + fileName;
        }

        File file = new File(savePath);
        if(file.exists()){
            logger.info("文件已存在 ==> " + savePath);
            return savePath;
        }

        String dirpath = savePath.substring(0, savePath.lastIndexOf("/"));
        File dir = new File(dirpath);
        if(!dir.exists()){
            boolean flag = dir.mkdir();
            if (flag)
                logger.info("创建目录：" + dirpath);
        }

        InputStream inputStream = openUrl(url, method, param);

        FileOutputStream outputStream = new FileOutputStream(file);
        int len;
        byte[] bytes = new byte[2048];
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        inputStream.close();
        logger.info("文件保存至 ==> " + savePath);
        return savePath;
    }

}
