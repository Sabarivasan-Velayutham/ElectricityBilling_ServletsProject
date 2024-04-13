package com.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter({
	"/admin_action.jsp","/list", "/new", "/searchById", "/searchByName", "/sort", "/edit", "/delete", 
	"/user_action.jsp", "/listBills", "/paybill.jsp" })
public class AuthorizationFilter implements Filter {

//    private static final Set<String> PUBLIC_PATH = new HashSet<>(Arrays.asList("/login"));
    private static final Set<String> ADMIN_PATH = new HashSet<>(Arrays.asList(
    		"/admin_action.jsp", "/list", "/new", "/searchById", "/searchByName", "/sort", "/edit", "/delete"));
    private static final Set<String> USER_PATH = new HashSet<>(Arrays.asList(
    		"/user_action.jsp", "/listBills", "/paybill.jsp"));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    		throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        System.out.println("URL : "+uri);

//        if (isPublicPath(uri)) {
//            filterChain.doFilter(req, res);
//            return;
//        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (("admin".equals(role) && isAdminPath(uri)) || ("user".equals(role) && isUserPath(uri))) {
            filterChain.doFilter(req, res);
        } else {
        	res.sendRedirect("unauthorized-error.jsp");
        }
    }

//    private boolean isPublicPath(String uri) {
//    	System.out.println(PUBLIC_PATH.stream().anyMatch(uri::contains));
//        return PUBLIC_PATH.stream().anyMatch(uri::contains);
//    }

    private boolean isAdminPath(String uri) {
    	System.out.println(ADMIN_PATH.stream().anyMatch(uri::contains));
        return ADMIN_PATH.stream().anyMatch(uri::contains);
    }

    private boolean isUserPath(String uri) {
    	System.out.println(USER_PATH.stream().anyMatch(uri::contains));
        return USER_PATH.stream().anyMatch(uri::contains);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // Initialization code, if any
    }

    @Override
    public void destroy() {
        // Cleanup code, if any
    }
}


