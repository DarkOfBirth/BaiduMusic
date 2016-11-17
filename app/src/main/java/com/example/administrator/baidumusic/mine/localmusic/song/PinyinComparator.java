package com.example.administrator.baidumusic.mine.localmusic.song;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *  //实例化汉字转拼音类
 *
 */
public class PinyinComparator implements Comparator<LocalMusicBean> {

	public int compare(LocalMusicBean o1, LocalMusicBean o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
