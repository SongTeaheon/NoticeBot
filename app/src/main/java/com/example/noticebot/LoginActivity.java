package com.example.noticebot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    //컴포넌트를 입력하고, 해당 컴포넌트의 아이디들을 나열한 뒤, Alt + Enter를 하면 위에 import 항이 추가된다.
    EditText EditText_email, EditText_password;
    Button Button_Login, Button_Signup;

    //테스트 계정들
    String emailOK = "abc";
    String passwordOK = "123";
    String InputEmail = "";
    String InputPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText_email = findViewById(R.id.EditText_email);
        EditText_password = findViewById(R.id.EditText_password);
        Button_Login = findViewById(R.id.Button_Login);
        Button_Signup = findViewById(R.id.Button_Signup);



        //로그인 버튼
        Button_Login.setClickable(true);
        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EditText_email.getText().toString();
                String password = EditText_password.getText().toString();

                if(loginAccess(email, password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    //Intent 형으로 정의된 intent의 putExtra 기능을 통해 email과 passsword를 전송한다.
                    intent.putExtra("email", email);
                    intent.putExtra( "password", password);

                    //이를 전달해 그 다음 Activity를 시작한다.
                    startActivity(intent);
                }

                //아이디 비밀번호가 존재하지 않을 경우
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setTitle("회원정보 불일치");
                    alertDialogBuilder
                            .setMessage("아이디 혹은 비밀번호가 일치하지 않습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int whichButton){
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });


        //회원가입 버튼
        Button_Signup.setClickable(true);
        Button_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    //사용자가 입력한 이메일과 비밀번호가 일치하는지를 체크하여 반환하는 함수
    public boolean loginAccess(String InputEmail, String InputPassword) {
        return emailOK.equals(InputEmail) && passwordOK.equals(InputPassword);
    }

}
