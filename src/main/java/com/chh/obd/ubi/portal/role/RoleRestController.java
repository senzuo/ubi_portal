package com.chh.obd.ubi.portal.role;

import com.chh.obd.ubi.portal.auth.annotation.Auth;
import com.chh.obd.ubi.portal.auth.annotation.AuthModule;
import com.chh.obd.ubi.portal.common.response.RestCode;
import com.chh.obd.ubi.portal.common.response.RestResponse;
import com.chh.obd.ubi.portal.common.response.RestUtil;
import com.chh.obd.ubi.support.common.page.Page;
import com.chh.obd.ubi.support.role.model.Role;
import com.chh.obd.ubi.support.role.model.RoleDTO;
import com.chh.obd.ubi.support.role.model.UserRoleMapping;
import com.chh.obd.ubi.support.role.service.RoleService;
import com.chh.obd.ubi.support.user.model.User;
import com.chh.obd.ubi.support.user.model.UserDTO;
import com.chh.obd.ubi.support.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by niow on 2017/7/13.
 */

@RestController
@RequestMapping("/role")
@AuthModule(code = "role_module",desc = "角色模块")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


    @RequestMapping("/getAllRole")
    @Auth("获取全部角色页面")
    public RestResponse getAllRole(Page page, RoleDTO roleDTO) {

        if (page == null) page = new Page();
        if (roleDTO == null) roleDTO = new RoleDTO();

        Page<Role> rolePage = roleService.getRolePage(page, roleDTO);

        RestResponse response = RestUtil.getResponse();
        response.setData(rolePage);
        return response;
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.POST)
    @Auth("根据ID更新角色信息页面")
    public RestResponse updaterole(@PathVariable("roleId") Long Id,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String desc) {


        Role role = roleService.getRole(Id);


        System.out.println(role);

        if (name != null && name != "") {
            role.setName(name);
        }
        if (desc != null && desc != "") {
            role.setDescription(desc);
        }
        try {
            roleService.updateRole(role);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RestResponse response = RestUtil.getResponse();
        return response;
    }

    @RequestMapping(value = "/del/{roleId}", method = RequestMethod.POST)
    @Auth("根据ID删除角色页面")
    public RestResponse deleteUser(@PathVariable("roleId") Long roleId) {
        RestResponse response = RestUtil.getResponse();
        try {
            roleService.deleteRole(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @Auth("添加用户页面")
    public RestResponse addUser(@RequestParam Long id, @RequestParam String name, @RequestParam(required = false) String desc) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setDescription(desc);
        try {
            role = roleService.createRole(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RestResponse response = RestUtil.getResponse();
        return response;
    }

    /**
     * 给用户添加角色
     *
     * @return
     */
    @RequestMapping(value = "/AddUser", method = RequestMethod.POST)
    @Auth("给用户添加角色界面")
    public RestResponse addUserToRole(@RequestParam Long roleId, @RequestParam Long userId) {
        try {
            roleService.addUserToRole(userId,roleId);
        }catch (Exception e){
            e.printStackTrace();
        }
        RestResponse response = RestUtil.getResponse();
        return response;
    }

    /**
     * 查询已经添加该角色的用户
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/addedUser")
    @Auth("查询已添加角色用户页面")
    public RestResponse getAddedRole(@RequestParam Long roleId, Page page, UserDTO userDTO){

        if (page == null) page = new Page();
        if (userDTO == null) userDTO = new UserDTO();


        List<User> users = roleService.findUsersByRoleId(roleId,page);

        page.setEntities(users);


        RestResponse response = RestUtil.getResponse();
        response.setData(page);
        return response;
    }

    // DELETE 方法 无法接受参数   暂时更改为 POST方法
//    @RequestMapping(value = "/removeUser", method = RequestMethod.DELETE)

    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    @Auth("将用户从角色中移除页面")
    public RestResponse removeUser(@RequestParam Long userId,@RequestParam Long roleId) {
//    public RestResponse removeUser(/*@RequestParam String userId,@RequestParam Long roleId*/) {

        RestResponse response = RestUtil.getResponse();
        try {
            roleService.removeUserFromRole(userId,roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
