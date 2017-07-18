package com.chh.obd.ubi.portal.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by niow on 2017/7/13.
 */
@Controller
@RequestMapping("/menu/view")
public class MenuController {

    @RequestMapping(value = "/manage")
    public String toMenuManage() {
        return "/framework/menu/menuManage";//返回字符串时是根据目录路径来寻找的，默认是在WIB-INF目录下开始
    }
}
