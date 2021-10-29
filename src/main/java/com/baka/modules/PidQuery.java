package com.baka.modules;

import com.alibaba.fastjson.JSONArray;
import com.baka.base.Constants;
import com.baka.base.Subscribe;
import com.baka.utils.DownloadUtil;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * pid 查询
 */
public class PidQuery implements Subscribe {


    static MiraiLogger logger = MiraiLogger.create(PidQuery.class.getName());

    private PidQuery(){
        regs.add("pid.*");
        regs.add("PID.*");
    }

    private static class PidQueryHolder {
        private static final PidQuery INSTANCE = new PidQuery();
    }

    public static PidQuery getInstance(){
        return PidQuery.PidQueryHolder.INSTANCE;
    }

    Listener<?> listener;
    private List<String> regs = new ArrayList<>();

    @Override
    public void startListener() {
        listener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, event -> {

            String content = event.getMessage().contentToString().trim();

            if(reg(regs, content)) {
                List<MessageContent> messages = new ArrayList<>(); // 消息列表
                String pid = content.substring(3);
                Map<String, Object> map;
                try {
                    map = JSONUtil.io2json(DownloadUtil.openUrl(Constants.PID_URL, "GET", "p="+pid));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    return;
                }

                if(!Boolean.parseBoolean(String.valueOf(map.get("success")))){
                    event.getSubject().sendMessage(MessageUtil.bulidForwordedMessage("群聊的聊天记录", Arrays.asList(new PlainText(content), new PlainText("查询失败"))));
                    return;
                }

                boolean isMultiple = Boolean.parseBoolean(map.get("multiple") + "");

                messages.add(new PlainText(content));

                if(isMultiple){ // 多P
                    JSONArray orgUrls = (JSONArray) map.get("original_urls");
                    for(Object objurl : orgUrls){
                        String url = objurl.toString().replace(Constants.ORG_PIXIV_PERFIX, Constants.PROXY_PIXIV_PERFIX);
                        Image image = null;
                        try {
                            String localpath = DownloadUtil.saveFile(url, "GET", "");
                            ExternalResource externalResource = ExternalResource.create(new FileInputStream(localpath));
                            image = event.getSubject().uploadImage(externalResource);
                            externalResource.close();
                        }catch (Exception e){
                            messages.add(new PlainText("查询失败：" + e.getMessage()));
                            logger.error(e.getMessage());
                        }

                        messages.add(image);
                    }
                }else { // 单P
                    String orgUrl = map.get("original_url") + "";
                    String url = orgUrl.replace(Constants.ORG_PIXIV_PERFIX, Constants.PROXY_PIXIV_PERFIX);
                    Image image = null;
                    try {
                        String localpath = DownloadUtil.saveFile(url, "GET", "");
                        ExternalResource externalResource = ExternalResource.create(new FileInputStream(localpath));
                        image = event.getSubject().uploadImage(externalResource);
                        externalResource.close();
                    }catch (Exception e){
                        messages.add(new PlainText("查询失败：" + e.getMessage()));
                        logger.error(e.getMessage());
                    }

                    messages.add(image);
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
