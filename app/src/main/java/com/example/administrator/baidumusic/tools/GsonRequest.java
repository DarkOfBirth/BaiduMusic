package com.example.administrator.baidumusic.tools;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by dllo on 16/10/24.
 */

public class GsonRequest<T> extends Request<T> {
    private Class<T> mClass;
    private Response.Listener<T> listener;
    private Gson gson;
    public GsonRequest(int method, Class<T> tClass, String url, Response.Listener<T> listener,  Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);
        this.listener = listener;
        this.mClass = tClass;
        gson = new Gson();

    }
    public GsonRequest(Class<T> tClass, String url, Response.Listener<T> listener,  Response.ErrorListener errorlistener) {
       this(Method.GET, tClass,url,listener,errorlistener);

    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;// 请求成功的字符串
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        T t = gson.fromJson(parsed,mClass);
        return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
