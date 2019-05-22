package com.sr03.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xthml" })
public class AuthFilter implements Filter {
    public AuthFilter() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletException request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) resp;
            HttpSession sess = req.getSession(false);

            String reqURI = req.getRequestURI();
            if (reqURI.contains("/login.xhtml")
                    || (sess != null && sess.getAttribute("username") != null)
                    || reqURI.contains("/public/")
                    || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
            }
            else {
                resp.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
