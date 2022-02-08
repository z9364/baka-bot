package com.baka.modules;

import com.baka.base.Constants;
import com.baka.base.Subscribe;
import com.baka.utils.DownloadUtil;
import io.ktor.http.ContentType;
import net.mamoe.mirai.contact.AudioSupported;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Audio;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Text2Voice implements Subscribe {

    static MiraiLogger logger = MiraiLogger.create(Text2Voice.class.getName());

    private Text2Voice(){
        regs.add("说.*");
    }

    private static class Text2VoiceHolder {
        private static final Text2Voice INSTANCE = new Text2Voice();
    }

    public static Text2Voice getInstance(){
        return Text2Voice.Text2VoiceHolder.INSTANCE;
    }

    Listener<?> listener;
    private List<String> regs = new ArrayList<>();

    @Override
    public void startListener() {
        listener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            try {
                String content = event.getMessage().contentToString();
                // List<MessageContent> messages = new ArrayList<>();
                if (reg(regs, content)) {
                    String text = content.substring(1);
                    if(text.length()>0) {
                        // 将语音文件用md5加密存储到本地，防止文件名过长
                        String localPath = DownloadUtil.saveFile(Constants.BAIDU_YANYI, "GET", "lan=zh&spd=5&source=web&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8));
                        ExternalResource externalResource = ExternalResource.create(new FileInputStream(localPath));
                        //Audio voice = ExternalResource.uploadAsVoice(externalResource, event.getSubject());
                        Audio voice = event.getSubject().uploadAudio(externalResource);
                        externalResource.close();

                        event.getSubject().sendMessage(voice);
                    }

                }
            }catch (Exception e){
                logger.error(e.getMessage());
                //e.printStackTrace();
            }
        });
    }

    @Override
    public void stopListener() {
        listener.complete();
    }
}
