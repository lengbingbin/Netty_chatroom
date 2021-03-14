package com.zhb.nettychat.model.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * 值对象
 */
public class ResponseJson extends HashMap<String, Object> {

    private static final Integer SUCCESS_STATUS = 200;
    private static final Integer ERROR_STATUS = -1;
    private static final String SUCCESS_MSG = "一切正常";

    public ResponseJson() {
        super();
    }

    public ResponseJson(int code) {
        super();
        setStatus(code);
    }

    public ResponseJson error(String msg) {
        put("msg", msg);
        put("status", ERROR_STATUS);
        return this;
    }

    public ResponseJson setStatus(int status) {
        put("status", status);
        return this;
    }

    public ResponseJson setMsg(String msg) {
        put("msg", msg);
        return this;
    }

    public ResponseJson success() {
        put("msg", SUCCESS_MSG);
        put("status", SUCCESS_STATUS);
        return this;
    }

    public ResponseJson setData(String key, Object obj) {
        HashMap<String, Object> data = (HashMap<String, Object>) get("data");
        if (data == null) {
            data = new HashMap<>();
            put("data", data);
        }
        data.put(key, obj);
        return this;
    }

    /**
     * 返回JSON字符串
     */
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
