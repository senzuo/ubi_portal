package com.chh.obd.ubi.portal.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Niow on 2016/12/16.
 */
@Controller
public class MainViewController {

    public static final String PAGE_PATH = "framework/";

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "ffffffffffffffffffffffuck off";
    }

    @RequestMapping(value = "/")
    public String mainIndex() {
        return PAGE_PATH + "index";
    }

    @RequestMapping(value = "/index")
    public String toIndexPage() {

        return PAGE_PATH + "index";
    }
}
