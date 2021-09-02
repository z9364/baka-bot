package com.baka.modules;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baka.base.Constants;
import com.baka.base.Subscribe;
import com.baka.utils.DownloadUtil;
import com.baka.utils.IOUtil;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.MiraiLogger;

public class GoodGoodSay implements Subscribe {


    static MiraiLogger logger = MiraiLogger.create(GoodGoodSay.class.getName());

    Listener<?> listener;

    private GoodGoodSay(){
        // regs.add("猫图");
    }

    private static class GoodGoodSayHolder {
        private static final GoodGoodSay INSTANCE = new GoodGoodSay();
    }

    public static GoodGoodSay getInstance(){
        return GoodGoodSay.GoodGoodSayHolder.INSTANCE;
    }

    @Override
    public void startListener() {
        listener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, event -> {

            String content = event.getMessage().contentToString().trim();

            String text = content.replaceAll("[^\\w]+", ",");

            if(text.equals(",")) { // 如果替换之后只剩下','说明没有字母
                return;
            }

            if(text.startsWith(",")){
                text = text.substring(1);
            }

            try {
                StringBuilder msg = new StringBuilder(event.getSenderName()).append("语中的\n");
                String json = IOUtil.readInputStream(DownloadUtil.openUrl(Constants.NBNHHSH_API, "POST", "text="+ text));
                //System.out.println("json ==> " + json);
                JSONArray result = JSONArray.parseArray(json);

                if(result.isEmpty()){
                    return;
                }

                for(Object obj : result){
                    JSONObject jsonobj = (JSONObject) obj;
                    msg.append(jsonobj.get("name")).append("可能是：\n");
                    JSONArray array = jsonobj.getJSONArray("trans");
                    for(Object o : array){
                        msg.append(o.toString()).append("，");
                    }
                    msg.append("\n");
                }

                event.getSubject().sendMessage(new PlainText(msg));

            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        });
    }

    @Override
    public void stopListener() {
        listener.complete();
    }
}
