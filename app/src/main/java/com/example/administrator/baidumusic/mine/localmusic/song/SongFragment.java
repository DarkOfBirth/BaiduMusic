package com.example.administrator.baidumusic.mine.localmusic.song;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.base.MyApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dllo on 16/11/1.
 */
public class SongFragment extends BaseFragment {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private TextView mClearEditText;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private List<LocalMusicBean> LocalMusicBeansList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private TextView total;
    private View footView;

    @Override
    protected int getLayout() {
        return R.layout.frag_song;
    }

    @Override
    protected void initView() {
        total =bindView(R.id.total_lm);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = bindView(R.id.sidrbar);
        dialog = bindView(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView = bindView(R.id.country_lvcountry);

        // 给sortListView 添加头尾布局
        //  TODO: 16/11/1 添加头尾布局

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                Toast.makeText(MyApp.getmContext(), ((LocalMusicBean)adapter.getItem(position-1)).getName(), Toast.LENGTH_SHORT).show();

            }
        });
        // 头布局
        View headView = LayoutInflater.from(mContext).inflate(R.layout.head_local_song, null);
        sortListView.addHeaderView(headView, null, false);



        ArrayList<LocalMusicBean> lists = new ScanMusic().query(MyApp.getmContext());
        LocalMusicBeansList = filledData(lists);
        // 尾布局
        footView = LayoutInflater.from(mContext).inflate(R.layout.foot_local_song, null);
        TextView num = (TextView) footView.findViewById(R.id.num_local_music_tv);
        num.setText("共有" + LocalMusicBeansList.size() + "首歌曲");
        sortListView.addFooterView(footView,null,false);
        // 根据a-z进行排序源数据
        Collections.sort(LocalMusicBeansList, pinyinComparator);
        adapter = new SortAdapter(MyApp.getmContext(), LocalMusicBeansList);
        sortListView.setAdapter(adapter);



    }

    @Override
    protected void initData() {

    }
    /**
     * 为ListView填充数据 给sortLetter字段填充值
     * @param datas
     * @return
     */
    private List<LocalMusicBean> filledData(ArrayList<LocalMusicBean> datas) {

        for (int i = 0; i < datas.size(); i++) {
            // 汉字转换为拼音
            String pinyin = characterParser.getSelling(datas.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                datas.get(i).setSortLetters(sortString.toUpperCase());
            }else{
                datas.get(i).setSortLetters("#");
            }


        }

        return datas;
    }

    public void search(String filterStr) {
        //List<SortModel> filterDateList = new ArrayList<SortModel>();
        List<LocalMusicBean> filterDateList = new ArrayList<>();
        if(TextUtils.isEmpty(filterStr)){
            filterDateList = LocalMusicBeansList;
        }else{
            filterDateList.clear();
            for(LocalMusicBean LocalMusicBean : LocalMusicBeansList){
                String name = LocalMusicBean.getName();
                if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    filterDateList.add(LocalMusicBean);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        TextView num = (TextView) footView.findViewById(R.id.num_local_music_tv);
        num.setText("共有" + filterDateList.size() + "首歌曲");

        sortListView.addFooterView(footView,null,false);
        adapter.updateListView(filterDateList);
    }
}
