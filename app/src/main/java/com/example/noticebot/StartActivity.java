package com.example.noticebot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    private TimerTask mTask;
    private Timer mTimer;
    private Intent intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);




                //로그인 상태 체크 (임시로 타이머 설정해놓음)
        mTask = new TimerTask() {
            @Override
            public void run() {
                checkNewLogin();
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 2000);

    }



    void checkNewLogin() {
       //실제로 상용화 할 때
        if(SaveSharedPreference.getUserName(StartActivity.this).length() == 0) {
            // call Login Activity
            intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            // Call Next Activity
            intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra("STD_NUM", SaveSharedPreference.getUserName(this).toString());
            startActivity(intent);
            this.finish();
        }

        //테스트 위해서 로그인 생략할 때
//        Intent intent = new Intent(StartActivity.this, MainActivity.class);
//        startActivity(intent);
    }
}
