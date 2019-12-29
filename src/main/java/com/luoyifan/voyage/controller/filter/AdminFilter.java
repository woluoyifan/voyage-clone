package com.luoyifan.voyage.controller.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author EvanLuo
 */
public class AdminFilter extends OncePerRequestFilter {

    private static final String ADMIN_AUTHENTICATION = AdminFilter.class.getName() + "ADMIN_AUTHENTICATION";

    private String adminPassword;

    public AdminFilter(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (!requestURI.matches("^/admin($|/.*)")) {
            filterChain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession();
        String ps = request.getParameter("ps");
        if ("/admin/login".equals(requestURI)) {
            if (adminPassword.equals(ps)) {
                session.setAttribute(ADMIN_AUTHENTICATION, true);
                response.sendRedirect("/admin");
            } else {
                filterChain.doFilter(request, response);
            }
            return;
        }
        if ("/admin/logout".equals(requestURI)) {
            session.removeAttribute(ADMIN_AUTHENTICATION);
        }
        if (session.getAttribute(ADMIN_AUTHENTICATION) == null) {
            response.sendRedirect("/admin/login");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
