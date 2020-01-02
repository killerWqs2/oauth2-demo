package cn.killers.wqscasserver.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * @author Wu QiaoSheng
 * @description 其实我更关心的是前台怎么无缝衔接的，重定向只是请求级别的，不是页面级别的即可。嗯就是这样
 *              token和 用户信息一样吗？？？ 我需要看一个大型项目来验证我的想法
 * @date 2019-12-27 16:31
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private HttpServletRequest request;

    private HttpServletResponse response;

    public LoginController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @PostMapping("/token")
    public void fetchToken(@RequestParam String redirectUri) throws IOException {
        // token service 获取之后
        response.sendRedirect(redirectUri);
    }

    @GetMapping("/cookie")
    public void testCookie() throws IOException {
        // 从一个网站上访问另一个网站的后台，cookie会设置在哪个地方呢？？？
        Cookie cookie = new Cookie("test", "test");
        // cookie 可以设置domain
        cookie.setDomain("client-server");
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write("Hello world".getBytes());
        outputStream.flush();
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }

}
