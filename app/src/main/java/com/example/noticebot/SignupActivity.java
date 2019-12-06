package com.example.noticebot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements HttpCallback{
    private final String TAG = "TAGSignupActivity";
    EditText EditText_NewId, EditText_NewPassword, EditText_NewPasswordCheck;
    Button Button_SignupConfirmed;
    DBHelper dbHelper;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //토큰을 위한 dbHelper
        if(dbHelper == null){
            Log.d(TAG, "new DBHelper ");
            dbHelper = new DBHelper(this, "APP_DB", null, 1);
        }

        // R (Resource)의 id인 ~~를 볼러와 변수로 설정하는 것. (앞과 뒤가 똑같은 변수명이지만, 지역적으로 할당되어 있으므로 문제 없음)
        EditText_NewId = findViewById(R.id.EditText_NewId);
        EditText_NewPassword = findViewById(R.id.EditText_NewPassword);
        EditText_NewPasswordCheck = findViewById(R.id.EditText_NewPasswordCheck);
        Button_SignupConfirmed = findViewById(R.id.Button_SignupConfirmed);

        // 1. 값을 가져온다
        // 2. 클릭을 감지한다
        // 3. 1번의 값을 다음 액티비티로 넘긴다.
        Button_SignupConfirmed.setClickable(true);
        Button_SignupConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = EditText_NewId.getText().toString();
                String newPassword = EditText_NewPassword.getText().toString();
                String newPasswordCheck = EditText_NewPasswordCheck.getText().toString();

                if(!name.matches("[0-9]*") || name.length() != 10) {
                    Utils.alertFunc(SignupActivity.this, "아이디 형식 불일치", "학번(숫자 10자)로 가입하시기 바랍니다.");
                }else if(validatePassword(newPassword)){
                    Utils.alertFunc(SignupActivity.this, "비밀번호 형식 불일치", "비밀번호는 6~20자의 영문 대소문자, 숫자, 특수문자만의 조합으로 작성해주시기 바랍니다.");
                }else if(!newPassword.equals(newPasswordCheck)) {//비밀번호 불일치
                    Utils.alertFunc(SignupActivity.this, "비밀번호 확인 불일치", "비밀번호를 일치시켜주시기 바랍니다.");
                }else{
                    signUp(name, newPassword);
                }

            }
        });
    }

    private void signUp(String name, String password){
        Log.d(TAG, "sign up func start");
        try {
            JSONObject data = new JSONObject();
            data.put("type", "signup");
            data.put("name", name);
            data.put("pw", password);
            NetworkTask networkTask = new NetworkTask(this, data, "signup");
            networkTask.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JSONObject resultJson) {
        Log.d(TAG, "callback is called");
        Toast.makeText(SignupActivity.this, "신규 생성된 회원정보로 로그인합니다.", Toast.LENGTH_LONG).show();
        moveToMainActivity();
        try {
            String msg = resultJson.getString("message");
            if(msg.equals("record inserted.")){
                Utils.sendTokenToServer(name, dbHelper.getToken());
                moveToMainActivity();
            }else if(msg.equals("name already exist")){
                Utils.alertFunc(SignupActivity.this, "회원가입 실패", "해당 id가 이미 존재합니다.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void moveToMainActivity(){
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //비밀번호 체크 정규식
//    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,20}$"); // 6자리 ~ 20자리의 영문 대소문자, 숫자, 특수문자 일부
    public static boolean validatePassword(String pwStr) {
        String passwordPattern = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+).{6,20}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(pwStr);
        return !matcher.matches();
    }


}


////아이디가 중복될 경우
//                else if(emailCheck(NEWemail)) {
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignupActivity.this);
//                        alertDialogBuilder.setLink("아이디 중복됨");
//                        alertDialogBuilder
//                        .setMessage("다른 아이디를 사용하시기 바랍니다.")
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
//public void onClick(DialogInterface dialog, int whichButton){
//        dialog.cancel();
//        }
//        });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//        }
//        //위에 해당되지 않고 조건을 만족할 경우
//        else {
//
//        //개선 해야할점: 알림 창에서 얼렁뚱땅 넘어가는 것을 개선해야 함
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignupActivity.this);
//        alertDialogBuilder.setLink("회원가입 완료");
//        alertDialogBuilder
//        .setMessage("생성한 아이디로 로그인하십시오.")
//        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
//public void onClick(DialogInterface dialog, int whichButton){
//        dialog.cancel();
//        }
//        });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//
//        //Intent 형으로 정의된 intent의 putExtra 기능을 통해 email과 passsword를 전송한다.
//        intent.putExtra("email", NEWemail);
//        intent.putExtra( "password", NEWpassword);
//
//        //이를 전달해 그 다음 Activity를 시작한다.
//        startActivity(intent);
//        }