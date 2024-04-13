package com.user.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");

     // Get the database connection from the servlet context
        Connection con = (Connection) getServletContext().getAttribute("dbConnection");

        try {
            if (con != null) {
                System.out.println("Connected to the database.");

                // Insert user data into the userinfo table
                String insertUserSQL = "INSERT INTO userinfo (username, password, address, billamount, billstatus) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = con.prepareStatement(insertUserSQL)) {
                    // Set default values for bill amount and bill status
                    double billamount = 0.0;
                    String billstatus = "notpaid";

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, address);
                    preparedStatement.setDouble(4, billamount); // Set default bill amount
                    preparedStatement.setString(5, billstatus); // Set default bill status

                    preparedStatement.executeUpdate();
                    System.out.println("User registered successfully with username: " + username);

                    // Add success message to request attribute
                    request.setAttribute("successMessage", "Registration successful! Please login.");

                    // Forward to UserLogin.jsp after successful registration
                    request.getRequestDispatcher("user_login.jsp").forward(request, response);
                }
            } else {
                System.out.println("Failed to connect to the database.");
                response.sendRedirect("user-error.jsp");
            }
        } catch (SQLException | IOException | ServletException e) {
            e.printStackTrace();
            response.sendRedirect("user-error.jsp");
        }
    }
}
