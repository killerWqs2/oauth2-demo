package com.killer.authorizationserver.modules.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author killer
 * @date 2019/12/04 - 23:37
 */
@Controller
@RequestMapping("/oauth")
public class OAuth2Controller {

    @GetMapping("/confirm_access")
    public ModelAndView confirmAccess(ModelAndView mview) {
        mview.setViewName("login");
        return mview;
    }

}
