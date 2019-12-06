package com.example.noticebot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    String dbName;

    private final String TAG = "TAGDBHelper";
    String keywordTableName = "KEYWORD_TABLE";
    String tokenTableName = "TOKEN_TABLE";
    String noticeTableName = "NOTICE_TABLE";

    String _id =  "_id";
    String keyword_col = "Keyword";

    String not_link_col = "Link";
    String not_title_col = "Title";
    String not_id_col = "Id";

    Context context;

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.dbName = name;
        this.context = context;
    }

    public void createKeywordTable(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Create table if not exists " + keywordTableName +"(" +_id +" INTEGER PRIMARY KEY AUTOINCREMENT, " + keyword_col +" VARCHAR(21));";
        db.execSQL(query);
    }

    public void createTokenTable(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Create table if not exists " +"TOKEN_TABLE" +"(" +_id +" INTEGER PRIMARY KEY AUTOINCREMENT, " + "token"+" VARCHAR(1000));";
        db.execSQL(query);
    }

    public void createNoticeTable(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Create table if not exists " +noticeTableName +"(" +not_id_col +" INTEGER PRIMARY KEY, "  + not_title_col +" VARCHAR(100), "+ not_link_col+ " VARCHAR(500));";
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "SQL create db");
    }

    /** * Application의 버전이 올라가서 * Table 구조가 변경되었을 때 실행된다.
     *  * @param db
     *  * @param oldVersion
     *  * @param newVersion
     *  */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }

    public void addKeyword(String data){
        Log.d(TAG, "add Keyword. data : " + data);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(keyword_col, data);
        db.insert(keywordTableName, null, values);
    }

    public void addNotice(DataNotices data){
        Log.d(TAG, "add notice. title : " + data.getTitle());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(not_id_col, data.getId());
        values.put(not_link_col, data.getLink());
        values.put(not_title_col, data.getTitle());
        db.insertWithOnConflict(noticeTableName, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        db.insert(noticeTableName, null, values);
    }

    public void saveToken(String token){
        Log.d(TAG, "save token : " + token);
        SQLiteDatabase db = getWritableDatabase();

        //이미 들어있는 건 지워준다.
        String query = "delete from TOKEN_TABLE;";
        db.execSQL(query);

        ContentValues values = new ContentValues();
        values.put("token", token);
        db.insert("TOKEN_TABLE", null, values);
    }

    public String getToken(){
        Log.d(TAG, "getToken");
        ArrayList<String> array = new ArrayList<>();
        String query = "SELECT "+ _id +", " + "token" + " FROM "+ "TOKEN_TABLE;";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            while( cursor.moveToNext() ) {
                Log.d(TAG, cursor.getInt(0) + ": " + cursor.getString(1));
                array.add(cursor.getString(1));
            }
        } finally {
            if(cursor != null) cursor.close();
        }
        return array.get(0);
    }

    public void deleteAllKeyword(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from KEYWORD_TABLE;";
        db.execSQL(query);
    }

    public void deleteAllNotice(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from "+ noticeTableName+";";
        db.execSQL(query);
    }

    public ArrayList<String> getAllKeyword() {
        Log.d(TAG, "getAllKeyword");
        ArrayList<String> res = new ArrayList<>();
        String query = "SELECT "+ _id +", " + keyword_col + " FROM "+ keywordTableName +";";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            while( cursor.moveToNext() ) {
                Log.d(TAG, cursor.getInt(0) + ": " + cursor.getString(1));
                res.add(cursor.getString(1));
            }
        } finally {
            if(cursor != null) cursor.close();
        }
        return res;
    }


    public ArrayList<DataNotices> getAllNotice() {
        Log.d(TAG, "getAllNotice");
        ArrayList<DataNotices> res = new ArrayList<>();
        String query = "SELECT "+ not_id_col +", " + not_title_col +", "+ not_link_col + " FROM "+ noticeTableName +";";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            while( cursor.moveToNext() ) {
                Log.d(TAG, cursor.getInt(0) + ": " + cursor.getString(1));
                res.add(new DataNotices(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }
        } finally {
            if(cursor != null) cursor.close();
        }
        return res;
    }



}
