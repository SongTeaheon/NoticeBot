package com.example.noticebot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

    static private final String TAG = "TAGUtils";

    static void alertFunc(Context context,String title, String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(msg)
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    static public void sendTokenToServer(String name, String token){
        Log.d(TAG, "토큰 서버에 저장! id : " + name + " 토큰 : " + token);
        try {
            JSONObject data = new JSONObject();
            data.put("type", "token");
            data.put("name", name);
            data.put("token", token);
            NetworkTask networkTask = new NetworkTask(new HttpCallback() {
                @Override
                public void callback(JSONObject resultJson) {
                    Log.d(TAG, "sendTokenToServer result : " + resultJson.toString());
                }
            }, data, "token");
            networkTask.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
