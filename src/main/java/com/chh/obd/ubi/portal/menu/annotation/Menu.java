package com.chh.obd.ubi.portal.menu.annotation;

import java.lang.annotation.*;

/**
 * @author Niow
 * @Description: 菜单注解目录
 * @date 2017年3月22日
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Menu {

    // 目录标题
    String title() default "";

    // URI后面跟随的参数
    String param() default "";

    // 完整的URL，如果不设置这个值则从requestMapping反射获取得到+param为完整的菜单URL
    String url() default "";
}
