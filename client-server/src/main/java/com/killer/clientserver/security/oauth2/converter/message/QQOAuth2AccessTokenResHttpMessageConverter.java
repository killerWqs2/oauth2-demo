package com.killer.clientserver.security.oauth2.converter.message;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;

/**
 * <p>
 *     用于转换QQ oauth2 获取的token
 * </p>
 * @author killer
 * @date 2019/08/21 - 21:25
 */
public class QQOAuth2AccessTokenResHttpMessageConverter extends AbstractHttpMessageConverter<OAuth2AccessTokenResponse> {

    @Override
    protected boolean supports(Class<?> clazz) {
        // 子类可以赋值给父类
        return OAuth2AccessTokenResponse.class.isAssignableFrom(clazz);
    }

    @Override
    protected OAuth2AccessTokenResponse readInternal(Class<? extends OAuth2AccessTokenResponse> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        StringBuilder result = new StringBuilder();
        byte[] buffer = new byte[1];
        while(inputMessage.getBody().read(buffer, 0, 1) != -1) {
            result.append(new String(buffer));
            buffer = new byte[1];
        }

        OAuth2AccessTokenResponse.Builder oAuth2AccessTokenResponseBuilder = null;
        HashMap<String, Object> additionalParams = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();
        // 使用流来处理
        Stream.of(result.toString())
                .flatMap(s -> Arrays.stream(s.split("&")))
                .forEach(item -> {
                    String[] split = item.split("=");
                    map.put(split[0], split[1]);
                });

        for (Map.Entry<String, String> entry : map.entrySet()) {
            // 这里可能会有空指针异常， 但是这里返回值顺序是固定的
            switch (entry.getKey()) {
                case ACCESS_TOKEN :
                    oAuth2AccessTokenResponseBuilder = OAuth2AccessTokenResponse.withToken(entry.getValue());
                    break;
                case REFRESH_TOKEN:
                    oAuth2AccessTokenResponseBuilder.refreshToken(entry.getValue());
                    break;
                case EXPIRES_IN:
                    oAuth2AccessTokenResponseBuilder.expiresIn(Long.valueOf(entry.getValue()));
                    break;
                default:
                    additionalParams.put(entry.getKey(), entry.getValue());
            }
        }

        oAuth2AccessTokenResponseBuilder.tokenType(OAuth2AccessToken.TokenType.BEARER);

        additionalParams.put("params", new HashMap<>());

        return oAuth2AccessTokenResponseBuilder.additionalParameters(additionalParams).build();
    }

    @Override
    protected void writeInternal(OAuth2AccessTokenResponse oAuth2AccessTokenResponse, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // no need
    }

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        // 使用流来处理
        Stream.of("access_token=C8C15A2B25148FA26BBE065C57D380AC&expires_in=7776000&refresh_token=C036D7B8C56EAA04E963E1E79FF748CD content-type".toString())
                .flatMap(s -> Arrays.stream(s.split("&")))
                .forEach(item -> {
                    String[] split = item.split("=");
                    map.put(split[0], split[1]);
                });

        System.out.println(map.get("access_token"));
    }
}
