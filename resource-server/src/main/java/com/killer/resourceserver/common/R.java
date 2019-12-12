package com.killer.resourceserver.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wu QiaoSheng
 * @description
 * @date 2019-12-12 11:37
 */
public class R {

    private R() {};

    public static Map<String, Object> ok(Object data) {
        HashMap<String, Object> map = new HashMap(3);
        map.put("code", 200);
        map.put("msg", "success");
        map.put("data", data);

        return map;
    }

    // public static R ok(String msg, Object data) {
    //     HashMap map = new HashMap(3);
    //     map.put("code", 200);
    //     map.put("msg", msg);
    //     map.put("data", data);
    //
    //     return map;
    // }
    //
    // public static R err(int code, String msg) {
    //     R r = new R();
    //     Map<String, Object> map = r.getMap();
    //     map.put("code", code);
    //     map.put("msg", msg);
    //
    //     return r;
    // }
}
