package com.baka.task;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.FileInputStream;
import java.util.concurrent.Callable;

public class UploadImageTask implements Callable<Image> {

    private final MessageEvent event;
    private final String localpath;

    public UploadImageTask(MessageEvent event, String localpath){
        this.event = event;
        this.localpath = localpath;
    }

    @Override
    public Image call() throws Exception {
        ExternalResource externalResource = ExternalResource.create(new FileInputStream(localpath));
        Image image = event.getSubject().uploadImage(externalResource);
        externalResource.close();
        return image;
    }
}
