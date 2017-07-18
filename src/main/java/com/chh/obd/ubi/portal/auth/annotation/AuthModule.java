package com.chh.obd.ubi.portal.auth.annotation;


import com.chh.obd.ubi.support.auth.model.AuthorityModule;

import java.lang.annotation.*;

/**
 * @author Niow
 * @Description: 权限模块，不直接参与鉴权拦截，只对auth进行分类管理
 * @date 2017年3月22日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthModule {

    String code() default AuthorityModule.MODULE_COMMON;

    String desc() default "公共模块";
}
