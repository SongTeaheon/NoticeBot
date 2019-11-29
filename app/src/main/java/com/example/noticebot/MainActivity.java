package com.example.noticebot;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null;
    ArrayList<DataNotices> mList = new ArrayList<DataNotices>();
    MainNoticesAdapter mAdapter;
    private final String TAG = "TAGLoginActivity";

    //화면 레이아웃
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //메뉴 액션바
        getSupportActionBar().setTitle("메인화면");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recyclerview_main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        MainNoticesAdapter adapter = new MainNoticesAdapter(mList);
        recyclerView.setAdapter(adapter);

        // test로 임의의 리스트 형성
        testNoticesList();

        adapter.notifyDataSetChanged();
    }

    //리사이클러 뷰 아이템 형성
    public void addItem(String keyword, String title) {
        DataNotices item = new DataNotices();

        item.setTitle(keyword);
        item.setLink(title);

        mList.add(item);
    }

    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //설정 버튼 눌렀을 때
        if (id == R.id.menu_setting) {
            Intent intent = new Intent(MainActivity.this, CustomActivity.class);
            startActivity(intent);
//            Toast.makeText(this, "설정 클릭", Toast.LENGTH_SHORT).show();
//            return true;
        }

        //로그아웃 버튼 눌렀을 때
        if (id == R.id.menu_logout) {
            alertLogout();
//            Toast.makeText(this, "로그아웃 클릭", Toast.LENGTH_SHORT).show();
//            return true;
        }

        //나가기 버튼 눌렀을 때
        switch (item.getItemId()){
            case android.R.id.home:{
                alertExit();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }

    //로그아웃 경고창
    private void alertLogout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("로그 아웃");
        alertDialogBuilder
                .setMessage("로그아웃을 하시면 앱 내 저장된 자료가 초기화 됩니다. 로그아웃을 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                //로그인 기록 삭제
                                SaveSharedPreference.clearUserName(MainActivity.this);

                                //바로 종료보다는 로그인 화면으로 돌아가는 게 더 좋지 않나..?
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
//                                MainActivity.this.finish();
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

    //종료 경고창
    private void alertExit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("프로그램 종료");
        alertDialogBuilder
                .setMessage("프로그램을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                moveTaskToBack(true);
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
        alertExit();
    }


    public void testNoticesList() {
//        for (int i = 0; i < 20; i++) {
//            addItem("키워드" + (i + 1), "공지사항 제목" + (i + 1));
//        }
        addItem("연구","[신청가능연구실목록] 2019-겨울 공학연구인턴십 참여 신청 안내");
        addItem("공학","[학생미래지원센터] WDB공학 채용설명회 개최");
        addItem("공학","[공학교육혁신센터] 2019 공학페스티벌 \"학생성과 발표대회\" 신청 안내");
        addItem("연구","2019년 서울시립대학교 연구윤리 특강 안내");
        addItem("공학","[공학교육혁신센터] 2019 공학페스티벌 「공학밴드」 참가팀 모집");
        addItem("AI","[공학교육혁신센터] 2019 공학페스티벌 \"AI 사물인식해커톤\" 참가자 모집 안내");
        addItem("연구","2019년 대학혁신지원사업 [우수 학생 연구인력 지원 확대] 프로그램 참가자 모집(1차)(수정)");
        addItem("어학","서울시립대학교 한국어학당 직원 인사 관리 규정 제정 공고");
        addItem("연구","서울연구원 시민 아이디어 공모전 안내");
        addItem("연수","2019년 하반기 한미대학생연수(WEST) 대학설명회 개최 안내");
        addItem("AI","2019 인텔 AI 드론 경진대회");
        addItem("어학","2019년도 봄학기 서울시립대학교 글로벌외국어교육센터 교내어학프로그램 장학프로그램 안내");
        addItem("연수","[삼육대학교] 2019년 파란사다리 사업 - 해외연수프로그램 참가자 선발");

    }

    public void onItemClicked(int position) {
        Log.d(TAG,position + "번 아이템 클릭됨");
    }

    public void onTitleButtonClicked(int position) {
        Log.d(TAG,position + "번 링크 클릭됨");
    }
}