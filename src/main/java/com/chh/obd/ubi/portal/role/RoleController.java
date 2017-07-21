package com.chh.obd.ubi.portal.role;

import com.chh.obd.ubi.portal.auth.annotation.Auth;
import com.chh.obd.ubi.portal.auth.annotation.AuthModule;
import com.chh.obd.ubi.portal.menu.annotation.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Created by niow on 2017/7/13.
 */
@Controller
@RequestMapping("/role")
@AuthModule(code = "role_module", desc = "角色模块")
public class RoleController {

    @RequestMapping(value = "")
    @Auth("角色管理页面")
    @Menu(title = "角色管理")
    public String role(){
        return "framework/role/rolePage";
    }

    @RequestMapping("/addUser")
    @Auth("添加用户到角色页面")
    public String roleAddUser(@RequestParam("roleId") Long id, Model model) {

        model.addAttribute("roleId",id);

        return "/framework/role/addUser";
    }



}
