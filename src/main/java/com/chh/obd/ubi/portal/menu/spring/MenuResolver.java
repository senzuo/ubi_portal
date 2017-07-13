package com.chh.obd.ubi.portal.menu.spring;

import com.chinaj.portal.auth.annotation.Auth;
import com.chinaj.portal.auth.model.Menu;
import com.chinaj.portal.auth.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/4/1.
 */
@Component
public class MenuResolver {

    private static final Logger log = LoggerFactory.getLogger(MenuResolver.class);

    @Autowired
    private MenuService menuService;

    public void resolve(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Controller.class);
        if (beans == null || beans.isEmpty()) {
            log.warn("没有找到Spring Controller");
        }
        for (Object bean : beans.values()) {
            readMenu(bean.getClass());
        }
    }

    protected void readMenu(Class<? extends Object> clazz) {
        Method[] methods = clazz.getMethods();
        List<Menu> menuList = new ArrayList<>();
        RequestMapping controllerMapping = clazz.getAnnotation(RequestMapping.class);
        String controllerUrl = "";
        if (controllerMapping != null) {
            controllerUrl = AuthAssemblyResolver.readUrlFromRequestMapping(controllerMapping);
            controllerUrl = controllerUrl == null ? "" : controllerUrl.trim();
        }
        for (Method method : methods) {
            com.chinaj.portal.auth.annotation.Menu menuAnno = method.getAnnotation(com.chinaj.portal.auth.annotation.Menu.class);
            if (menuAnno == null) {
                continue;
            }
            Menu menu = new Menu();
            menu.setEnable(true);
            menu.setTitle(menuAnno.title());
            menu.setOrderIndex(0);
            menu.setPid(0);
            if (menuAnno.url() != null && !menuAnno.url().trim().equals("")) {
                log.debug("解析到目录:{},路径:{}", menuAnno.title(), menuAnno.url());
                menu.setUrl(menuAnno.url());
                menuList.add(menu);
                continue;
            }
            String authCode = clazz.getName() + "." + method.getName();
            Auth auth = method.getAnnotation(Auth.class);
            if (auth != null) {
                menu.setAuthCode(authCode);
            }
            RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
            if (methodMapping == null) {
                log.error("解析目录路径同时缺少url和RequestMapping,{}", authCode);
                continue;
            }
            String url = controllerUrl + AuthAssemblyResolver.readUrlFromRequestMapping(methodMapping);
            menu.setUrl(url);
            menuList.add(menu);
            log.debug("解析到目录:{},路径:{}", menuAnno.title(), url);
        }
        menuList = compareMenu(menuList);
        menuService.addAllMenu(menuList);
    }

    /**
     * 比较库里已有的目录和扫描到的目录，去掉重复的，保留新增的
     * 
     * @param menuList
     *            扫描到的目录列表
     * @return
     */
    protected List<Menu> compareMenu(List<Menu> menuList) {
        List<Menu> allMenu = menuService.getAllMenu();
        if (allMenu == null || allMenu.isEmpty()) {
            return menuList;
        }
        Iterator<Menu> iterator = menuList.iterator();
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            for (Menu existMenu : allMenu) {
                if (menu.getUrl().equals(existMenu.getUrl())) {
                    iterator.remove();
                }
            }
        }
        return menuList;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
}
