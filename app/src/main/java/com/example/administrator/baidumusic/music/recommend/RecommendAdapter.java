package com.example.administrator.baidumusic.music.recommend;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.music.recommend.hotalbum.HotAlbumAdapter;
import com.example.administrator.baidumusic.music.recommend.listrecommed.ListRecommedAdapter;
import com.example.administrator.baidumusic.music.recommend.newcd.NewCdAdapter;
import com.example.administrator.baidumusic.music.recommend.slideshow.SlideShowAdapter;
import com.example.administrator.baidumusic.music.recommend.todaymusic.TodayMusicAdapter;
import com.example.administrator.baidumusic.tools.CustomPoint;
import com.example.administrator.baidumusic.tools.SingleVolley;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class RecommendAdapter extends RecyclerView.Adapter {
    private Context context;
    private RecommendBean recommendBean;
    private List<CustomPoint> pointList;
    private SlideShowAdapter slideShowAdapter;
    private CustomPoint point;
    private SlideShowViewHolder slideShowViewHolder;

    public void setRecommendBean(RecommendBean recommendBean) {
        this.recommendBean = recommendBean;
        notifyDataSetChanged();
    }

    public RecommendAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            // 轮播图
            case 0: {
                View view = LayoutInflater.from(context).inflate(R.layout.slide_show_recommend, parent, false);
                holder = new SlideShowViewHolder(view);
                return holder;
            }
            // 音乐导航 MusicNavigation
            case 1: {
                View view = LayoutInflater.from(context).inflate(R.layout.entry_recommend, parent, false);
                holder = new MusicNavigationViewHolder(view);
                return holder;
            }
            // 歌单推荐 listRecommed
            case 2: {
                View view = LayoutInflater.from(context).inflate(R.layout.list_recommed, parent, false);
                holder = new ListRecommedViewHolder(view);
                return holder;
            }
            // 新碟上架 newCD
            case 3: {
                View view = LayoutInflater.from(context).inflate(R.layout.new_cd, parent, false);
                holder = new NewCDViewHolder(view);
                return holder;
            }
            // 热销专辑 hotAlbum
            case 4: {
                View view = LayoutInflater.from(context).inflate(R.layout.hot_album, parent, false);
                holder = new HotAlbumViewHolder(view);
                return holder;
            }
            // 场景电台 scene
            case 5: {
                //View view = LayoutInflater.from(context).inflate(R.layout.)
            }
            // 今日推荐歌曲
            case 6: {
                View view = LayoutInflater.from(context).inflate(R.layout.today_recommed_music,
                        parent, false);
                holder = new ToadyMusicViewHolder(view);
                return holder;
            }

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        switch (type) {
            // 轮播图
            case 0: {
                Log.d("RecommendAdapter", "进入case 0 ");

                slideShowViewHolder = (SlideShowViewHolder) holder;
                slideShowAdapter = new SlideShowAdapter(context);

                int slideShowSize = recommendBean.getResult().getFocus().getResult().size();
                ArrayList<String> imgurlList = new ArrayList<>();
                String imgurl;
                for (int i = 0; i < slideShowSize; i++) {
                    imgurl = recommendBean.getResult().getFocus().getResult().get(i).getRandpic();
                    imgurlList.add(imgurl);

                }

                slideShowViewHolder.vpRecommend.setAdapter(slideShowAdapter);

                slideShowAdapter.setStringList(imgurlList);
                // 点的初始化
                pointList = new ArrayList<>();
                for (int i = 0; i < slideShowAdapter.getImageCount(); i++) {
                    point = new CustomPoint(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                            ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    point.setLayoutParams(new LinearLayout.LayoutParams(40,40));

                    pointList.add(point);

                    slideShowViewHolder.ll.addView(point,layoutParams);

                }

                MyCounter myCounter = new MyCounter(Long.MAX_VALUE, 3000);
                 myCounter.start();

                // 轮播图的监听
                slideShowViewHolder.vpRecommend.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        int currentPage = position % slideShowAdapter.getImageCount();
                        for (int i = 0; i < pointList.size(); i++) {
                            pointList.get(i).setSelected(false);
                        }
                        pointList.get(currentPage).setSelected(true);

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                break;
            }
            // 音乐导航
            case 1: {
                MusicNavigationViewHolder musicholder = (MusicNavigationViewHolder) holder;
                String[] imgurl = new String[4];
                // 将图标的url存储
                for (int i = 0; i < 4; i++) {
                    imgurl[i] = recommendBean.getResult().getEntry().getResult().get(i).getIcon();
                }
                SingleVolley.getInstance().getImage(imgurl[0], musicholder.singer);
                SingleVolley.getInstance().getImage(imgurl[1], musicholder.musicType);
                SingleVolley.getInstance().getImage(imgurl[2], musicholder.transceiver);
                SingleVolley.getInstance().getImage(imgurl[3], musicholder.vip);
                break;
            }
            // 歌单推荐
            case 2: {

                ListRecommedViewHolder listholder = (ListRecommedViewHolder) holder;

                ListRecommedAdapter adapter = new ListRecommedAdapter(context);
                GridLayoutManager manager = new GridLayoutManager(context, 3);
                adapter.setBean(recommendBean);
                listholder.rvListRecommend.setAdapter(adapter);
                listholder.rvListRecommend.setLayoutManager(manager);
                break;
            }
            // 新碟上架 new cd

            case 3: {
                NewCDViewHolder cdViewHolder = (NewCDViewHolder) holder;
                NewCdAdapter adapter = new NewCdAdapter(context);
                adapter.setBean(recommendBean);
                cdViewHolder.newcd.setAdapter(adapter);
                GridLayoutManager manager = new GridLayoutManager(context, 3);
                cdViewHolder.newcd.setLayoutManager(manager);
                break;

            }
            // 热销专辑
            case 4: {
                HotAlbumViewHolder hotViewHolder = (HotAlbumViewHolder) holder;
                HotAlbumAdapter adapter = new HotAlbumAdapter(context);
                GridLayoutManager manager = new GridLayoutManager(context, 3);
                adapter.setBean(recommendBean);
                hotViewHolder.hotAlbum.setAdapter(adapter);
                hotViewHolder.hotAlbum.setLayoutManager(manager);
                break;
            }

            // 今日推荐歌曲
            case 6: {
                ToadyMusicViewHolder todayViewholer = (ToadyMusicViewHolder) holder;
                TodayMusicAdapter adapter = new TodayMusicAdapter(context);

                todayViewholer.todayMusic.setAdapter(adapter);
            }

        }

    }


    @Override
    public int getItemCount() {
//        return recommendBean.getModule().size();12
        return recommendBean == null ? 0 : 1;
    }

    /**
     * 轮播图的ViewHolder
     * 种类0
     */
    class SlideShowViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll;
        private ViewPager vpRecommend;

        public SlideShowViewHolder(View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.item_slide_ll);
            vpRecommend = (ViewPager) itemView.findViewById(R.id.vp_recommend);

        }
    }

    /**
     * 音乐导航的ViewHolder
     */
    private class MusicNavigationViewHolder extends RecyclerView.ViewHolder {

        private final ImageView singer;
        private final ImageView musicType;
        private final ImageView transceiver;
        private final ImageView vip;

        public MusicNavigationViewHolder(View view) {
            super(view);
            singer = (ImageView) view.findViewById(R.id.iv_singer);
            musicType = (ImageView) view.findViewById(R.id.iv_musictype);
            transceiver = (ImageView) view.findViewById(R.id.iv_transceiver);
            vip = (ImageView) view.findViewById(R.id.iv_vip);
        }
    }

    /**
     * 歌单推荐的viewholder
     */
    private class ListRecommedViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerView rvListRecommend;

        public ListRecommedViewHolder(View view) {
            super(view);
            rvListRecommend = (RecyclerView) view.findViewById(R.id.rv_list_recommend);

        }
    }

    /**
     * 新碟上架的viewholder
     */
    private class NewCDViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerView newcd;

        public NewCDViewHolder(View view) {
            super(view);
            newcd = (RecyclerView) view.findViewById(R.id.rv_new_cd);
        }
    }

    /**
     * 热销专辑的viewholder
     */
    private class HotAlbumViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView hotAlbum;

        public HotAlbumViewHolder(View view) {
            super(view);
            hotAlbum = (RecyclerView) view.findViewById(R.id.rv_hot_album);

        }
    }

    /**
     * 今日推荐歌曲
     */
    private class ToadyMusicViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerView todayMusic;

        public ToadyMusicViewHolder(View view) {
            super(view);
            todayMusic = (RecyclerView) view.findViewById(R.id.rv_today_recommend_music);
        }
    }

    private class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long time) {
            if (slideShowViewHolder.vpRecommend != null) {
                int num = slideShowViewHolder.vpRecommend.getCurrentItem();
                slideShowViewHolder.vpRecommend.setCurrentItem(num + 1);
            }
        }

        @Override
        public void onFinish() {

        }
    }
}
