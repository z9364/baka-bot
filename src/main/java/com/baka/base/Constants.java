package com.baka.base;

public class Constants {

    public static String OS_NAME = System.getProperty("os.name");

    /* connect out time */

    public static int CONNECT_OUT_TIME = 5000; // 3秒
    /* 保存信息 */
    public static String SAVE_ROOT_PATH = "./data/download";
    public static String PIXIV_PATH = "/pixiv";
    public static String CAT_PATH = "/cat";
    public static String WALLPAPER_PATH = "/wallpaper";
    public static String VOICE_PATH = "/voice";

    /* 涩图 */
    public static String R18 = "2"; // 0-->关闭，1-->开启，2-->混合
    public static String LOLICON_URL = "https://api.lolicon.app/setu/v2?R18=" + Constants.R18;

    /* pid查询 */
    public static String PID_URL = "https://api.pixiv.cat/v1/generate";

    /* 壁纸 */
    public static String WALLPAPER_URL = "";

    /* P站代理 */
    public static String ORG_PIXIV_PERFIX = "https://i.pximg.net/";
    public static String PROXY_PIXIV_PERFIX = "https://pixiv.1159445878.workers.dev/";
    public static String PIXIV_CAT_PERFIX = "https://i.pixiv.cat/";

    /* 猫图 */
    public static String CAT_URL = "https://fox.ftqq.com/dict/dictlist/_gallery/cute/";
    public static int CAT_IMAGE_MAX_INDEX = 4520;

    /* QQ小冰ID */
    public static Long LITTEL_ICE_ID = 2854196306L;
    public static String LITTEL_ICE_NAME = "小冰";

    /* 百度翻译语音 */
    public static String BAIDU_YANYI = "https://fanyi.baidu.com/gettts";

    /* 能不能好好说话 */
    public static String NBNHHSH_API = "https://lab.magiconch.com/api/nbnhhsh/guess";

}
