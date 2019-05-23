package com.sr03.filters;

import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {
    public AuthenticationFilter() {

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            HttpSession session = request.getSession(false);

            String loginURI = request.getContextPath() + "/login.xhtml";

            boolean loggedIn = session != null && session.getAttribute("email") != null;
            boolean loginRequest = request.getRequestURI().equals(loginURI);
            boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);

            if (loggedIn || loginRequest || resourceRequest) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(loginURI);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
