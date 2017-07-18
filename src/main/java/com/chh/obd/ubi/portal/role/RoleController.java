package com.chh.obd.ubi.portal.role;

import com.chh.obd.ubi.support.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by niow on 2017/7/13.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @RequestMapping(value = "")
    public String role(){
        return "/framework/role/roleAuth";
    }

//    @RequestMapping("/addUser")
//    public String roleAddUser(@RequestParam("roleId") Long id) {
//        return "/framework/role/add";
//    }


}
