package com.baka.utils;

import com.baka.base.Constants;
import net.mamoe.mirai.message.data.ForwardMessage;
import net.mamoe.mirai.message.data.MessageContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MessageUtil {

    /**
     * 转发消息构建器
     * @param title 标题
     * @param messages 转发内容 miraiCode 格式。
     *                 参考 https://docs.mirai.mamoe.net/Messages.html#%E7%94%B1-codablemessage-%E5%8F%96%E5%BE%97-mirai-%E7%A0%81%E5%AD%97%E7%AC%A6%E4%B8%B2
     * @return
     */
    public static ForwardMessage bulidForwordedMessage(String title, List<MessageContent> messages){
        List<ForwardMessage.Node> nodes = new ArrayList<>();
        for(MessageContent message : messages){
            nodes.add(new ForwardMessage.Node(Constants.LITTEL_ICE_ID, Long.valueOf(new Date().getTime() / 1000).intValue(), Constants.LITTEL_ICE_NAME, message));
        }
        // 第 2,3 个参数不知道干啥的...
        return new ForwardMessage(Collections.singletonList("很多消息..."), title, "", "", "查看" + messages.size() + "条消息", nodes);
    }

    public static ForwardMessage bulidForwordedMessage(String title, MessageContent message){

        List<ForwardMessage.Node> nodes = new ArrayList<>();
        nodes.add(new ForwardMessage.Node(Constants.LITTEL_ICE_ID, Long.valueOf(new Date().getTime() / 1000).intValue(), Constants.LITTEL_ICE_NAME, message));
        // 第 2,3 个参数不知道干啥的...
        return new ForwardMessage(Collections.singletonList("很多消息..."), title, "", "", "查看1条消息", nodes);
    }

}
