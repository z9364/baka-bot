package com.baka.modules;

import com.baka.base.Constants;
import com.baka.base.Subscribe;
import com.baka.utils.MessageUtil;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Meme implements Subscribe{

    private Meme(){
        regs.add(".*jpg");
        regs.add("查看表情包列表");

        File[] fileList = new File(Constants.MEME_PATH).listFiles();
        assert fileList != null;
        for(File file : fileList){
            memes.put(file.getName().split("\\.")[0], file.getPath());
        }
    }

    private static class MemeHolder {
        private static final Meme INSTANCE = new Meme();
    }

    public static Meme getInstance(){
        return Meme.MemeHolder.INSTANCE;
    }

    Listener<?> listener;
    private List<String> regs = new ArrayList<>();
    private Map<String, String> memes = new HashMap<>();


    @Override
    public void startListener() {
        listener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, event -> {
            String content = event.getMessage().contentToString().trim();
            if(reg(regs, content)) {
                if("查看表情包列表".equals(content)){
                    StringBuilder b = new StringBuilder();
                    for(String s : memes.keySet()){
                        b.append(s).append("\n");
                    }
                    event.getSubject().sendMessage(MessageUtil.bulidForwordedMessage("表情包", new PlainText(b.toString())));
                }else{
                    String localPath = memes.get(content.split("\\.")[0]);
                    Image image = null;
                    try {
                        ExternalResource externalResource = ExternalResource.create(new FileInputStream(localPath));
                        image = event.getSubject().uploadImage(externalResource);
                        externalResource.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    event.getSubject().sendMessage(image);
                }
            }
        });
    }

    @Override
    public void stopListener() {
        listener.complete();
    }

}
