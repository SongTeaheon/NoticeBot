package com.example.noticebot;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class CustomActivity extends AppCompatActivity {

    private final String TAG = "TAGCustomActivity";
    private final int EDIT_ACTIVITY_REQUEST_CODE = 1001;
    RecyclerView mRecyclerView = null;
    ArrayList<String> mList = new ArrayList<>();
    CustomKeywordAdapter mAdapter;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_custom);

        //메뉴 액션바
        getSupportActionBar().setTitle("사용자 설정");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //토글 스위치
        final ToggleButton function_switch = this
                .findViewById(R.id.fucntion_switch);
        function_switch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (function_switch.isChecked()) {
                    function_switch.setTextColor(Color.BLUE);
                } else {
                    function_switch.setTextColor(Color.GRAY);
                }
            }
        });

        //리사이클러뷰 (키워드)
        mRecyclerView = findViewById(R.id.recyclerview_keyword_custom);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // dummy data (리사이클러뷰 데이터 형성)
        for (int i = 0; i < 20; i++) {
            mList.add("키워드"+ i);
        }


        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new CustomKeywordAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);



        //편집 버튼
//        Button_Edit.setClickable(true);
//        Button_Edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CustomActivity.this, EditActivity.class);
//                startActivity(intent);
//            }
//        });

        //액션버튼 메뉴 액션바에 집어 넣기

    }


    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_custom, menu);
        return true;
    }

    //액션바 버튼 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //뒤로가기 버튼
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        //편집 버튼
        if(item.getItemId() == R.id.menu_edit){
            Intent intent = new Intent(CustomActivity.this, EditActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            Toast.makeText(CustomActivity.this, "something wrong.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == EDIT_ACTIVITY_REQUEST_CODE) {
            mList = data.getStringArrayListExtra("list");
            Log.d(TAG, "activity result from editAct.");

            if(data.getIntExtra("save", 0) == 1) {
                mAdapter = new CustomKeywordAdapter(mList);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

}
