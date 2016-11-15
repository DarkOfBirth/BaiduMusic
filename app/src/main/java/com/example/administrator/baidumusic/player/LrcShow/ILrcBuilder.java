package com.example.administrator.baidumusic.player.lrcshow;

/**
 * Created by Administrator on 2016/11/11.
 */

import java.util.List;

/**
 * 解析歌词，得到LrcRow的集合
 */
public interface ILrcBuilder {
    List<LrcRow> getLrcRows(String rawLrc);
}