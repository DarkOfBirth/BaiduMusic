package com.example.administrator.baidumusic.tools;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.MyApp;

/**
 *
 * Created by dllo on 16/10/24.
 */
public class SingleVolley {
    private static SingleVolley singleVolley;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;
    private  SingleVolley() {
        mRequestQueue = Volley.newRequestQueue(MyApp.getmContext());
        imageLoader = new ImageLoader(mRequestQueue, new MemoryCache());
    }
    public static SingleVolley getInstance(){
        // 只在第一次进行加锁
        if(singleVolley == null) {
            // 参数:决定的是这把锁的作用范围
            synchronized (SingleVolley.class){
                if (singleVolley == null){
                    singleVolley = new SingleVolley();
                }
            }
        }
        return singleVolley;
    }


    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }

// 图片
    public void getImage(String url, ImageView imageView){
        imageLoader.get(url, ImageLoader.getImageListener(imageView,
                R.mipmap.ic_mymusic_picture_down_2,R.mipmap.ic_launcher));
    }
    // 获取bitmap
    public void getImage(String url,GetBitmap getBitmap){
       imageLoader.get(url,new BitmapLoader(getBitmap));

    }
    // 获取图片
    public class BitmapLoader implements ImageLoader.ImageListener{
        private GetBitmap getBitmap;

        public BitmapLoader(GetBitmap getBitmap) {
            this.getBitmap = getBitmap;
        }

        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            Bitmap bitmap = response.getBitmap();
            if(bitmap != null) {
                getBitmap.onGetBitmap(bitmap);
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            getBitmap.onGetBitmap(null);
        }
    }

    public interface GetBitmap{
        void onGetBitmap(Bitmap bitmap);
    }

    public void fun(){
        mRequestQueue.cancelAll(0);
    }


}

