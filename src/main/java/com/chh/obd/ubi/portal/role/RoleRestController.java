package com.chh.obd.ubi.portal.role;

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
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


    @RequestMapping("/getAllRole")
    public RestResponse getAllRole(Page page, RoleDTO roleDTO) {

        if (page == null) page = new Page();
        if (roleDTO == null) roleDTO = new RoleDTO();

        Page<Role> rolePage = roleService.getRolePage(page, roleDTO);

        RestResponse response = RestUtil.getResponse();
        response.setData(rolePage);
        return response;
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.PUT)
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

    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
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
    public RestResponse addUserToRole(@RequestParam Long roleId, @RequestParam String userId) {
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
    public RestResponse getAddedRole(@RequestParam Long roleId, Page page){

        if (page == null) page = new Page();

        List<User> users = roleService.findUsersByRoleId(roleId);

        Page<User> pageUser = new Page<>();

        pageUser.setDatas(users);

        RestResponse response = RestUtil.getResponse();
        response.setData(pageUser);
        return response;
    }


}
