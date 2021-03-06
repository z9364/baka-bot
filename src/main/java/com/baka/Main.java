package com.baka;


import com.baka.core.ModuleManager;
import com.baka.utils.DownloadUtil;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin {

    public static Main INSTANCE = new Main();

    private Main(){
        super(new JvmPluginDescriptionBuilder("com.baka.Main", "0.1").author("z9364").name("ba~ ka~ bot").info("一个自用qq bot").build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        ModuleManager.getInstance().init();
        DownloadUtil.init();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
