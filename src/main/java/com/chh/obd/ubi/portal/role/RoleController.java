package com.chh.obd.ubi.portal.role;

import com.chh.obd.ubi.support.common.page.Page;
import com.chh.obd.ubi.support.role.service.RoleService;
import com.chh.obd.ubi.support.user.model.User;
import com.chh.obd.ubi.support.user.model.UserDTO;
import com.chh.obd.ubi.support.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by niow on 2017/7/13.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "")
    public String role(){
        return "/framework/role/roleAuth";
    }

    @RequestMapping("/addUser")
    public String roleAddUser(@RequestParam("roleId") Long id, Model model) {

        model.addAttribute("roleId",id);

        return "/framework/role/addUser";
    }



}
