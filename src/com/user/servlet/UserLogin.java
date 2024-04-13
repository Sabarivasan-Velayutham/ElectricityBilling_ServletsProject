package com.user.servlet;

import java.io.IOException;

import com.dao.UserDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user")
public class UserLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userDAO = new UserDAO(servletContext);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String conditional = (String) request.getParameter("button");

		if (conditional.contentEquals("Login")) {
			String inputUsername = request.getParameter("username");
			String inputPassword = request.getParameter("password");
			int userId = userDAO.isValidUser(inputUsername, inputPassword);
			if (userId!=-1) {
				HttpSession session = request.getSession(); // Create a new session or get existing session
				session.setAttribute("Username", inputUsername); // Store username in the session
				session.setAttribute("userId", userId);
				session.setAttribute("role", "user");

				response.sendRedirect("user_action.jsp"); // Redirect to login success page
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/user-error.jsp");
				rd.include(request, response);
			}
		}

		if (conditional.contentEquals("Logout")) {
			HttpSession session = request.getSession();
			String user = (String) session.getAttribute("Username");
			// check in session storage
			if (user != null) {
				session.removeAttribute("Username");
				session.removeAttribute("userId");
				session.removeAttribute("role");
				session.invalidate();
				System.out.println("Logged out, removed column from (session): " + user);
			}
			// redirect to login page
			response.sendRedirect("user_login.jsp");
		}
    }
}
