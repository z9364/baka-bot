package com.baka.utils;

import com.baka.base.Constants;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.MiraiLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MemeUtil {

    static MiraiLogger logger = MiraiLogger.create(MemeUtil.class.getName());

    public synchronized static String[] getUploadMemeInfo(MessageChain chains){
        // chains[0]  [mirai:source:[4078],[1319245085]]
        // chains[1]  上传表情包 xxxx
        String[] arr = new String[2];
        for (SingleMessage chain : chains) {
            String str = chain.toString();
            if (chain instanceof Image) {
                arr[0] = Image.queryUrl((Image) chain);
            }
            if (str.startsWith("上传表情")) {
                arr[1] = str.substring(4).trim();
            }
        }
        return arr;
    }

    public static String saveMeme(String url, String name) throws Exception{
        // 不用DownUtil.saveFile()是因为文件名需要自定义
        InputStream inputStream = DownloadUtil.openUrl(url, "GET", "");
        String savePath = Constants.MEME_PATH + "/" + name + ".jpg";
        File file = new File(savePath);
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
