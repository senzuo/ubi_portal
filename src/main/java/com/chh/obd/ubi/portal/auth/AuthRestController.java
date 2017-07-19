package com.chh.obd.ubi.portal.auth;

import com.chh.obd.ubi.portal.common.response.RestResponse;
import com.chh.obd.ubi.portal.common.response.RestUtil;
import com.chh.obd.ubi.support.auth.model.Authority;
import com.chh.obd.ubi.support.auth.model.AuthorityModule;
import com.chh.obd.ubi.support.auth.service.AuthService;
import com.chh.obd.ubi.support.common.page.Page;
import com.chh.obd.ubi.support.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by niow on 2017/7/13.
 */
@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/page")
    public RestResponse getAuthPage(Page page,Authority authority) {
        if (page == null) page = new Page();
        if (authority == null) {
            authority = new Authority();
        }
        Page<Authority> authPage = authService.getAuthorityPage(page, authority);
        RestResponse response = RestUtil.getResponse();
        response.setData(authPage);
        return response;
    }

    @RequestMapping("/module/all")
    public RestResponse getAllAuthModule() {
        List<AuthorityModule> allAuthModule = authService.getAllAuthModule();

        for (AuthorityModule authorityModule:allAuthModule) {

            List<Authority> authorities = authorityModule.getAuthorityList();

            if (authorities == null) {
                System.out.println("bad");
            }
//            for (Authority authority:authorities) {
//                System.out.println(authority.getAuthCode());
//            }
        }

        RestResponse response = RestUtil.getResponse();
        response.setData(allAuthModule);

        return response;
    }

}
