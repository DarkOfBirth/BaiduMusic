package com.example.administrator.baidumusic.music.musiclist.musiclistdetail;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.base.MyApp;
import com.example.administrator.baidumusic.databean.MusicListDetailBean;
import com.example.administrator.baidumusic.messageevent.ClearEvent;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.FastBlur;
import com.example.administrator.baidumusic.tools.GsonRequest;
import com.example.administrator.baidumusic.tools.SingleVolley;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/11/5.
 */
public class MusicListDetailFragment extends BaseFragment implements View.OnClickListener {
    private MusicListDetailBean result;
    private TextView title;
    private ImageView back;
    private ImageView center;
    private ImageView bg;
    private TextView num;
    private TextView listenNum;
    private View view;
    private RecyclerView rv;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private MusicListDetailAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_musiclist_detail;
    }

    @Override
    protected void initView() {
        title = bindView(R.id.title_musiclist_detail);
        back = bindView(R.id.back_musiclist_detail);
        bg = bindView(R.id.bg_musiclist_detail);
        center = bindView(R.id.center_musiclist_detail);
        num = bindView(R.id.num_musiclist_detail);
        listenNum = bindView(R.id.listen_num_musiclist_detail);
        view = bindView(R.id.content_musiclist_detail);
        rv = bindView(view, R.id.rv_musiclist_detail);
        back.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        String list_id = getArguments().getString("list_id");
        String newUrl = AppValues.MUSIC_LIST_DETAIL_BEFORE + list_id + AppValues.MUSIC_LIST_DETAIL_AFTER;
        adapter = new MusicListDetailAdapter();
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApp.getmContext());
        rv.setLayoutManager(layoutManager);
        Log.d("MusicListDetailFragment", "test");
        getData(newUrl);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ClearEvent event) {

       adapter.setLoad(event.isFlag());
    }

    private void getData(String newUrl) {
        GsonRequest<MusicListDetailBean> request = new GsonRequest<MusicListDetailBean>(MusicListDetailBean.class, newUrl, new Response.Listener<MusicListDetailBean>() {
            @Override
            public void onResponse(MusicListDetailBean response) {
                setViewData(response);
                adapter.setMusicListDetailBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingleVolley.getInstance().getmRequestQueue().add(request);
    }

    // 给上半部分赋值, 即背景, 图片
    private void setViewData(MusicListDetailBean response) {
        title.setText(response.getTitle());
        num.setText(response.getContent().size() + "首歌");
        listenNum.setText(response.getListenum());
        SingleVolley.getInstance().getImage(response.getPic_300(), center);

        SingleVolley.getInstance().getImage(response.getPic_w700(), new SingleVolley.GetBitmap() {
            @Override
            public void onGetBitmap(Bitmap bitmap) {

                blur(bitmap, bg);

                Log.d("MusicListDetailFragment", "bmp:" + bitmap);

            }
        });
        ;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_musiclist_detail:
                getActivity().onBackPressed();
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void blur(final Bitmap bkg, final View view) {

        final float radius = 20;
        final Bitmap overlay = bkg.copy(Bitmap.Config.ARGB_8888, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                FastBlur.doBlur(overlay, (int) radius, true, new FastBlur.BitmapFastBlur() {
                    @Override
                    public void transerBitmap(final Bitmap bitmap) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (view instanceof ImageView) {

                                    ImageView imageView = (ImageView) view;
                                    Log.d("Sysout", "bitmap.getWidth():" + bitmap.getWidth());
                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                    Log.d("Sysout", "imageView.getWidth():" + imageView.getWidth());

                                    imageView.setImageBitmap(bitmap);
                                }
                            }
                        });
                    }
                });


            }
        }).start();

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
