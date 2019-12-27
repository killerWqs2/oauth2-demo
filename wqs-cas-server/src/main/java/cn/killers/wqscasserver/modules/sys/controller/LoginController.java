package cn.killers.wqscasserver.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Wu QiaoSheng
 * @description 其实我更关心的是前台怎么无缝衔接的
 * @date 2019-12-27 16:31
 */
@Controller
public class LoginController {

    private HttpServletResponse response;

    public LoginController(HttpServletResponse response) {
        this.response = response;
    }

    @PostMapping("/token")
    public void fetchToken(@RequestParam String redirectUri) throws IOException {
        // token service 获取之后
        response.sendRedirect(redirectUri);
    }

}
