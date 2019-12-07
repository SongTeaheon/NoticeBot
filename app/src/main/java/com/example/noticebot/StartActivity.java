package com.example.noticebot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    private TimerTask mTask;
    private Timer mTimer;
    private Intent intent;

    private final String TAG = "TAGLoginActivity";

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
    protected void onResume() {
        super.onResume();

        if(!checkInternetState()){
            Toast.makeText(StartActivity.this,
                    "현재 네트워크에 연결되어 있지 않습니다. 어플리케이션을 정상적으로 작동시키시려면 데이터 네트워크 혹은 와이파이에 연결해주시기 바랍니다.",Toast.LENGTH_LONG).show();
        }

    }




    void checkNewLogin() {
        if (checkInternetState()) {
            intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        } else if (SaveSharedPreference.getUserName(StartActivity.this).length() == 0) {
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

    }


    //인터넷 연결상태 체크
    private boolean checkInternetState() {
        int networkStatus = NetworkStatus.getConnectivityStatus(getApplicationContext());
        boolean ableToLogin = (networkStatus == NetworkStatus.TYPE_MOBILE || networkStatus == NetworkStatus.TYPE_WIFI);

//
//        if(networkStatus != NetworkStatus.TYPE_MOBILE && networkStatus != NetworkStatus.TYPE_WIFI) {
//            ableToLogin = false;
//        } else{
//            ableToLogin = true;
//        }

        return ableToLogin;
    }

}
