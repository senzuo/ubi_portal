package com.chh.obd.ubi.portal.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * 无参数
     * 通过 ModelAndView 返回
     * @return
     */
    @RequestMapping(value = "/test0713")
    public ModelAndView test0713() {
        ModelAndView modelAndView = new ModelAndView("test/test0713");

        List<MyUser> users = new ArrayList<>();
        MyUser user = null;
        user = new MyUser();
        user.setId(1);
        user.setName("设置");
        users.add(user);

        user = new MyUser();
        user.setId(1);
        user.setName("设置");
        users.add(user);

        user = new MyUser();
        user.setId(1);
        user.setName("设置");
        users.add(user);


        modelAndView.addObject("users",users);
        return modelAndView;
    }


    /**
     * 通过 Model参数取值
     * 返回String
     */
    @RequestMapping("/test0713_1")
    public String index1(Model model){
        MyUser user = new MyUser();
        user.setName("语言");
        user.setId(222);
        model.addAttribute("user",user);
        return "test/test0713_1";
    }

    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    @ResponseBody
    public MyUser valid(){
        MyUser myUser = new MyUser();
        myUser.setId(911);
        myUser.setName("285");
        return myUser;
    }

    @RequestMapping(value = "/0713form",method = RequestMethod.GET)
    public String form(){

        return "test/test0713_2";
    }

    @RequestMapping(value = "/0713getPara",method = RequestMethod.POST)
    public String getPara(@RequestParam String id,@RequestParam String name, Model model){

        List<MyUser> users = new ArrayList<>();
        System.out.println("id is " + id);
        System.out.println("name is " + name);
//        users.add(myUser);
//        System.out.println(myUser);
        model.addAttribute("users",users);
        return "test/test0713";
    }

}
