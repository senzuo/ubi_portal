package com.chh.obd.ubi.portal.user;


import com.chh.obd.ubi.portal.common.response.RestCode;
import com.chh.obd.ubi.portal.common.response.RestResponse;
import com.chh.obd.ubi.portal.common.response.RestUtil;

import com.chh.obd.ubi.support.common.page.Page;
import com.chh.obd.ubi.support.user.model.User;
import com.chh.obd.ubi.support.user.model.UserDTO;
import com.chh.obd.ubi.support.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Niow on 2016/11/23.
 */
@RestController
@RequestMapping("/user")
public class UserRestContoller {

    @Autowired
    private UserService userService;

    @RequestMapping("/create")
    public RestResponse addUser(User user) {
        RestResponse response = RestUtil.getResponse();
        try {
            userService.createUser(user);
        } catch (Exception e) {
            response.setRestCode(RestCode.ERROR);
        }
        return response;
    }


    @RequestMapping("/{id}/delete")
    public RestResponse deleteUser(@PathVariable("id") Long id) {
        RestResponse response = RestUtil.getResponse();
        try {
            int i = userService.deleteUserById(id);
            if (i < 1) {
                response.setRestCode(RestCode.TARGET_IS_NULL);
            }
        } catch (Exception e) {
            response.setRestCode(RestCode.ERROR);
        }
        return response;
    }

    @RequestMapping("/{id}")
    public RestResponse getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        RestResponse response = RestUtil.getResponse();
        if (user == null) {
            response.setRestCode(RestCode.TARGET_IS_NULL);
            return response;
        }
        response.setData(user);
        return response;
    }


    @RequestMapping("/page")
    public RestResponse getUserPage(Page page, UserDTO userDTO) {
        if (page == null) page = new Page();
        if (userDTO == null) {
            userDTO = new UserDTO();
        }
        Page<User> userPage = userService.getUserPage(page, userDTO);
        RestResponse response = RestUtil.getResponse();
        response.setData(userPage);
        return response;
    }
}
