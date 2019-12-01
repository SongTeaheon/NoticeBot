package com.example.noticebot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private final String TAG = "TAGDBHelper";
    String tableName = "KEYWORD_TABLE";
    String wor = "Keyword";
    String _id =  "_id";

    Context context;

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;

        SQLiteDatabase db = getWritableDatabase();
        String query = "Create table if not exists " +tableName +"(" +_id +" INTEGER PRIMARY KEY AUTOINCREMENT, " + wor+" VARCHAR(21));";
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "SQL create table");
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
        values.put(wor, data);
        db.insert(tableName, null, values);
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from KEYWORD_TABLE;";
        db.execSQL(query);
    }

    public ArrayList<String> getAllData() {
        Log.d(TAG, "getAllData");
        ArrayList<String> res = new ArrayList<>();
        String query = "SELECT "+ _id +", " + wor + " FROM "+ tableName+";";

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



}
