package com.example.noticebot;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MenuItem;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class CustomActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null;
    ArrayList<DataNotices> mList = new ArrayList<DataNotices>();
    MainNoticesAdapter mAdapter;

    Button Button_Edit;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_custom);

        Button_Edit  = findViewById(R.id.Button_Edit);

        //메뉴 액션바
        getSupportActionBar().setTitle("사용자 설정");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //토글 스위치
        final ToggleButton function_switch = (ToggleButton)this
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
        RecyclerView recyclerView = findViewById(R.id.recyclerview_keyword_custom);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        CustomKeywordAdapter adapter = new CustomKeywordAdapter(mList);
        recyclerView.setAdapter(adapter);

        // 리사이클러뷰에 표시할 데이터 리스트 생성. (for 구문)
        for (int i = 0; i < 20; i++) {
            addItem("제목" + (i + 1), "urls" + (i + 1));

        }
        adapter.notifyDataSetChanged();

        //편집 버튼
        Button_Edit.setClickable(true);
        Button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    //리사이클러 뷰 아이템 형성
    public void addItem(String title, String link) {
        DataNotices item = new DataNotices();

        item.setTitle(title);
        item.setLink(link);

        mList.add(item);
    }

    //뒤로가기 버튼 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
