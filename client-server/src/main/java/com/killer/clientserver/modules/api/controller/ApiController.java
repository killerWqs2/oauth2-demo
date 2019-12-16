package com.killer.clientserver.modules.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author killer
 * @date 2019/12/07 - 13:34
 */
@Slf4j
@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private OAuth2AuthorizedClientRepository clientRepository;

    @Autowired
    private HttpServletRequest request;

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("say")
    public Map<String, Object> say(Authentication authentication) {

        // clientRegistration.
        // switch (clientRegistration.getClientName()) {
        //     case "ac":
        OAuth2AuthorizedClient auth2AuthorizedClient = clientRepository.loadAuthorizedClient("ac", authentication, request);

        // 这里相当于变相的校验认证，实际上是在校验auth2AuthorizedClient
        if(auth2AuthorizedClient == null) {
            throw new InsufficientAuthenticationException("oauth2 token not access");
        }

        HashMap<String, Object> result = new HashMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer" + auth2AuthorizedClient.getAccessToken().getTokenValue());
        HttpEntity<HashMap<String, Object>> httpEntity = new HttpEntity<>(result, httpHeaders);
        ResponseEntity<HashMap> response = restTemplate.exchange("http://resource-server:8003/api/test", HttpMethod.GET, httpEntity, HashMap.class);

        // 应该是实现token过期刷新机制
        //         return result;
        //     default:
        //         return null;
        // }
        return response.getBody();
    }

}
