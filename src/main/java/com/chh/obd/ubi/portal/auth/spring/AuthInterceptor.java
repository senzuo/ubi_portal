package com.chh.obd.ubi.portal.auth.spring;

import com.alibaba.fastjson.JSON;
import com.chh.obd.ubi.portal.auth.annotation.Auth;
import com.chh.obd.ubi.portal.common.response.RestCode;
import com.chh.obd.ubi.portal.common.response.RestResponse;
import com.chh.obd.ubi.portal.common.response.RestUtil;
import com.chh.obd.ubi.portal.common.token.Token;
import com.chh.obd.ubi.portal.common.token.TokenUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Niow on 2016/12/27.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private AuthManager authManager;

    private String forbiddenUrl;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        log.debug("拦截到方法" + handlerMethod.getMethod().getName());
        Auth anno = handlerMethod.getMethodAnnotation(Auth.class);
        if (anno == null) {
            return true;
        }
        boolean isRestful = false;
        if (handlerMethod.getReturnType().equals(RestResponse.class)) {
            isRestful = true;
        }
        Token token = TokenUtil.getUser(httpServletRequest);
        RestResponse rs = RestUtil.getResponse();
        if (token == null) {
            if (log.isDebugEnabled()) {
                log.debug("没有该用户登录信息");
            }
            response(isRestful, rs, httpServletResponse);
            return false;
        }
        String authCode = handlerMethod.getBean().getClass().getName() + "." + handlerMethod.getMethod().getName();
        if (!authManager.checkUserAuth(authCode, token.getUserId())) {
            if (log.isDebugEnabled()) {
                log.debug("用户:{}无权限访问被拦截:{}", token.getUserId(), authCode);
            }
            rs.setRestCode(RestCode.OPTION_FORBIDDEN);
            response(isRestful, rs, httpServletResponse);
            return false;
        }
        return true;
    }




    /**
     * 回写json数据
     *
     * @param restResponse
     * @param response
     * @throws IOException
     */
    private void writeBackJson(RestResponse restResponse, HttpServletResponse response) throws IOException {
        String json = JSON.toJSONString(restResponse);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        IOUtils.write(json.getBytes(), response.getWriter());
    }

    private void response(boolean isRestful, RestResponse restResponse, HttpServletResponse response) {
        try {
            if (isRestful) {
                writeBackJson(restResponse, response);
            } else {
                response.sendRedirect(forbiddenUrl);
            }
        } catch (IOException e) {
            log.error("鉴权拦截器返回结果异常", e);
        }
    }

    public String getForbiddenUrl() {
        return forbiddenUrl;
    }

    public void setForbiddenUrl(String forbiddenUrl) {
        this.forbiddenUrl = forbiddenUrl;
    }
}
