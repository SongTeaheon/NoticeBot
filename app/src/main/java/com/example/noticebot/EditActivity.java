package com.example.noticebot;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EditActivity extends AppCompatActivity implements HttpCallback{
    private final String TAG = "TAGEditActivity";

    private ArrayList<String> mList;
    private ArrayList<String> addedList = new ArrayList<>();
    private ArrayList<String> deletedList = new ArrayList<>();

    private EditAdapter mAdapter;

    EditText keyword_typing;
    RecyclerView mRecyclerView;
    Button buttonInsert;

    //화면 레이아웃
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        mList = intent.getStringArrayListExtra("list");
        if(mList == null){
            Log.d(TAG, "mList is null, nothing come from CustomActivity");
            mList = new ArrayList<>();
        }
        //메뉴 액션바
        getSupportActionBar().setTitle("키워드 관리");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        keyword_typing = findViewById(R.id.keyword_typing);
//        keyword_add = findViewById(R.id.keyword_add);

        mRecyclerView = findViewById(R.id.recyclerview_keyword_edit);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//        mArrayList = new ArrayList<>();
//        DataKeywords data = new DataKeywords("Apple");
//
//        //기존 항목 불러오는 것
//        mArrayList.add(data);
//        mArrayList.add(data);
//        mArrayList.add(data);
//        mArrayList.add(data);
//        mArrayList.add(data);

        mAdapter = new EditAdapter(mList, deletedList);//지워진 데이터를 알아오기 위해서!!
        mRecyclerView.setAdapter(mAdapter);


        //토글 스위치 (board 1) : 일반
        final ToggleButton toggleBoard1 = this
                .findViewById(R.id.Toggle_Board1);

        //화면 진입시
        toggleView(toggleBoard1, 1);

        //클릭시
        toggleBoard1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                SaveSharedPreference.setBoardSwitch1(EditActivity.this, toggleState(toggleBoard1));
                toastBoardNull();
            }
        });

        //토글 스위치 (board 2) : 학사
        final ToggleButton toggleBoard2 = this
                .findViewById(R.id.Toggle_Board2);

        //화면 진입시
        toggleView(toggleBoard2, 2);

        //클릭시
        toggleBoard2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                SaveSharedPreference.setBoardSwitch2(EditActivity.this, toggleState(toggleBoard2));
                toastBoardNull();
            }
        });

        //키워드 입력창
        keyword_typing = findViewById(R.id.keyword_typing);

        //리사이클러뷰 아이템 형성
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        //추가 버튼
        buttonInsert = findViewById(R.id.keyword_add);

        //항목 20개 넘으면 추가버튼 비활성화
        if(mList.size()>20) {
            buttonInsert.setClickable(false);
        }

        //추가버튼 누를 경우
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = keyword_typing.getText().toString();
                str = str.trim();

                //특수 문자 확인
                if(!str.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")){
                    Log.d(TAG, "특수문자 포함");
                    Toast.makeText(EditActivity.this, "특수문자가 포함되어 있습니다.", Toast.LENGTH_LONG).show();
                }else if(mList.contains(str)){
                    Log.d(TAG, "특수문자 포함");
                    Toast.makeText(EditActivity.this, "이미 존재하는 키워드입니다.", Toast.LENGTH_LONG).show();
                } else if (mList.size() >= 20) {
                    Toast.makeText(EditActivity.this, "키워드는 최대 20개까지 등록할 수 있습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "키워드 추가!");
                    mList.add(str);
                    addedList.add(str);
                    keyword_typing.setText(null); //기존에 타이핑한 문자는 삭제
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        //삭제 버튼
//        Button buttonDelete = findViewById(R.id.keyword_delete);
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos =(int) v.getTag();
//                mList.remove(pos);
////                mList.remove(position);
//            }
//        });


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
        //switch문을
        if(item.getItemId() == android.R.id.home){
            //toolbar의 back키 눌렀을 때 동작
            alertSaveChange();
        }
        return super.onOptionsItemSelected(item);
    }

    private void toastBoardNull(){
        if(!SaveSharedPreference.getBoardSwitch1(EditActivity.this) &&
                !SaveSharedPreference.getBoardSwitch2(EditActivity.this)) {
            Toast.makeText(EditActivity.this,
                    "현재 탐색 게시판이 설정되어있지 않습니다." +
                            "게시판을 설정하지 않으면 키워드를 설정해두어도 공지사항 알림을 받을 수 없습니다.",
                    Toast.LENGTH_LONG).show();
        }
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
                                checkDeletedList(); //deletedList에 있는 Item이 mList에 있는 경우 확인
                                checkAddedList(); //AddedList에 있는 Item이 mList에 없는 경우 확인.

                                JSONObject data = makeJson();
                                NetworkTask networkTask = new NetworkTask(EditActivity.this, data, "keyword");
                                networkTask.execute();

                                Intent intent = new Intent();
                                Log.d(TAG, "save change." );
                                intent.putStringArrayListExtra("list", mList);
                                intent.putExtra("save", 1);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                Intent intent = new Intent();
                                Log.d(TAG, "no change. ");
                                intent.putExtra("save", 0);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private JSONObject makeJson(){
        JSONObject resJSON = new JSONObject();
        try {
            resJSON.put("type", "keyword");
            String name = SaveSharedPreference.getUserName(this);
            resJSON.put("name", name);
            resJSON.put("add", addedList);
            resJSON.put("delete", deletedList);
            Log.d(TAG, resJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resJSON;
    }



    //뒤로가기 버튼 눌렀을 경우
    @Override
    public void onBackPressed() {
        alertSaveChange();
    }

    @Override
    public void callback(JSONObject resultJson) {
        Log.d(TAG, "callback : " + resultJson.toString());
    }

    //deletedList에 있는 Item이 mList에 있는 경우 확인
    private void checkDeletedList(){
        for(int i = 0; i < deletedList.size(); i++){
            if(mList.contains(deletedList.get(i))){
                Log.d(TAG, "deleted item is still in mList. item : " + deletedList.get(i));
                deletedList.remove(i);
            }
        }
    }

    //AddedList에 있는 Item이 mList에 없는 경우 확인.
    private void checkAddedList(){
        for(int i = 0; i < addedList.size(); i++){
            if(!mList.contains(addedList.get(i))){
                Log.d(TAG, "added item is not in mList. item : " + addedList.get(i));
                addedList.remove(i);
            }
        }
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

    public void toggleOn(ToggleButton toggleButton) {
        toggleButton.setTextColor(Color.BLUE);
        toggleButton.setChecked(true);
    }

    public void toggleOff(ToggleButton toggleButton) {
        toggleButton.setTextColor(Color.GRAY);
        toggleButton.setChecked(false);
    }

    public void toggleView(ToggleButton toggleButton, int toggleNumber) {
        if(toggleNumber==1){
            if(SaveSharedPreference.getBoardSwitch1(EditActivity.this) == false) {
                toggleOff(toggleButton);
            } else {
                toggleOn(toggleButton);
            }
        } else if(toggleNumber==2){
            if(SaveSharedPreference.getBoardSwitch2(EditActivity.this) == false) {
                toggleOff(toggleButton);
            } else {
                toggleOn(toggleButton);
            }
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
}
