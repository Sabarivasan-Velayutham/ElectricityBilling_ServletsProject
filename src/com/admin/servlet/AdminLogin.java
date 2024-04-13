package com.admin.servlet;

import java.io.IOException;

import com.dao.AdminDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminDAO adminDAO;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        adminDAO = new AdminDAO(servletContext);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String conditional = (String) request.getParameter("button");

		if (conditional.contentEquals("Login")) {
			String inputUsername = request.getParameter("username");
			String inputPassword = request.getParameter("password");

			if (adminDAO.isAdminUser(inputUsername, inputPassword)) {
				HttpSession session = request.getSession(); // Create a new session or get existing session
				session.setAttribute("Username", inputUsername); // Store username in the session
				session.setAttribute("role", "admin");

				response.sendRedirect("admin_action.jsp"); // Redirect to login success page
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin-error.jsp");
				rd.include(request, response);
			}
		}

		if (conditional.contentEquals("Logout")) {
			HttpSession session = request.getSession();
			String user = (String) session.getAttribute("Username");
			// check in session storage
			if (user != null) {
				session.removeAttribute("Username");
				session.removeAttribute("role");
				session.invalidate();
				System.out.println("Logged out, removed column from (session): " + user);
			}
			// redirect to login page
			response.sendRedirect("admin_login.jsp");
		}
	}
    
}
