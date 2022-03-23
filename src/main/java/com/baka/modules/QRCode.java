package com.baka.modules;

import com.baka.base.Constants;
import com.baka.base.Subscribe;
import com.baka.utils.DownloadUtil;
import com.baka.utils.MessageUtil;
import com.github.binarywang.utils.qrcode.MatrixToLogoImageConfig;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QRCode implements Subscribe {

    static MiraiLogger logger = MiraiLogger.Factory.INSTANCE.create(QRCode.class);
    static File logo = null;

    private QRCode(){
        regs.add("识别二维码 .*");
        regs.add("生成二维码 .*");
    }

    private static class QRCodeHolder {
        private static final QRCode INSTANCE = new QRCode();
    }

    public static QRCode getInstance(){
        return QRCode.QRCodeHolder.INSTANCE;
    }

    Listener<?> listener;
    private List<String> regs = new ArrayList<>();


    @Override
    public void startListener() {
        listener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            try {
                String content = event.getMessage().serializeToMiraiCode().trim();
                // List<MessageContent> messages = new ArrayList<>();
                if(content.startsWith("识别二维码")){
                    if(content.contains("mirai:image")) {
                        List<MessageContent> mes = new ArrayList<>();
                        MessageChain chains = event.getMessage();
                        for (SingleMessage chain : chains) {
                            logger.info(chain.getClass().getTypeName());
                            if (chain instanceof Image) {
                                String url = Image.queryUrl((Image) chain);
                                //下载二维码
                                logger.info("url："+url);
                                long longTime = new Date().getTime();
                                String QRCodeFile = DownloadUtil.saveFile(url, "GET", "", Constants.QRCODE_TEMP_PATH + "/" + longTime + ".jpg");
                                mes.add(new PlainText(QrcodeUtils.decodeQrcode(new File(QRCodeFile))));
                            }
                        }
                        if (mes.size()>0){
                            event.getSubject().sendMessage(MessageUtil.bulidForwordedMessage("识别内容", mes));
                            return;
                        }
                        event.getSubject().sendMessage("未识别到二维码...");
                    }
                    return;
                }
                if(content.startsWith("生成二维码")){
                    String cont = content.substring(5);
                    MessageChain chains = event.getMessage();
                    for (SingleMessage chain : chains){
                        if (chain instanceof Image) {
                            String url = Image.queryUrl((Image) chain);
                            //下载二维码
                            long longTime = new Date().getTime();
                            String logoFile = DownloadUtil.saveFile(url, "GET", "", Constants.QRCODE_TEMP_PATH + "/" + longTime + ".jpg");
                            logo = new File(logoFile);
                        }
                    }
                    MatrixToLogoImageConfig config = new MatrixToLogoImageConfig(Color.WHITE, 5);
                    byte[] QRCode = QrcodeUtils.createQrcode(cont, 400, logo, config);

                    ExternalResource externalResource = ExternalResource.create(QRCode);
                    Image image = event.getSubject().uploadImage(externalResource);

                    event.getSubject().sendMessage(image);

                    logo = null;
                }

            }catch (Exception e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Override
    public void stopListener() {
        listener.complete();
    }




}
