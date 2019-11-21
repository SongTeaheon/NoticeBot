package com.example.noticebot;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;

import com.example.noticebot.MainNoticesAdapter;
import com.example.noticebot.SampleNotices;
import com.example.noticebot.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null ;
    ArrayList<SampleNotices> mList = new ArrayList<SampleNotices>();
    MainNoticesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recyclerview_main_list) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        MainNoticesAdapter adapter = new MainNoticesAdapter(mList) ;
        recyclerView.setAdapter(adapter) ;

        // 리사이클러뷰에 표시할 데이터 리스트 생성. (for 구문)
        for(int i = 0; i<20; i++) {
            addItem("제목"+(i+1), "urls"+(i+1) );

        }
        adapter.notifyDataSetChanged() ;
    }

    public void addItem(String title, String link) {
        SampleNotices item = new SampleNotices();

        item.setTitle(title);
        item.setLink(link);

        mList.add(item);
    }
}
