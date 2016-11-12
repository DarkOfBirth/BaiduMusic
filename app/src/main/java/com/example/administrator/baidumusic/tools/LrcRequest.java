package com.example.administrator.baidumusic.tools;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/11/11.
 */

public class LrcRequest extends Request<java.lang.String> {
    private final Response.Listener<java.lang.String> mListener;


    public LrcRequest(int method, java.lang.String url, Response.Listener<java.lang.String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }


    public LrcRequest(java.lang.String url, Response.Listener<java.lang.String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(java.lang.String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<java.lang.String> parseNetworkResponse(NetworkResponse response) {
        String parsed = "";
        InputStream inputStream = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputStream = new ByteArrayInputStream(response.data);
            inputReader = new InputStreamReader( inputStream );
            bufReader = new BufferedReader(inputReader);
            String line="";
            String result="";
            while((line = bufReader.readLine()) != null){
                if(line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
            parsed = result;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtil.closeAll(bufReader,inputReader,inputStream);
        }

//        try {
//            parsed = new java.lang.String(response.data, HttpHeaderParser.parseCharset(response.headers));
//        } catch (UnsupportedEncodingException e) {
//            parsed = new java.lang.String(response.data);
//        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
