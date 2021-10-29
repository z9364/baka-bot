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

import java.util.ArrayList;
import java.util.List;

public class GoodGoodSay implements Subscribe {


    static MiraiLogger logger = MiraiLogger.create(GoodGoodSay.class.getName());

    Listener<?> listener;
    private List<String> regs = new ArrayList<>();

    private GoodGoodSay(){
        regs.add("好好说话\\s.*");
        regs.add("hhsh\\s.*");
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

            String content = event.getMessage().contentToString();
            if(reg(regs, content)){
                String temp = content.substring(5);
                // 去除汉字和数字 将"，"换成","
                String text = temp.replaceAll("[^\\w]+", "").replaceAll("[\\d]+", "").replaceAll("，", ",");

                if(text.isBlank()){
                    return;
                }

                try {
                    String json = IOUtil.readInputStream(DownloadUtil.openUrl(Constants.NBNHHSH_API, "POST", "text="+ text));
                    //System.out.println("json ==> " + json);
                    JSONArray result = JSONArray.parseArray(json);

                    if(result.isEmpty()){
                        return;
                    }

                    StringBuilder msg = new StringBuilder();
                    for(Object obj : result){
                        JSONObject jsonObj = (JSONObject) obj;
                        msg.append(jsonObj.get("name")).append("可能是：");
                        JSONArray array = jsonObj.getJSONArray("trans");
                        for(Object o : array){
                            msg.append(o.toString()).append("，");
                        }
                        msg.append("\n");
                    }

                    event.getSubject().sendMessage(new PlainText(msg.substring(0, msg.length()-2)));

                } catch (Exception e) {
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
