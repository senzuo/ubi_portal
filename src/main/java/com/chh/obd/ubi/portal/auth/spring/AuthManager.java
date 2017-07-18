package com.chh.obd.ubi.portal.auth.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chh.obd.ubi.support.auth.model.Authority;
import com.chh.obd.ubi.support.auth.service.AuthService;
import com.chh.obd.ubi.support.common.cache.CacheFactory;
import com.chh.obd.ubi.support.common.cache.MapCache;
import com.chh.obd.ubi.support.role.model.Role;
import com.chh.obd.ubi.support.role.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AuthManager implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(AuthManager.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private RoleService roleService;

    /**
     * <roleId,Map<authCode,Authority>>
     */
    private MapCache<Long, Map<String, Authority>> roleAuthCache = null;

    /**
     * <userId,Map<roleId,Role>>
     */
    private MapCache<Long, Map<Long, Role>> userRoleCache = null;

    // /**
    // * userId,Map<authCode,Authority>>
    // */
    // private MapCache<String, Map<String, Authority>> userAuthCache = null;

    /**
     * 初始化的时候缓存类型
     */
    private int cacheType = 0;

    /**
     * 是否懒加载权限信息，当调用到的时候再去加载
     */
    private boolean isLaszyLoad = false;

    private static AuthManager instance = null;

    public AuthManager() {
        instance = this;
    }

    /**
     * 获取本服务实例，此方法不安全，只适合在系统启动完成后的位置调用，如页面标签功能
     *
     * @return 服务实例
     */
    public static final AuthManager getInstance() {
        return instance;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void init() {
        this.cacheType = MapCache.TYPE_LOCAL_MAP;
        roleAuthCache = CacheFactory.createCache(cacheType);
        userRoleCache = CacheFactory.createCache(cacheType);
        // userAuthCache = CacheFactory.createCache(cacheType);
        if (!isLaszyLoad) {
            loadAllAuth();
        }
    }

    public synchronized void loadUserAuth(String userId) {
        // List<Role> roles = roleService.findRoleByUserId(userId);
        // if (roles == null || roles.isEmpty()) {
        // log.info("此用户没有任何权限" + userId);
        // return;
        // }
        // userRoleCache.put(userId, new HashSet<Role>(roles));
        // Set<Authority> userAuthSet = userAuthCache.get(userId);
        // if (userAuthSet == null) {
        // userAuthSet = new HashSet<>();
        // }
        // for (Role role : roles) {
        // Set<Authority> authorities = roleAuthCache.get(role.getId());
        // if (authorities != null) {
        // continue;
        // }
        // Map<String, Authority> authorityMap =
        // authService.findAuthByRoleId(role.getId());
        // if (authorityMap == null) {
        // continue;
        // }
        // authorities = new HashSet<>();
        // authorities.addAll(authorityMap.values());
        // roleAuthCache.put(role.getId(), authorities);
        // userAuthSet.addAll(authorities);
        // }
        // userAuthCache.put(userId,userAuthSet);
    }

    /**
     * 一次性加载所有用户权限到缓存中
     */
    protected synchronized void loadAllAuth() {
        Map<Long, Map<String, Authority>> roleAuthMap = authService.getAllRoleAuthorityMap();
        log.info("加载到{}个角色权限信息", roleAuthMap.size());
        roleAuthCache.putAll(roleAuthMap);

        Map<Long, Map<Long, Role>> userRoleMap = roleService.getAllUserRoleMap();
        log.info("加载了所有用户的角色信息");
        userRoleCache.putAll(userRoleMap);

        // for (String userId : userRoleCache.getAllKey()) {
        // Map<Long, Role> roles = userRoleCache.get(userId);
        // Map<String, Authority> userAuthSet = new HashMap<String,
        // Authority>();
        // for (Role role : roles.values()) {
        // Map<String, Authority> roleAuthSet = roleAuthMap.get(role.getId());
        // if (roleAuthSet == null) {
        // continue;
        // }
        // userAuthSet.putAll(roleAuthSet);
        // }
        // userAuthCache.put(userId, userAuthSet);
        // }
        // log.info("加载了所有用户的权限信息");
    }

    /**
     * 从新加载角色权限
     *
     * @param roleId
     */
    public void reloadRoleAuth(Long roleId) {
        Map<String, Authority> roleAuthMap = authService.findAuthByRoleId(roleId);
        log.info("重载到[{}]角色权限信息", roleId);
        roleAuthCache.put(roleId, roleAuthMap);
    }

    /**
     * 从新加载用户角色
     *
     * @param userId
     */
    public void reloadUserRole(Long userId) {
        List<Role> userRoles = roleService.findRoleByUserId(userId);
        Map<Long, Role> userRoleMap = new HashMap<>();
        if (userRoles == null || userRoles.isEmpty()) {
            userRoleCache.put(userId, userRoleMap);
            return;
        }
        log.info("重载了用户[{}]的角色信息", userId);
        for (Role role : userRoles) {
            userRoleMap.put(role.getId(), role);
        }
        userRoleCache.put(userId, userRoleMap);
    }


    /**
     * @param userId
     * @return Map<String,Authority>
     * @Description:获取用户权限集合
     * @author Niow
     */
    public Map<String, Authority> getUserAuth(long userId) {
        Map<String, Authority> userAuthorityMap = new HashMap<>();
        Map<Long, Role> userRoleMap = userRoleCache.get(userId);
        if (userRoleMap == null || userRoleMap.isEmpty()) {
            return userAuthorityMap;
        }
        for (Long roleId : userRoleMap.keySet()) {
            Map<String, Authority> authorityMap = roleAuthCache.get(roleId);
            if (authorityMap == null || authorityMap.isEmpty()) {
                continue;
            }
            userAuthorityMap.putAll(authorityMap);
        }
        return userAuthorityMap;
    }

    /**
     * @param roleId
     * @return Set<Authority>
     * @Description: 获取角色权限集合
     * @author Niow
     */
    public Map<String, Authority> getRoleAuth(Long roleId) {
        return roleAuthCache.get(roleId);
    }

    /**
     * @param userId
     * @param roleId
     * @return boolean
     * @Description: 检测用户是否拥有指定角色
     * @author Niow
     */
    public boolean checkUserRole(Long userId, int roleId) {
        Map<Long, Role> roleMap = userRoleCache.get(userId);
        if (roleMap == null || roleMap.isEmpty()) {
            return false;
        }
        return roleMap.containsKey(roleId);
    }

    /**
     * @param authCode
     * @param userId
     * @return boolean
     * @Description: 检测用户是否拥有指定权限, 通过遍历角色对应的权限判断
     * @author Niow
     */
    public boolean checkUserAuth(String authCode, Long userId) {
        Map<Long, Role> userRoleMap = userRoleCache.get(userId);
        if (userRoleMap == null || userRoleMap.isEmpty()) {
            return false;
        }
        for (Long roleId : userRoleMap.keySet()) {
            Map<String, Authority> authorityMap = roleAuthCache.get(roleId);
            if (authorityMap == null || authorityMap.isEmpty()) {
                continue;
            }
            if (authorityMap.containsKey(authCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param authCode
     * @param roleId
     * @return boolean
     * @Description: 检测角色是否拥有指定权限
     * @author Niow
     */
    public boolean checkRoleAuth(String authCode, Long roleId) {
        Map<String, Authority> authorityMap = roleAuthCache.get(roleId);
        if (authorityMap == null || authorityMap.isEmpty()) {
            return false;
        }
        return authorityMap.containsKey(authCode);
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

}
