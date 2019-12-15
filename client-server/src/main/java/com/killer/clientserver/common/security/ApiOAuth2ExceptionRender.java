package com.killer.clientserver.common.security;

import org.springframework.http.HttpEntity;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author killer
 * @date 2019/12/15 - 14:41
 */
public class ApiOAuth2ExceptionRender implements OAuth2ExceptionRenderer {

    @Override
    public void handleHttpEntityResponse(HttpEntity<?> responseEntity, ServletWebRequest webRequest) throws Exception {
        HttpServletResponse response = webRequest.getResponse();
        response.sendRedirect("/static/api.html");
    }

}
