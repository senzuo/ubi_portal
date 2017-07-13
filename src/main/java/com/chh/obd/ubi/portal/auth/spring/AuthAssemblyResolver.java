package com.chh.obd.ubi.portal.auth.spring;

import com.chinaj.portal.auth.AuthorityValidException;
import com.chinaj.portal.auth.annotation.Auth;
import com.chinaj.portal.auth.annotation.AuthModule;
import com.chinaj.portal.auth.model.Authority;
import com.chinaj.portal.auth.model.AuthorityModule;
import com.chinaj.portal.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Niow
 * @ClassName: com.chinaj.portal.auth.spring.AuthAssemblyResolver
 * @Description: TODO
 * @date 2017年3月22日
 */
public class AuthAssemblyResolver implements ApplicationListener<ContextRefreshedEvent> {

    public static final Logger log = LoggerFactory.getLogger(AuthAssemblyResolver.class);

    /**
     * 权限加载装备开关，默认关闭，应该在验证环境中开启生成sql数据插入到生产环境中去
     */
    private boolean enable = false;

    Map<String, AuthorityModule> authorityModuleMap = new HashMap<>();

    Map<String, Authority> authorityMap = new HashMap<>();

    @Autowired
    private AuthService authService;

    private MenuResolver menuResolver;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if(event.getApplicationContext().getParent() != null){
//            return;
//        }
        assembly(event.getApplicationContext());
        if (menuResolver != null) {
            log.info("开启目录扫描功能");
            menuResolver.resolve(event.getApplicationContext());
        }
    }

    /**
     * @param applicationContext
     * @return void
     * @Description: 装配权限
     * @author Niow
     */
    public void assembly(ApplicationContext applicationContext) {
        try {
            if (!enable) {
                log.info("权限注释生成解析未开启");
                return;
            }
            log.info("开始进行权限注释扫描并解析入库");
            Map<String, Object> controllerMap = applicationContext.getBeansWithAnnotation(Controller.class);
            try {
                load(controllerMap.values());
            } catch (Exception e) {
                log.error("权限注解解析生产异常", e);
            }
            authorityModuleMap = compareModule(authorityModuleMap);
            for (AuthorityModule module : authorityModuleMap.values()) {
                log.info("创建权限模块code:{},desc:{}", module.getModuleCode(), module.getDescription());
                authService.createAuthModule(module);
            }
            authorityMap = compareAuthority(authorityMap);
            for (Authority authority : authorityMap.values()) {
                log.debug("创建权限method:{},module:{},desc:{}", authority.getAuthCode(), authority.getModuleCode(), authority.getDescription());
                authService.createAuth(authority);
            }
        } catch (Exception e) {
            log.error("权限模块解析器加载失败", e);
        }
    }

    /**
     * @param beans
     *            所有Controller的实例列表
     * @return void
     * @throws Exception
     * @Description: 加载权限注解
     * @author Niow
     */
    public void load(Collection<Object> beans) throws Exception {

        for (Object bean : beans) {
            AuthorityModule authorityModule = readAuthModule(bean.getClass());
            if (authorityModule != null) {
                log.debug("扫描到有AuthModule注解的类{}", bean.getClass().getName());
                authorityModuleMap.put(authorityModule.getModuleCode(), authorityModule);
            }
            readAuth(bean.getClass(), authorityMap, authorityModule);
        }

    }

    /**
     * @param fromAnnotation
     * @return Map<String,Authority>
     * @Description: 对比从注解解析得到的权限和当前数据库中的差异结果
     * @author Niow
     */
    protected Map<String, Authority> compareAuthority(Map<String, Authority> fromAnnotation) {
        Map<String, Authority> additionalMap = new HashMap<>();
        Map<String, Authority> fromDatabaseMap = authService.getAllAuthorityMap();
        for (Authority authority : fromAnnotation.values()) {
            if (fromDatabaseMap.containsKey(authority.getAuthCode())) {
                continue;
            }
            additionalMap.put(authority.getAuthCode(), authority);
        }
        return additionalMap;
    }

    /**
     * @param fromAnnotation
     * @return Map<String,AuthorityModule>
     * @Description: 对比从注解解析得到的权限模块和当前数据库中的差异结果
     * @author Niow
     */
    protected Map<String, AuthorityModule> compareModule(Map<String, AuthorityModule> fromAnnotation) {
        Map<String, AuthorityModule> additionalMap = new HashMap<>();
        Map<String, AuthorityModule> fromDatabaseMap = authService.getAllAuthorityModuleMap();
        for (AuthorityModule module : fromAnnotation.values()) {
            if (fromDatabaseMap.containsKey(module.getModuleCode())) {
                continue;
            }
            additionalMap.put(module.getModuleCode(), module);
        }
        return additionalMap;
    }

    /**
     * @param clazz
     * @return AuthorityModule
     * @Description: 读取权限模块
     * @author Niow
     */
    protected AuthorityModule readAuthModule(Class<? extends Object> clazz) {
        AuthModule anno = (AuthModule) clazz.getAnnotation(AuthModule.class);
        if (anno != null) {
            AuthorityModule authorityModule = new AuthorityModule();
            authorityModule.setDescription(anno.desc());
            authorityModule.setModuleCode(anno.code());
            return authorityModule;
        }
        return null;
    }

    /**
     * @param clazz
     * @param authorityMap
     * @param authorityModule
     * @return void
     * @throws AuthorityValidException
     * @Description: 读取解析Auth标签
     * @author Niow
     */
    protected void readAuth(Class<? extends Object> clazz, Map<String, Authority> authorityMap, AuthorityModule authorityModule) throws AuthorityValidException {
        Method[] methods = clazz.getMethods();
        RequestMapping controllerMapping = clazz.getAnnotation(RequestMapping.class);
        String controllerUrl = "";
        if (controllerMapping != null) {
            controllerUrl = readUrlFromRequestMapping(controllerMapping);
            controllerUrl = controllerUrl == null ? "" : controllerUrl.trim();
        }
        for (Method method : methods) {
            Auth anno = method.getAnnotation(Auth.class);
            if (anno == null) {
                continue;
            }
            log.debug("扫描到有Auth注解的方法{}.{}", clazz.getName(), method.getName());
            Authority authority = new Authority();
            authority.setAuthCode(clazz.getName() + "." + method.getName());
            authority.setDescription(anno.value());
            if (!StringUtils.isEmpty(anno.module())) {
                authority.setModuleCode(anno.module());
            } else if (authorityModule != null) {
                authority.setModuleCode(authorityModule.getModuleCode());
            } else {
                authority.setModuleCode(AuthorityModule.MODULE_COMMON);
            }
            if (authorityMap.containsKey(authority.getAuthCode())) {
                throw new AuthorityValidException("权限方法" + authority.getAuthCode() + "存在重复，请检查配置方法" + clazz.getName() + "[" + method.getName() + "]");
            }
            RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
            if (methodMapping != null) {
                String methodUrl = readUrlFromRequestMapping(methodMapping);
                if (methodUrl != null) {
                    authority.setUrl(controllerUrl + methodUrl);
                }
            }
            authorityMap.put(authority.getAuthCode(), authority);
        }
    }

    /**
     * @param requestMapping
     * @return String
     * @Description: 从RequestMapping中读取Url
     * @author Niow
     */
    public static String readUrlFromRequestMapping(RequestMapping requestMapping) {
        String[] mapping = requestMapping.value();
        if (mapping == null || mapping.length == 0) {
            return null;
        }
        String url = mapping[0];
        if (!url.equals("") && !url.startsWith("/")) {
            url = "/" + url;
        }
        return url;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public MenuResolver getMenuResolver() {
        return menuResolver;
    }

    public void setMenuResolver(MenuResolver menuResolver) {
        this.menuResolver = menuResolver;
    }
}
