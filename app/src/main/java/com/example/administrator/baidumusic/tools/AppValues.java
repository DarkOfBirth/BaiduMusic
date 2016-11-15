package com.example.administrator.baidumusic.tools;

/**
 * Created by dllo on 16/10/24.
 */
public final class AppValues {

    public static final String MUSIC_LIST_DETAIL_BEFORE = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedanInfo&from=ios&listid=";
    public static final String MUSIC_LIST_DETAIL_AFTER = "&version=5.2.3&from=ios&channel=appstore";


    public static final String MUSICTOP_SONGLIST_HEAD = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=";
    public static final String MUSICTOP_SONGLIST_END = "&format=json&offset=0&size=50&from=ios&fields=title,song_id,author,resource_type,havehigh,is_new,has_mv_mobile,album_title,ting_uid,album_id,charge,all_rate&version=5.2.1&from=ios&channel=appstore";

    public static String WELCOME_IMAGE_URL = "http://img1.gamedog.cn/2013/07/12/43-130G21509190.jpg";
    public static String MUSIC_LIST = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=xiaomi&operator=0&method=baidu.ting.ugcdiy.getChanneldiy&param=k8HEX%2FMB%2BzEmeSCCXylP3IVvDrRb1qarKPk5dP8%2F8oUWsIPOQWuCewyZVU6ysFrPpeyM4N9%2BSRUFxTXivsvVuSxNVR4oXV7RQFGFYDUzM2x7IDDMRNV9Ip%2B2LY31aM8s&timestamp=1477490631&sign=05477a31c8e40741d13e7733b2d0e00a";
    public static String TOP_LIST = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billCategory&format=json&from=ios&version=5.2.1&from=ios&channel=appstore";
    public static String VIDEO = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=xiaomi&operator=0&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=1&page" + "_num=1&page_size=20&query=%E5%85%A8%E9%83%A8";
    public static String RECOMMEND="http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=xiaomi&operator=0&method=baidu.ting.plaza.index&cuid=BB4CB967DAEF05B2F1B359FA3A90CA88";
    public static String MUSIC_LIST_LAST="http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=xiaomi&operator=0&method=baidu.ting.ugcdiy.getChanneldiy&param=kQr4Ajwx3qpKDoRbLTGWOwSe7SOYdQZiJrWR2GlQvPXxdYVwGSvpE8rERYmUzbsRDaciKgTlXBYl7c71wRsyilrU2hBZw5hja%2BU4BM7xYQFVqCF2ZykoOdIGqFkkuWBn&timestamp=1477490592&sign=de2f8026497500c3545d67002a147192";
    public static final String PLAY_SONG_HEAD = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid=";
    public static final String PLAY_SONG_END = "&_=1413017198449";
    public static final String MV_HEAD = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=xiaomi&operator=0&provider=11%2C12&method=baidu.ting.mv.playMV&format=json&mv_id=";
    public static final String MV_END = "&song_id=&definition=0";
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 0;
    public static final int STOP_STATE = -1;

    // PROGRESS_AUTO 自动改变播放进度
    // PROGRESS_HAND 手动改变播放进度
    public static final int PROGRESS_AUTO = 0;
    public static final int PROGRESS_HAND = 1;

    public static final int SINGLE_CIRCLE = 0;
    public static final int LIST_CIRCLE = 1;
    public static final int LIST_PLAY = 2;
    public static final int RANDOM_PLAY = 3;






}
