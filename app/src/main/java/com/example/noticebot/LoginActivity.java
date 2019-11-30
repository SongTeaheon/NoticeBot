package com.example.noticebot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements HttpCallback{
    private final String TAG = "TAGLoginActivity";

    EditText EditText_name, EditText_password;
    Button Button_Login, Button_Signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText_name = findViewById(R.id.EditText_email);
        EditText_password = findViewById(R.id.EditText_password);
        Button_Login = findViewById(R.id.Button_Login);
        Button_Signup = findViewById(R.id.Button_Signup);

        //로그인 버튼
        Button_Login.setClickable(true);
        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = EditText_name.getText().toString();
                String password = EditText_password.getText().toString();
                Log.d(TAG, "name :" + name);
                if(name.isEmpty()|| password.isEmpty()){
                    AlertUtils.alertFunc(LoginActivity.this, "값 없음", "아이디와 비밀번호를 모두 넣어주세요");
                }else {
                    login(name, password);
                    SaveSharedPreference.setUserName(LoginActivity.this, EditText_name.getText().toString());
                }
            }
        });


        //회원가입 버튼
        Button_Signup.setClickable(true);
        Button_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSignupActivity();
            }
        });
    }

    /*
    * login하는 함수. JsonObject로 ID, PW를 넣어서 서버로 보내준다.
    * */
    private void login(String name, String password){
        Log.d(TAG, "login func start");
        try {
            JSONObject data = new JSONObject();
            data.put("type", "login");
            data.put("name", name);
            data.put("pw", password);
            NetworkTask networkTask = new NetworkTask(this, data, "login");
            networkTask.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void moveToMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void moveToSignupActivity(){
        Intent intent = new Intent (LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void callback(JSONObject resultJson) {
        Log.d(TAG, "login callback called");

        try {
            String msg = resultJson.getString("message");
            if(msg.equals("login success")){
                moveToMainActivity();
            }else{
                AlertUtils.alertFunc(LoginActivity.this, "로그인 실패", "id와 pw를 확인해주세요");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
