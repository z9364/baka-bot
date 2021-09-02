package com.baka.modules;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baka.base.Constants;
import com.baka.base.Subscribe;
import com.baka.utils.DownloadUtil;
import com.baka.utils.IOUtil;
import com.baka.utils.JSONUtil;
import com.baka.utils.MessageUtil;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageContent;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ColorImage implements Subscribe {

    static MiraiLogger logger = MiraiLogger.create(ColorImage.class.getName());

    private ColorImage(){
        regs.add("[色|涩]图");
        regs.add("不够[涩|色]");
        regs.add("来[1-9][张|份](.*)[涩|色]图");
    }

    private static class ColorImageHolder {
        private static final ColorImage INSTANCE = new ColorImage();
    }

    public static ColorImage getInstance(){
        return ColorImageHolder.INSTANCE;
    }

    Listener<?> listener;
    private final List<String> regs = new ArrayList<>();

    @Override
    public void startListener(){
        listener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, event -> {
                String content = event.getMessage().contentToString();
                List<MessageContent> messages = new ArrayList<>();

                if (reg(regs, content)) {
                    String num = "3";
                    String tag = "";

                    if(content.startsWith("来")){
                        char c = content.charAt(1);
                        if(Character.isDigit(c)){
                            num = String.valueOf(c);
                        }
                        if (content.length()>5) {
                            tag = URLEncoder.encode(content.substring(3, content.length() - 2), StandardCharsets.UTF_8);
                        }
                    }

                    // 如果触发
                    String apiurl = Constants.LOLICON_URL + "&num="+num+"&tag="+tag;
                    String json;
                    try {
                        json = IOUtil.readInputStream(DownloadUtil.openUrl(apiurl, "GET", ""));
                    }catch (Exception e){
                        logger.error(e.getMessage());
                        messages.add(new PlainText("查询失败：" + e.getMessage()));
                        return;
                    }

                    Map<String, Object> map = JSONUtil.str2json(json);
                    JSONArray data = (JSONArray) map.get("data");

                    messages.add(new PlainText(content));

                    if(data.isEmpty()){
                        messages.add(new PlainText("没 ~ 找 ~ 到 ~ 哦 ~"));
                    }

                    for (Object obj : data) {
                        String url = ((JSONObject) ((JSONObject) obj).get("urls")).get("original").toString();
                        url = url.replace(Constants.PIXIV_CAT_PERFIX, Constants.PROXY_PIXIV_PERFIX);
                        Image image = null;
                        try {
                            String localpath = DownloadUtil.saveFile(url, "GET", "");
                            ExternalResource externalResource = ExternalResource.create(new FileInputStream(localpath));
                            image = event.getSubject().uploadImage(externalResource);
                            externalResource.close();
                        }catch (Exception e){
                            logger.error(e.getMessage());
                            messages.add(new PlainText("查询失败：" + e.getMessage()));
                        }
                        if(image != null) {
                            messages.add(image);
                        }
                    }

                    event.getSubject().sendMessage(MessageUtil.bulidForwordedMessage("群聊的聊天记录", messages));
                }
        });
    }

    @Override
    public void stopListener() {
        listener.complete();
    }
}
