package com.example.noticebot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements HttpCallback{
    private final String TAG = "TAGSignupActivity";
    EditText EditText_NEWemail, EditText_NEWpassword, EditText_NEWpasswordcheck;
    Button Button_Signup;

    //아이디 패턴: 숫자만으로 10자
    public static final String idPattern = "^[0-9]{10}+$";
    //비밀번호 패턴: 영문, 숫자, 특수문자 조합으로 6~20자
    public static final String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{6,20}$";



    //임시 ID체크
    String[] emailCheck = {"ijk", "abc", "xyz"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // R (Resource)의 id인 ~~를 볼러와 변수로 설정하는 것. (앞과 뒤가 똑같은 변수명이지만, 지역적으로 할당되어 있으므로 문제 없음)
        EditText_NEWemail = findViewById(R.id.EditText_NEWemail);
        EditText_NEWpassword = findViewById(R.id.EditText_NEWpassword);
        EditText_NEWpasswordcheck = findViewById(R.id.EditText_NEWpasswordcheck);
        Button_Signup = findViewById(R.id.Button_SignupConfirmed);



        // 1. 값을 가져온다
        // 2. 클릭을 감지한다
        // 3. 1번의 값을 다음 액티비티로 넘긴다.

        //아이디 비밀번호 포맷 체크
        Button_Signup.setEnabled(false);
        signupFormatCheck(Button_Signup, EditText_NEWemail, EditText_NEWpassword);

        Button_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = EditText_NEWemail.getText().toString();
                String newPassword = EditText_NEWpassword.getText().toString();
                String newPasswordCheck = EditText_NEWpasswordcheck.getText().toString();

//                if(signupFormatCheck(Button_Signup, EditText_NEWemail, EditText_NEWpassword)){
//                    Log.d(TAG, "정보 수정됨");
//                }
                //비밀번호가 불일치 할 경우
                if(!newPassword.equals(newPasswordCheck)) {
                    alertInvalidPasswordCheck();
                }else{
                    signUp(newEmail, newPassword);
                }


            }
        });
    }

    private void alertInvalidPasswordCheck(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignupActivity.this);
        alertDialogBuilder.setTitle("비밀번호 불일치");
        alertDialogBuilder
                .setMessage("비밀번호를 일치시켜주시기 바랍니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        moveToLoginActivity();
    }

    private void moveToLoginActivity(){
        alertSignupSucceed();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void alertSignupSucceed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignupActivity.this);
        alertDialogBuilder.setTitle("회원가입 완료");
        alertDialogBuilder
                .setMessage("생성된 아이디로 로그인 해주시기 바랍니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // ID, PW 포맷에 대한 체크
    private void signupFormatCheck(final Button button_Signup, final EditText newEmail, final EditText newPassword) {
        Log.d(TAG,"signupFormatCheck 실행됨");

        newEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (  !Pattern.matches(idPattern, s.toString()) ) {
                    newEmail.setError("ID는 10자리 학번을 이용해서 가입해주세요.");
                }
                else {
                    button_Signup.setEnabled(true);
                }
            }
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ( !Pattern.matches(pwPattern, s.toString()) ) {
                    newPassword.setError("비밀번호는 6자 이상, 20자 이하의 영문, 숫자, 특수문자의 조합이어야 합니다.");
                }
                else {
                    button_Signup.setEnabled(true);
                }
            }
        });

    }

}








//TODO: 해당 코드는 callback함수에서 구현해야함.
////아이디가 중복될 경우
//                else if(emailCheck(NEWemail)) {
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignupActivity.this);
//                        alertDialogBuilder.setTitle("아이디 중복됨");
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
//        alertDialogBuilder.setTitle("회원가입 완료");
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