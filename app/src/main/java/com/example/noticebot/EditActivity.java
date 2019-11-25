package com.example.noticebot;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private ArrayList<DataKeywords> mArrayList;
    private EditAdapter mAdapter;
    private int count = -1;

    String newKeyword;
    EditText keyword_typing;
//    Button keyword_add;
    RecyclerView mRecyclerView;

    //화면 레이아웃
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_edit);

        //메뉴 액션바
        getSupportActionBar().setTitle("키워드 관리");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        keyword_typing = findViewById(R.id.keyword_typing);
//        keyword_add = findViewById(R.id.keyword_add);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_keyword_edit);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();
        DataKeywords data = new DataKeywords("Apple");

        //기존 항목 불러오는 것
        mArrayList.add(data);
        mArrayList.add(data);
        mArrayList.add(data);
        mArrayList.add(data);
        mArrayList.add(data);


        mAdapter = new EditAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        //토글 스위치 (board 1)
        final ToggleButton toggleBoard1 = (ToggleButton)this
                .findViewById(R.id.Toggle_Board1);
        toggleBoard1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (toggleBoard1.isChecked()) {
                    toggleBoard1.setTextColor(Color.BLUE);
                } else {
                    toggleBoard1.setTextColor(Color.GRAY);
                }
            }
        });

        //토글 스위치 (board 2)
        final ToggleButton toggleBoard2 = (ToggleButton)this
                .findViewById(R.id.Toggle_Board2);
        toggleBoard2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (toggleBoard2.isChecked()) {
                    toggleBoard2.setTextColor(Color.BLUE);
                } else {
                    toggleBoard2.setTextColor(Color.GRAY);
                }
            }
        });

        //키워드 입력창
        keyword_typing = findViewById(R.id.keyword_typing);

        //추가 버튼
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Button buttonInsert = (Button)findViewById(R.id.keyword_add);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                String str = keyword_typing.getText().toString();
                DataKeywords data = new DataKeywords(str);
                mArrayList.add(data);   //RecyclerView의 마지막 줄에 삽입

                mAdapter.notifyDataSetChanged();
            }
        });
//        keyword_add.setClickable(false);
//        keyword_add.addTextChangedListener(new TextWatcher() {
//            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////                keyword_add.setClickable(false);
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
//////                Log.d("SENTI", s + "," + count);\
////                //toString은 null이면 에러가 나므로 조건문 활용해야 함
////                if(s!=null) {
////                    newKeyword = s.toString();
//////                    keyword_add.setEnabled(validation());
////                    keyword_add.setEnabled(true);
////                }
////
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        //클릭 가능 여부를 설정

//        keyword_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //getText()의 기본형은 string이 아니므로 (Editable) .toString()을 해줘야 함.
//                String newKeyword = keyword_add.getText().toString();
//            }
//        });
    }

    //나가기 버튼 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                alertSaveChange();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //나가기 경고 알림
    private void alertSaveChange(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditActivity.this);
        alertDialogBuilder.setTitle("변경사항 저장");
        alertDialogBuilder
                .setMessage("변경사항을 저장하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                EditActivity.this.finish();
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

    //뒤로가기 버튼 눌렀을 경우
    @Override
    public void onBackPressed() {
        alertSaveChange();
    }

    //리사이클러 뷰 기존 아이템 형성
//    public void addItem(String title, String link) {
//        DataNotices item = new DataNotices();
//
//        item.setTitle(title);
//        item.setLink(link);
//
//        mList.add(item);
//    }

    //키워드에 대한 조건을 검사하는 함수
//    public boolean validation() {
//        return emailOK.equals(InputEmail) && passwordOK.equals(InputPassword);
//    }
}
