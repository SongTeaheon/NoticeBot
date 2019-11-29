package com.example.noticebot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    private TimerTask mTask;
    private Timer mTimer;

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
        //새로 로그인  하는 경우
//        if(1 == 1) { //로그인 확인하는 오퍼레이션 들어가야 함
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
//        }
//        //로그인 되어있는 경우
//        else {
//            Intent intent = new Intent(StartActivity.this, MainActivity.class);
//            startActivity(intent);
//        }
    }
}
