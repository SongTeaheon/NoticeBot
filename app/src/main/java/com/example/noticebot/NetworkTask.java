package com.example.noticebot;

import android.os.AsyncTask;
import android.util.Log;

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
            Log.d("ConnectionResult", res.toString());
            callbackActivity.callback(res);
        }
        else
            Log.e("ConnectionResult", "null");
    }
}
