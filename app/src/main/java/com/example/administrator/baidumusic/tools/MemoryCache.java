package com.example.administrator.baidumusic.tools;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/10/24.
 */
public class MemoryCache implements ImageLoader.ImageCache{

    private LruCache<String,Bitmap> mCache;
    public MemoryCache() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        mCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);

    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        bitmap.copy(Bitmap.Config.RGB_565,true);
        mCache.put(url,bitmap);

    }
}
