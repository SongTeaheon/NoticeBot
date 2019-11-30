package com.example.noticebot;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";
    static final String BOARD1_ON = "isBoard1";
    static final String BOARD2_ON = "isBoard2";
    static final String APP_FUNCTION = "isWorking";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    // 저장된 회원 정보 가져오기
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    // 로그아웃
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }


    //Board1: 일반 공지
    public static void setBoardSwitch1(Context ctx, boolean isBoard1) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(BOARD1_ON, isBoard1);
        editor.commit();
    }

    public static Boolean getBoardSwitch1(Context ctx) {
//        return getSharedPreferences(ctx).getString(BOARD1_ON, "");
        return getSharedPreferences(ctx).getBoolean(BOARD1_ON, false);
    }


    //Board2: 학사 공지
    public static void setBoardSwitch2(Context ctx, boolean isBoard2) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(BOARD2_ON, isBoard2);
        editor.commit();
    }


    public static Boolean getBoardSwitch2(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(BOARD2_ON, false);
    }

    //
    public static void setFunctionSwitch(Context ctx, boolean isWorking) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(APP_FUNCTION, isWorking);
        editor.commit();
    }

    public static Boolean getFunctionSwtich (Context ctx) {
        return getSharedPreferences(ctx).getBoolean(APP_FUNCTION, true);
    }
}
