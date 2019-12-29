package com.luoyifan.voyage.controller.interceptor;

import com.luoyifan.voyage.property.VoyageProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author EvanLuo
 */
@Component
public class BaseAttributeInterceptor implements HandlerInterceptor {
    @Autowired
    private VoyageProperty voyageProperty;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("appName",voyageProperty.getAppName());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        request.setAttribute("appName",voyageProperty.getAppName());
    }

}
