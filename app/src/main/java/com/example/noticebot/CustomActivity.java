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
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;

public class CustomActivity extends AppCompatActivity {

    private final String TAG = "TAGCustomActivity";
    private final int EDIT_ACTIVITY_REQUEST_CODE = 1001;
    RecyclerView mRecyclerView = null;
    ArrayList<String> mList;
    CustomKeywordAdapter mAdapter;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_custom);

        if(dbHelper == null){
            Log.d(TAG, "new DBHelper ");
            dbHelper = new DBHelper(this, "APP_DB", null, 1);
        }

        //메뉴 액션바
        getSupportActionBar().setTitle("사용자 설정");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //토글 스위치
        final ToggleButton toggleFunction = this
                .findViewById(R.id.fucntion_switch);

        //화면 진입시
        toggleView(toggleFunction);
        toggleFunction.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                SaveSharedPreference.setFunctionSwitch(CustomActivity.this, toggleState(toggleFunction));
            }
        });

        //리사이클러뷰 (키워드)
        mRecyclerView = findViewById(R.id.recyclerview_keyword_custom);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // dummy data (리사이클러뷰 데이터 형성)
        //TODO: 테스트용 코드. 서버 통신으로 키워드를 받아오면 없앨 것!
        mList = dbHelper.getAllKeyword();
        if(mList.size() == 0)
            testCustomKeywords();

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new CustomKeywordAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

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
            Log.d(TAG, "menu_edit clicked");
            Intent intent = new Intent(CustomActivity.this, EditActivity.class);
            intent.putStringArrayListExtra("list", mList);
            startActivityForResult(intent, EDIT_ACTIVITY_REQUEST_CODE);
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
            Log.d(TAG, "activity result from editAct.");

            if(data.getIntExtra("save", 0) == 1) {
                mList = data.getStringArrayListExtra("list");
                updateDB();
                mAdapter = new CustomKeywordAdapter(mList);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    public void toggleOn(ToggleButton toggleButton) {
        toggleButton.setTextColor(Color.BLUE);
        toggleButton.setChecked(true);
    }

    public void toggleOff(ToggleButton toggleButton) {
        toggleButton.setTextColor(Color.GRAY);
        toggleButton.setChecked(false);
    }

    public void toggleView(ToggleButton toggleButton) {
        if(!SaveSharedPreference.getFunctionSwtich(CustomActivity.this)) {
            toggleOff(toggleButton);
        } else {
            toggleOn(toggleButton);
        }
    }


    public boolean toggleState(ToggleButton toggleButton){
        boolean state;
        if (toggleButton.isChecked()) {
            toggleButton.setTextColor(Color.BLUE);
            state = true;
        } else {
            toggleButton.setTextColor(Color.GRAY);
            state = false;
        }
        return state;
    }




    public void testCustomKeywords() {
        //TODO 내림차순 정렬 알고리즘 만들어 적용시키기
        mList.add("연구");
        mList.add("공학");
        mList.add("AI");
        mList.add("연수");
        mList.add("어학");
        mList.add("가1");
        mList.add("가2");
        mList.add("나3");
        mList.add("가4");
        mList.add("가5");
        mList.add("다6");
        mList.add("가7");
        mList.add("가8");
        mList.add("라9");
        mList.add("라10");
        mList.add("가11");
        mList.add("a12");
        mList.add("b13");
        mList.add("x14");
        Collections.sort(mList);
        updateDB();
    }
    private void updateDB(){
        dbHelper.deleteAllKeyword();
        for(int i = 0; i < mList.size(); i++){
            dbHelper.addKeyword(mList.get(i));
        }
        dbHelper.getAllKeyword(); //TODO: 테스트용 지우기.
    }
}
