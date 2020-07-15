package com.lintao.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

public class Index {
    public static int index = 0;
    static MediaPlayer player = new MediaPlayer();
    public static Context context;

    public static void Player(String filename){
        try{
            player.setDataSource("/sdcard/aaaa/" + filename + ".mp3");
            player.prepare();
        }catch(Exception e){
//            e.printStackTrace();
            Toast.makeText(context, "缺少音频文件",Toast.LENGTH_SHORT).show();
        }
        player.setLooping(true);
        player.start();
    }
}
