package com.chh.obd.ubi.portal.role;

import com.chh.obd.ubi.support.role.service.RoleService;
import com.chh.obd.ubi.support.role.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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


}
