package com.baka.base;

import java.util.List;

/**
 * 消息监听
 */
public interface Subscribe {

    void startListener();

    void stopListener();

    default boolean reg(List<String> regs, String text){
        for(String reg : regs){
            if(text.matches(reg)){
                return true;
            }
        }
        return false;
    }

}
