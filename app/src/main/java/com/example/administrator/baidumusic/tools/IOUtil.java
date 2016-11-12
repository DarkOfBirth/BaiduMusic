package com.example.administrator.baidumusic.tools;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/11.
 */

public class IOUtil {
    public static void closeAll(Closeable ... closeables){
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
