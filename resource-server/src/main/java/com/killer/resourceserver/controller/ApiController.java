package com.killer.resourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author killer
 * @date 2019/07/25 - 21:34
 */
@RestController
@RequestMapping("api")
public class ApiController  {

    @GetMapping("test")
    public String say(HttpServletRequest request, HttpServletResponse response) {
        return "Hello";
    }

}
