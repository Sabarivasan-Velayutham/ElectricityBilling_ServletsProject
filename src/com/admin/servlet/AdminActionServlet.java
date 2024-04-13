package com.admin.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dao.AdminDAO;
import com.entity.Admin;

@WebServlet("/")
public class AdminActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO adminDAO;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        adminDAO = new AdminDAO(servletContext);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertUser(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                case "/searchById":
                    searchUserById(request, response);
                    break;
                case "/searchByName":
                    searchUserByName(request, response);
                    break;
                case "/sort":
                    sortUsers(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void searchUserById(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    String userIdParam = request.getParameter("userId");
    if (userIdParam == null || userIdParam.trim().isEmpty()) {
        request.setAttribute("errorMessage", "User ID cannot be empty");
        listUser(request, response);
        return;
    }

    try {
        int userId = Integer.parseInt(userIdParam);
        if (userId <= 0) {
            request.setAttribute("errorMessage", "Invalid user ID..Please enter valid userID");
            listUser(request, response);
            return;
        }

        List<Admin> userList = adminDAO.searchUserById(userId);
        if (userList.isEmpty()) {
            request.setAttribute("errorMessage", "No user found with the provided ID");
            listUser(request, response);
            return;
        }

        request.setAttribute("listUser", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_action.jsp");
        dispatcher.forward(request, response);
    } catch (NumberFormatException e) {
        request.setAttribute("errorMessage", "Invalid user ID");
        listUser(request, response);
    }
  }

        

    private void searchUserByName(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String userName = request.getParameter("userName");
        if (userName == null || userName.trim().isEmpty()) {
            request.setAttribute("errorMessage", "User name cannot be empty");
            listUser(request, response);
            return;
        }

        // Validate user name using isValidString method
        if (!isValidString(userName)) {
            request.setAttribute("errorMessage", "Invalid username..Please enter valid username");
            listUser(request, response);
            return;
        }

        List<Admin> userList = adminDAO.searchUserByName(userName);
        if (userList.isEmpty()) {
            request.setAttribute("errorMessage", "No user found with the provided name");
            listUser(request, response);
            return;
        }

        request.setAttribute("listUser", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_action.jsp");
        dispatcher.forward(request, response);
    }


    private void sortUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder"); 
        
        if (sortBy == null || sortBy.isEmpty() || sortOrder == null || sortOrder.isEmpty()) {
            request.setAttribute("errorMessage", "Please choose any options to sort by");
            listUser(request, response);
            return;
        }

        List<Admin> userList = adminDAO.sortUsers(sortBy, sortOrder); 
        request.setAttribute("listUser", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_action.jsp");
        dispatcher.forward(request, response);
    }
    
    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int page = 1;
        int recordsPerPage = 3;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        
        List<Admin> listUser = adminDAO.selectAllUsers(recordsPerPage, (page - 1) * recordsPerPage);
        int noOfRecords = adminDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("listUser", listUser);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_action.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {   	
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Admin existingUser = adminDAO.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String billAmountStr = request.getParameter("billamount");
        String billStatus = "notpaid";

        // Validate input
        if (name == null || name.isEmpty() || password == null || password.isEmpty() ||
            address == null || address.isEmpty() || billAmountStr == null || billAmountStr.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required");
            showNewForm(request, response);
            return;
        }

        double billAmount;
        try {
            billAmount = Double.parseDouble(billAmountStr);
            if (billAmount < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid bill amount");
            showNewForm(request, response);
            return;
        }

        // Validate username and address
        if (!isValidString(name) || !isValidString(address)) {
            request.setAttribute("errorMessage", "Enter valid Username and address..");
            showNewForm(request, response);
            return;
        }

        Admin newUser = new Admin(name, password, address, billAmount, billStatus);
        try {
            adminDAO.insertUser(newUser);
            response.sendRedirect("list");
            return;
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error inserting user: " + e.getMessage());
            showNewForm(request, response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String billAmountStr = request.getParameter("billamount");
        String billStatus = "notpaid";

        // Validate input
        if (name == null || name.isEmpty() || password == null || password.isEmpty() ||
            address == null || address.isEmpty() || billAmountStr == null || billAmountStr.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required");
            showEditForm(request, response); 
            return;
        }

        double billAmount;
        try {
            billAmount = Double.parseDouble(billAmountStr);
            if (billAmount < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid bill amount");
            showEditForm(request, response); 
            return;
        }

        // Validate username and address
        if (!isValidString(name) || !isValidString(address)) {
            request.setAttribute("errorMessage", "Enter valid Username and address..It should contains only letters");
            showEditForm(request, response);
            return;
        }

        Admin user = new Admin(id, name, password, address, billAmount, billStatus);
        adminDAO.updateUser(user);
        response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        adminDAO.deleteUser(id);
        response.sendRedirect("list");
    }
    
    private boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty() && str.matches("[a-zA-Z\\s]+");
    }
}
