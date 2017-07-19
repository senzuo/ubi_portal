package com.chh.obd.ubi.portal.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by niow on 2017/7/13.
 */
@Controller
@RequestMapping("/auth/view")
public class AuthController {
    @RequestMapping("/manage")
    public String AuthView(){
        return "/framework/auth/authManage";
    }

}
