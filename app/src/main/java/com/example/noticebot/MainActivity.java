package com.example.noticebot;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.Toast;

import com.example.noticebot.MainNoticesAdapter;
import com.example.noticebot.SampleNotices;
import com.example.noticebot.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null;
    ArrayList<SampleNotices> mList = new ArrayList<SampleNotices>();
    MainNoticesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("ACTIONBAR");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recyclerview_main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        MainNoticesAdapter adapter = new MainNoticesAdapter(mList);
        recyclerView.setAdapter(adapter);

        // 리사이클러뷰에 표시할 데이터 리스트 생성. (for 구문)
        for (int i = 0; i < 20; i++) {
            addItem("제목" + (i + 1), "urls" + (i + 1));

        }
        adapter.notifyDataSetChanged();
    }

    public void addItem(String title, String link) {
        SampleNotices item = new SampleNotices();

        item.setTitle(title);
        item.setLink(link);

        mList.add(item);
    }

    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //설정 버튼 눌렀을 때
        if (id == R.id.menu_setting) {
            Toast.makeText(this, "설정 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }

        //로그아웃 버튼 눌렀을 때
        if (id == R.id.menu_logout) {
            Toast.makeText(this, "로그아웃 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }


}