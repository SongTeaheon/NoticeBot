package com.example.noticebot;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
    private final String TAG = "TAGNetworkTask";
    private JSONObject values;
    private HttpCallback callbackActivity;
    private String targetUrl;
    public NetworkTask(HttpCallback callbackActivity, JSONObject values, String targetUrl) {
        Log.d(TAG, "NetworkTask construct");
        this.targetUrl = targetUrl;
        this.callbackActivity = callbackActivity;
        this.values = values;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        Log.d(TAG, "do background task");
        JSONObject result; // 요청 결과를 저장할 변수.
        HttpConnection httpConnection = new HttpConnection();
        result = httpConnection.request(values, targetUrl); // 해당 URL로 부터 결과물을 얻어온다.
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject res) {
        Log.d(TAG, "background task is done");
        super.onPostExecute(res);

        //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
        if(res != null) {
            try {
                if(res.getString("message").equals("wrong response code")) {
                    Log.d(TAG, "something wrong. code : " + res.getInt("code"));
//                    Utils.alertFunc((Context) callbackActivity, "somethig wrong", "response code : " + res.getInt("code"));
                }else {
                    callbackActivity.callback(res);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            Log.e(TAG, "null");
    }
}
