package com.baka.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class IOUtil {

    public static String readInputStream(InputStream inputStream) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        byte[] by = new byte[2048];
        int tem;
        while((tem = inputStream.read(by)) != -1){
            stringBuilder.append(new String(by, 0, tem, StandardCharsets.UTF_8));
        }
        inputStream.close();
        return stringBuilder.toString();
    }



}
