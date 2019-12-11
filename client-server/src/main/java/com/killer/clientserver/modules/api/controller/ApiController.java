package com.killer.clientserver.modules.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author killer
 * @date 2019/12/07 - 13:34
 */
@Slf4j
@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("test")
    public void test(Authentication authentication) {

        // 这种方式已重启用户信息就没有了
        OAuth2AuthorizationCodeAuthenticationToken authenticationToken = (OAuth2AuthorizationCodeAuthenticationToken)authentication;
        // ClientRegistration clientRegistration = authenticationToken.getClientRegistration();
        // auth2AuthorizedClientRepository.loadAuthorizedClient(, principal.getName(), request); 这个可以存储到外面
        // 通过Rest请求获取资源

        String tokenValue = authenticationToken.getAccessToken().getTokenValue();

    }

}
