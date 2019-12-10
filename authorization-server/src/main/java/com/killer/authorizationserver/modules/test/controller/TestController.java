package com.killer.authorizationserver.modules.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author killer
 * @date 2019/12/07 - 13:34
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("test")
    public void test(Principal principal) {
        log.info(principal.getName());
    }

}
