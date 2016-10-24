package com.example.administrator.baidumusic.tools;

import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.main.MyApp;

/**
 *
 * Created by dllo on 16/10/24.
 */
public class SingleVolley {
    private static SingleVolley singleVolley;
    private RequestQueue requestQueue ;
    private ImageLoader imageLoader;
    private  SingleVolley() {
        requestQueue = Volley.newRequestQueue(MyApp.getmContext());
        imageLoader = new ImageLoader(requestQueue, new MemoryCache());
    }
    public static SingleVolley getInstance(){
        if(singleVolley == null) {
            synchronized (SingleVolley.class){
                if (singleVolley == null){
                    singleVolley = new SingleVolley();
                }
            }
        }
        return singleVolley;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

// 图片
    public void getImage(String url, ImageView imageView){
        imageLoader.get(url, ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher,R.mipmap.ic_launcher));
    }
}
