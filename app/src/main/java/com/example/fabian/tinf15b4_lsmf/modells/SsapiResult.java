package com.example.fabian.tinf15b4_lsmf.modells;

import org.json.JSONArray;

/**
 * Created by s.gerhardt on 10.05.2017.
 */

public class SsapiResult {
    private JSONArray content;
    private int code;
    private String msg;

    public SsapiResult(JSONArray content, int code, String msg) {
        this.content = content;
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public JSONArray getContent() {
        return content;
    }


}
