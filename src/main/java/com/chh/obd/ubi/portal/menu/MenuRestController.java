package com.chh.obd.ubi.portal.menu;

import com.chh.obd.ubi.portal.common.response.RestCode;
import com.chh.obd.ubi.portal.common.response.RestResponse;
import com.chh.obd.ubi.portal.common.response.RestUtil;
import com.chh.obd.ubi.support.common.page.Page;
import com.chh.obd.ubi.support.menu.model.Menu;
import com.chh.obd.ubi.support.menu.model.MenuDTO;
import com.chh.obd.ubi.support.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;

/**
 * Created by niow on 2017/7/13.
 */
@RestController
@RequestMapping("/menu")
public class MenuRestController {
    @Autowired
    private Menu menu;
    @Autowired
    private MenuService menuService;
    @RequestMapping("/search")
    public RestResponse search(Long id){
        menu = menuService.getMenuById(id);
        RestResponse response = RestUtil.getResponse();
        if (menu == null) {
            response.setRestCode(RestCode.TARGET_IS_NULL);
            return response;
        }
        response.setData(menu);
        return response;
    }


    @RequestMapping("/page")
    public RestResponse getMenuPage(Page page, MenuDTO menuDTO){
        if (page == null) page = new Page();
        if (menuDTO == null) {
            menuDTO = new MenuDTO();
        }
        Page<Menu> menuPage = menuService.getAllRootMenu(page, menuDTO);
        RestResponse response = RestUtil.getResponse();
        response.setData(menuPage);
        return response;
    }
    @RequestMapping(value ="/{id}",method = RequestMethod.GET)
    public RestResponse getChildrenMenuPage(Page page, MenuDTO menuDTO, @PathVariable("id") Long id){
        if (page == null) page = new Page();
        if (menuDTO == null) {
            menuDTO = new MenuDTO();
        }
        Page<Menu> menuPage = menuService.getChildrenMenu(page, menuDTO,id);
        RestResponse response = RestUtil.getResponse();
        response.setData(menuPage);
        return response;
    }

    @RequestMapping(value = "",method =RequestMethod.POST )
    public void addMenu(@RequestParam String name, @RequestParam String icon, @RequestParam int order_index){//这里是通过名字对应的，
        menu = new Menu();
        if(name!=null){

            menu.setTitle(name);
        }
        if(icon != null){
            menu.setIcon(icon);
        }
        if(order_index >= 0){
            menu.setOrderIndex(order_index);
        }
        menu.setPid(-1L);
        menuService.addMenu(menu);
    }
    @RequestMapping(value="/delete")
    public void deleteChildrenMenu(@RequestParam Long id){
        menuService.deleteChildrenMenu(id);

    }

    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public RestResponse updateParetMenu(@RequestParam Long id,@RequestParam String title,@RequestParam int order_index,@RequestParam String icon){
        menu = new Menu();
        if(title != null) {
            menu.setTitle(title);
        }
        if(order_index >= 0){
            menu.setOrderIndex(order_index);
        }
        if(icon != null){
            menu.setIcon(icon);
        }
        menu.setId(id);
        menu.setPid(-1L);
        menuService.updateMenu(menu);
        RestResponse response = RestUtil.getResponse();
        return response;

    }
}
