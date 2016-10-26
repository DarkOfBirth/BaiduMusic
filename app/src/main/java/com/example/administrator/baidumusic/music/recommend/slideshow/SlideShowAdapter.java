package com.example.administrator.baidumusic.music.recommend.slideshow;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.baidumusic.tools.SingleVolley;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/10/25.
 */

public class SlideShowAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> stringList;
    private ArrayList<ImageView> views;

    public void setStringList(ArrayList<String> stringList) {
        this.stringList = stringList;
        Log.d("SlideShowAdapter", "stringList.size():" + stringList.size());

        views = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            Log.d("SlideShowAdapter", stringList.get(i));
            ImageView imageView = new ImageView(context);
            SingleVolley.getInstance().getImage(stringList.get(i), imageView);
            views.add(imageView);
        }
        notifyDataSetChanged();
    }

    public SlideShowAdapter(Context context) {
        Log.d("SlideShowAdapter", "进入轮播图");
        this.context = context;
    }

    @Override
    public int getCount() {
        return stringList == null ? 0 : stringList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {

            container.addView(views.get(position % views.size()));
        } catch (Exception e) {
        }
        return container.getChildAt(position);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("SlideShowAdapter", "position:" + position);
       //  container.removeView(views.get(position));

    }


}
