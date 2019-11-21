package com.example.noticebot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText EditText_NEWemail, EditText_NEWpassword, EditText_NEWpasswordcheck;
    Button Button_Signup;

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


        //클릭 가능 여부를 설정
        Button_Signup.setClickable(true);
        Button_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getText()의 기본형은 string이 아니므로 (Editable) .toString()을 해줘야 함.
                String NEWemail = EditText_NEWemail.getText().toString();
                String NEWpassword = EditText_NEWpassword.getText().toString();
                String NEWpasswordcheck = EditText_NEWpasswordcheck.getText().toString();

                //비밀번호가 불일치 할 경우
                if(!NEWpassword.equals(NEWpasswordcheck)) {
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
                //아이디가 중복될 경우
                else if(emailCheck(NEWemail)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignupActivity.this);
                    alertDialogBuilder.setTitle("아이디 중복됨");
                    alertDialogBuilder
                            .setMessage("다른 아이디를 사용하시기 바랍니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int whichButton){
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                //위에 해당되지 않고 조건을 만족할 경우
                else {

                    //개선 해야할점: 알림 창에서 얼렁뚱땅 넘어가는 것을 개선해야 함
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignupActivity.this);
                    alertDialogBuilder.setTitle("회원가입 완료");
                    alertDialogBuilder
                            .setMessage("생성한 아이디로 로그인하십시오.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int whichButton){
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);

                    //Intent 형으로 정의된 intent의 putExtra 기능을 통해 email과 passsword를 전송한다.
                    intent.putExtra("email", NEWemail);
                    intent.putExtra( "password", NEWpassword);

                    //이를 전달해 그 다음 Activity를 시작한다.
                    startActivity(intent);
                }
            }
        });
    }

    //사용자가 입력한 이메일이 중복되지 않는지 체크하는 함수
    public boolean emailCheck(String NEWemail) {
        boolean access = false;
        for(int i=0; i<3; i++) {
            if(NEWemail.equals(emailCheck[i])){
                access = true;
                break;
            }
        }
        return access;
    }

}
