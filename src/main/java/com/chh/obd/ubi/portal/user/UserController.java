package com.chh.obd.ubi.portal.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Niow on 2016/11/22.
 */
@Controller
@RequestMapping("/user/view")
public class UserController {

//    public static void main(String[] args) throws Exception {
//        // 项目中jar包所在物理路径
//        String jarName = "D:\\support-user-0.0.1_d.jar";
//// 项目中war包所在物理路径
////String jarName = "C:\\Users\\Administrator\\Desktop\\my.war";
//        JarFile jarFile = new JarFile(jarName);
//        Enumeration<JarEntry> entrys = jarFile.entries();
//        while (entrys.hasMoreElements()) {
//            JarEntry jarEntry = entrys.nextElement();
//            System.out.println(jarEntry.getName());
//        }
//    }

    @RequestMapping(value = "/manage")
    public String toUserManage() {
        return "/framework/user/userManage";
    }

    @RequestMapping(value = "/info")
    public String toUserInfo() {
        return "/framework/user/userInfo";
    }
}
