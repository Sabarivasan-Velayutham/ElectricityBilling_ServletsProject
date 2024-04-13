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

@WebServlet("/user-action/paybill")
public class UserActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userDAO = new UserDAO(servletContext);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        String paymentMethod = request.getParameter("paymentMethod");

        // Check if the bill amount is zero
        double billAmount = userDAO.getBillAmount(userId);
        if (billAmount == 0.0) {
            // Bill amount is zero, redirect to billupdation.jsp
            response.sendRedirect(request.getContextPath() + "/billupdation.jsp");
            return;
        }

        // Check if the bill for this user has already been paid
        boolean isBillPaid = userDAO.isBillPaid(userId);
        if (isBillPaid) {
            // Bill has already been paid, redirect to billpaidstatus.jsp
            response.sendRedirect(request.getContextPath() + "/billpaidstatus.jsp");
            return;
        }

        // Process payment
        boolean paymentProcessed = processPayment(userId, paymentMethod);

        if (paymentProcessed) {
            // Update bill status to "paid"
            boolean isSuccess = userDAO.updateBillStatus(userId, "paid");
            if (isSuccess) {
                response.sendRedirect(request.getContextPath() + "/payment-success.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/payment-failure.jsp");
            }
        } else {
            // Payment processing failed
            response.sendRedirect(request.getContextPath() + "/payment-failure.jsp");
        }
    }

    private boolean processPayment(int userId, String paymentMethod) {
        if ("credit".equals(paymentMethod) || "upi".equals(paymentMethod) || "debitcard".equals(paymentMethod)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/listBills":
                    listUserBills(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/paybill.jsp").forward(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private int getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (int) session.getAttribute("userId");
    }

    private void listUserBills(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int userId = getUserIdFromSession(request);
        User listUser = userDAO.selectUser(userId);
        request.setAttribute("listUser", listUser);
		System.out.println("UserAction file -> User ID from session: " + userId); // Check if user ID is retrieved correctly
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_action.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
