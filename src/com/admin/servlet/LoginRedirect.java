package com.admin.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginRedirectServlet")
public class LoginRedirect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = request.getParameter("role");

        if ("admin".equals(role)) {
            response.sendRedirect("admin_login.jsp");
        } else if ("user".equals(role)) {
            response.sendRedirect("user_login.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
        
    }
}
