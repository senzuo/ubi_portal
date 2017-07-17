package com.chh.obd.ubi.portal.test;

/**
 * Created by 申卓 on 2017/7/13.
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    User user = new User("name","msg");
    //入口
    @RequestMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("user",user);
        return "aa";
    }

    //提交表单后进行数据读取，并将数据传出
    @RequestMapping(value = "/bb",method = RequestMethod.POST)
    public String bb(User user,Model model) {
//    public String bb(@RequestParam String name, @RequestParam String msg, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("message", ",welcome");
        return "bb";
    }
}