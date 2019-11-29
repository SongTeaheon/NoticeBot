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
        //TODO: sharedpreference 응용해서 로그인 상태 체크하는 구문이 추가되어야 한다.

        //실제로 상용화 할 때
//        Intent intent = new Intent(StartActivity.this, LoginActivity.class);

        //테스트 위해서 로그인 생략할 때
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
