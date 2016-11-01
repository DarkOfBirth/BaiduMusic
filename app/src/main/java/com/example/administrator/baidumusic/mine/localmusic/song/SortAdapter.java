package com.example.administrator.baidumusic.mine.localmusic.song;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;

import java.util.List;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<LocalMusicBean> list = null;
	private Context mContext;

	public SortAdapter(Context mContext, List<LocalMusicBean> list) {
		this.mContext = mContext;
		this.list = list;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<LocalMusicBean> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final LocalMusicBean mContent = list.get(position);
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.item_song, arg2, false);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		Log.d("SortAdapter", "viewHolder:" + viewHolder.tvTitle);
		viewHolder.tvTitle.setText(this.list.get(position).getName());
		viewHolder.tvArtist.setText(String.format("%s - %s", list.get(position).getArtist(), list.get(position).getAlbum()));
		return view;

	}



	private class ViewHolder {
		private TextView tvLetter;
		private TextView tvTitle;
		private TextView tvArtist;

		public ViewHolder(View view) {
			tvTitle = (TextView) view.findViewById(R.id.title_lm);
			tvLetter = (TextView) view.findViewById(R.id.catalog);
			tvArtist = (TextView) view.findViewById(R.id.artist_album_lm);
		}
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {

		return list.get(position).getSortLetters().charAt(0);
	}


	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		// 遍历
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 *
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}