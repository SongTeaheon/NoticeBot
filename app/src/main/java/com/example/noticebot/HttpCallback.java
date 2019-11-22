package com.example.noticebot;

import org.json.JSONObject;

public interface HttpCallback {
    void  callback (final JSONObject resultJson);
}
