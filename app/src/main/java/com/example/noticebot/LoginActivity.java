package com.example.noticebot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements HttpCallback{
    private final String TAG = "TAGLoginActivity";

    EditText EditText_Id, EditText_Password;
    Button Button_Login, Button_Signup;
    DBHelper dbHelper;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //토큰을 위한 dbHelper
        if(dbHelper == null){
            Log.d(TAG, "new DBHelper ");
            dbHelper = new DBHelper(this, "APP_DB", null, 1);
        }

        EditText_Id = findViewById(R.id.EditText_Id);
        EditText_Password = findViewById(R.id.EditText_Password);
        Button_Login = findViewById(R.id.Button_Login);
        Button_Signup = findViewById(R.id.Button_Signup);

        //로그인 버튼
        Button_Login.setClickable(true);
        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = EditText_Id.getText().toString();
                String password = EditText_Password.getText().toString();
                Log.d(TAG, "name :" + name);
                if(name.isEmpty()|| password.isEmpty()){
                    Utils.alertFunc(LoginActivity.this, "값 없음", "아이디와 비밀번호를 모두 넣어주세요");
                }else {
                    login(name, password);
                    SaveSharedPreference.setUserName(LoginActivity.this, EditText_Id.getText().toString());
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
                Utils.sendTokenToServer(name, dbHelper.getToken());
                moveToMainActivity();
            }else{
                Utils.alertFunc(LoginActivity.this, "로그인 실패", "id와 pw를 확인해주세요");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        alertExit();
    }

    //종료 경고창
    private void alertExit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setTitle("프로그램 종료");
        alertDialogBuilder
                .setMessage("프로그램을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                moveTaskToBack(true);
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
