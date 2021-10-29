package com.baka.modules;

import com.baka.base.Constants;
import com.baka.base.Subscribe;
import com.baka.utils.DownloadUtil;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatImage implements Subscribe {


    static MiraiLogger logger = MiraiLogger.create(CatImage.class.getName());

    private CatImage(){
        regs.add("猫图");
    }

    private static class CatImageHolder {
        private static final CatImage INSTANCE = new CatImage();
    }

    public static CatImage getInstance(){
        return CatImage.CatImageHolder.INSTANCE;
    }

    private Listener<?> listener;
    private List<String> regs = new ArrayList<>();

    @Override
    public void startListener() {
        listener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, event -> {

            String content = event.getMessage().contentToString().trim();

            if(reg(regs, content)){
                try {
                    Random random = new Random();
                    int catIndex = random.nextInt(Constants.CAT_IMAGE_MAX_INDEX);

                    String url = Constants.CAT_URL + catIndex + ".jpg";
                    String localpath = DownloadUtil.saveFile(url, "GET", "");

                    ExternalResource externalResource = ExternalResource.create(new FileInputStream(localpath));
                    Image image = event.getSubject().uploadImage(externalResource);
                    externalResource.close();

                    event.getSubject().sendMessage(image);

                } catch (Exception e) {
                    event.getSubject().sendMessage(new PlainText(e.getMessage()));
                    logger.error(e.getMessage());
                }

            }

        });
    }

    @Override
    public void stopListener() {
        listener.complete();
    }
}
