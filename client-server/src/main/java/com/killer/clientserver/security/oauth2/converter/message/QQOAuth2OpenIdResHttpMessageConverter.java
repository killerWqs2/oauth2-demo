package com.killer.clientserver.security.oauth2.converter.message;

import com.killer.clientserver.common.Constants;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author killer
 * @date 2019/08/24 - 17:30
 */
public class QQOAuth2OpenIdResHttpMessageConverter extends AbstractHttpMessageConverter<String> {
    @Override
    protected boolean supports(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        StringBuilder result = new StringBuilder();
        byte[] buffer = new byte[1];
        while(inputMessage.getBody().read(buffer, 0, 1) != -1) {
            result.append(new String(buffer));
            buffer = new byte[1];
        }

        Pattern regx = Pattern.compile(Constants.QQ_OPENID_CALLBACK);
        Matcher matcher = regx.matcher(result.toString());

        // 这句调用是执行匹配的语句
        if(matcher.find()) {
           return matcher.group(2);
        } else {
            throw new HttpMessageNotReadableException("返回格式不匹配", inputMessage);
        }
    }

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // do nothing
    }
}
