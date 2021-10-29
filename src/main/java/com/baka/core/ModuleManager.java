package com.baka.core;

import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 模块管理器
 */
public class ModuleManager {

    MiraiLogger logger = MiraiLogger.create(ModuleManager.class.getName());

    private final String modulePackage = "com.baka.modules.";

    private static final Map<String, String> modules = new HashMap<>(); // 全部模块

    static {
        // 初始化模块
        modules.put("色图", "ColorImage");
        modules.put("PID查询", "PidQuery");
        modules.put("猫图", "CatImage");
        modules.put("文本转语音", "Text2Voice");
        modules.put("能不能好好说话", "GoodGoodSay");
        modules.put("人生重开", "Remake");
    }

    private ModuleManager(){}

    private static final ModuleManager INSTANCE = new ModuleManager();

    public static ModuleManager getInstance(){
        return INSTANCE;
    }

    public void init(){
        try {
            for (String module : modules.keySet()) {
                Class<?> clazz = Class.forName(modulePackage + modules.get(module));
                Method method = clazz.getDeclaredMethod("startListener");
                Object o = clazz.getDeclaredMethod("getInstance").invoke(new Object());
                method.invoke(o);
                logger.info("模块" + module + "启用成功！");
            }
        }catch (Exception e){
            logger.info("模块初始化失败！");
            e.printStackTrace();
        }
        // 加载管理监听
        this.manageListener();
    }

    public void load(String moduleName){
        logger.info("模块" + moduleName + "加载成功！");
    }

    public boolean onable(String moduleName){
        try {
            Class<?> clazz = Class.forName(modulePackage + moduleName);
            Method method = clazz.getDeclaredMethod("startListener");
            Object o = clazz.getDeclaredMethod("getInstance").invoke(new Object());
            method.invoke(o);
            logger.info("模块" + moduleName + "启用成功！");
            return true;
        }catch (Exception e){
            logger.info("模块" + moduleName + "启用失败！");
            e.printStackTrace();
            return false;
        }
    }

    public boolean disable(String moduleName){
        try {
            Class<?> clazz = Class.forName(modulePackage + moduleName);
            Method method = clazz.getDeclaredMethod("stopListener");
            Object o = clazz.getDeclaredMethod("getInstance").invoke(new Object());
            method.invoke(o);
            logger.info("模块" + moduleName + "禁用成功！");
            return true;
        }catch (Exception e){
            logger.info("模块" + moduleName + "禁用失败！");
            e.printStackTrace();
            return false;
        }
    }

    private void manageListener(){
        GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, event -> {
            String content = event.getMessage().contentToString();
            if(content.startsWith("启用")) {
                String moduleName = content.split(" ")[1];
                if (modules.containsKey(moduleName)) {
                    if (onable(modules.get(moduleName))) {
                        event.getSubject().sendMessage("启用" + moduleName + "成功！");
                    } else {
                        event.getSubject().sendMessage("启用" + moduleName + "失败！");
                    }
                } else {
                    event.getSubject().sendMessage("找不到模块：" + moduleName);
                }
            }else if(content.startsWith("禁用")){
                String moduleName = content.split(" ")[1];
                if (modules.containsKey(moduleName)) {
                    if (disable(modules.get(moduleName))) {
                        event.getSubject().sendMessage("禁用" + moduleName + "成功！");
                    } else {
                        event.getSubject().sendMessage("禁用" + moduleName + "失败！");
                    }
                } else {
                    event.getSubject().sendMessage("找不到模块：" + moduleName);
                }
            }else if(content.startsWith("加载")){
                String moduleName = content.split(" ")[1];
                load(moduleName);
            }
        });
    }

}
