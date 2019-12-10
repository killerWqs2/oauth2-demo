package com.killer.clientserver.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author killer
 * @date 2019/08/24 - 17:33
 */
public interface Constants {

    /**正则：匹配qq openId*/
    public static String QQ_OPENID_CALLBACK = "callback\\( \\{\"client_id\":\"(.*)\",\"openid\":\"(.*)\"} \\);";

    public static String QQ_OPENID_URL = "https://graph.qq.com/oauth2.0/me";

    public static void main(String[] args) {
        String  a = "callback( {\"client_id\":\"YOUR_APPID\",\"openid\":\"YOUR_OPENID\"} );";

        Pattern regx = Pattern.compile(QQ_OPENID_CALLBACK);
        Matcher matcher = regx.matcher(a);

        // 我靠 这句调用是执行匹配的语句
        if(matcher.find()) {
            System.out.println(matcher.groupCount());
            System.out.println(matcher.group(2));
        }
    }
}
