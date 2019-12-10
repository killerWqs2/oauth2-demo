package com.killer.clientserver.security.oauth2.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.killer.clientserver.modules.sys.entity.User;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author killer
 * @date 2019/08/24 - 23:07
 */
public class OAuth2LoginSuccessfulHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // OAuth2Authentication auth = (OAuth2Authentication)authentication;
        // // 这里需要判断用户是否在数据库中已存在， 存在直接获取用户信息， 不存在直接获取并且需要注册
        // QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        // userQueryWrapper.eq("appId", openIdRequest.getBody());
        // User user = userService.getOne(userQueryWrapper);
        //
        // if(user != null) {
        //     // 用户已经注册
        // } else {
        //     // 用户未注册， 使用第三方登录
        //     HashMap<String, String> params = (HashMap<String, String>)userRequest.getAdditionalParameters().get("params");
        //     params.put("openId", openIdResponse.getBody());
        //     RequestEntity<?> userInfoRequest = this.userInfoRequestEntityConverter.convert(userRequest);
        //     userInfoResponse = this.restOperations.exchange(userInfoRequest, PARAMETERIZED_USERINFO_RESPONSE_TYPE);
        //
        //     // 注册用户到数据库中
        //     Map<String, Object> userInfo = userInfoResponse.getBody();
        //     User newUser = new User();
        //     newUser.setGender(userInfo.get("gender").equals("男") ? 0 : 1);
        //     newUser.setNickname(userInfo.get("nickname").toString());
        //     newUser.setQqOpenid(openIdResponse.getBody());
        //     // newUser.setQqRefreshToken(userRequest.get)
        // }

        // 颁发token
    }
}
