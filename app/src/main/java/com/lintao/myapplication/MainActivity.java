package com.lintao.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        公有变量
        final TextView contentview = findViewById(R.id.content);
        final TextView title = findViewById(R.id.title);
        Button pre = findViewById(R.id.previous);
        final Button control = findViewById(R.id.control);
        Button next = findViewById(R.id.next);

        Index.context = getApplicationContext();

//        获取txt行数
        int line = 0;
        try {
            line = readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        将txt存入字符串数组
        final String[] question = new String[line];
        try {
            FileReader fr = new FileReader("/sdcard/content.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            int count = 0;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                question[count] = str;
                count++;
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//文本内容初始化
        contentview.setText(question[Index.index].replace("\\n", "\n"));
        title.setText(String.valueOf(Index.index+1));

//        音频初始化
        Index.Player(String.valueOf(Index.index+1));

//        上一个按钮
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Index.index==0)
                    Toast.makeText(MainActivity.this, "已经是第一个",Toast.LENGTH_SHORT).show();
                else{
                    Index.index--;
                    contentview.setText(question[Index.index].replace("\\n", "\n"));
                    title.setText(String.valueOf(Index.index+1));
                    Index.player.reset();
                    Index.Player(String.valueOf(Index.index+1));

                }
            }
        });


//        播放/暂停
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(control.getText().equals("暂停")){
                    Index.player.pause();
                    control.setText("播放");
                }else{
                    Index.player.start();
                    control.setText("暂停");
                }
            }
        });


//        下一个按钮
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Index.index==(question.length-1))
                    Toast.makeText(MainActivity.this, "已经是最后一个",Toast.LENGTH_SHORT).show();
                else{
                    Index.index++;
                    contentview.setText(question[Index.index].replace("\\n", "\n"));
                    title.setText(String.valueOf(Index.index+1));
                    Index.player.reset();
                    Index.Player(String.valueOf(Index.index+1));

                }
            }
        });

    }

//    获取txt行数的方法
    public static int readFile() throws FileNotFoundException {
        int count=0;
        File file = new File("/sdcard/content.txt");
        FileInputStream fis = new FileInputStream(file);
        Scanner scanner = new Scanner(fis);
        while(scanner.hasNextLine()){
            scanner.nextLine();
            count++;
        }
        return count;
    }

}