package com.killer.clientserver.modules.sys.controller;


import com.killer.clientserver.modules.sys.entity.TestEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wu QiaoSheng
 * @since 2019-08-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/user")
public class UserController {

    private HttpServletRequest request;

    private HttpServletResponse response;

    @GetMapping("test/{id}")
    public void test(TestEntity test, @PathVariable Integer id) {
        try {
            response.sendError(400, "test");
            // response.sendError(500, "test");
        } catch (IOException e) {
            response.setStatus(500);
        }

    }

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("test")
    public String test(Principal pricipal) {
        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient("stragory", pricipal.getName());

        return client.getAccessToken().getTokenValue();
    }

}
