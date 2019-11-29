package com.example.noticebot;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnection {

    private final String urlStr = "http://yh401.pythonanywhere.com/";
    private final String TAG = "TAGRequestHttpCon";

    public JSONObject request(JSONObject _params, String target) {
        Log.d(TAG, "request!");
        HttpURLConnection urlConn = null;

        OutputStream os;
        InputStream is;
        ByteArrayOutputStream baos;

        try {
            URL url = new URL(urlStr + target);

            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Cache-Control", "no-cache");
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);

            os = urlConn.getOutputStream();
            os.write(_params.toString().getBytes());
            os.flush();
            os.close();

            String response;

            int responseCode = urlConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                is = urlConn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData;
                int nLength;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);
                Log.i(TAG, "DATA response string = " + response);

                JSONObject responseJSON = new JSONObject(response);

                Log.i(TAG, "DATA response = " + responseJSON.toString());
                return responseJSON;
            }else{
                Log.e(TAG, "responseCode = " + responseCode);
                JSONObject errJSON = new JSONObject();
                errJSON.put("message", "wrong response code");
                errJSON.put("code", responseCode);
                return errJSON;
            }
        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }

        return null;
    }
}

