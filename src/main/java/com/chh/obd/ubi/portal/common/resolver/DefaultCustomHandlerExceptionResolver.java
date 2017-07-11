package com.chh.obd.ubi.portal.common.resolver;



import com.chh.obd.ubi.portal.common.response.RestCode;
import com.chh.obd.ubi.portal.common.response.RestResponse;
import com.chh.obd.ubi.portal.common.response.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Niow on 2016/3/22.
 */


public class DefaultCustomHandlerExceptionResolver implements HandlerExceptionResolver {

    public static final Logger log = LoggerFactory.getLogger(DefaultCustomHandlerExceptionResolver.class);

    @Autowired
    private MappingJackson2HttpMessageConverter converter;
    //= new MappingJackson2HttpMessageConverter();

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("错误处理", e);
        RestResponse response = RestUtil.getResponse(RestCode.ERROR, "服务器异常");
        MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
        ServletServerHttpResponse re = new ServletServerHttpResponse(httpServletResponse);
        try {
            converter.write(response, mediaType, re);
        } catch (IOException e1) {
            log.error("错误信息回写失败", e1);
        }
        return new ModelAndView();
    }
}
