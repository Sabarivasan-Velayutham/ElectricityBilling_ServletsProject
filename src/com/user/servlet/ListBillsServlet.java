package com.user.servlet;

import com.dao.UserDAO;
import com.entity.Admin;
import com.entity.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/listBills")
public class ListBillsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		userDAO = new UserDAO(servletContext);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        HttpSession session = request.getSession();
	        Integer userId = (Integer) session.getAttribute("userId"); // Use Integer instead of int to handle null
	        System.out.println("Servlet file -> User ID from session: " + userId); // Check if user ID is retrieved correctly
	        
	        if (userId != null && userId != -1) { // Check if userId is not null and not -1
	            User listUser = userDAO.selectUser(userId);
	            request.setAttribute("listUser", listUser);
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/user_action.jsp");
	            dispatcher.forward(request, response);
	        } else {
	            response.sendRedirect("user_login.jsp");
	        }
	    } catch (NullPointerException e) {
	        // Handle the NullPointerException gracefully
	        e.printStackTrace(); // You can log the exception for debugging purposes
	        response.sendRedirect("user_login.jsp");
	    }
	}

}
