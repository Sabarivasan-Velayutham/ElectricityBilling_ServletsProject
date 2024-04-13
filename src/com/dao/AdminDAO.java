package com.dao;

import com.entity.Admin;
import jakarta.servlet.ServletContext;
import com.db.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private static final String INSERT_USERS_SQL = "INSERT INTO userinfo (username, password, address, billamount, billstatus) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM userinfo WHERE id = ?";
//    private static final String SELECT_ALL_USERS = "SELECT * FROM userinfo";
    private static final String SELECT_ALL_USERS = "SELECT * FROM userinfo LIMIT ? OFFSET ?";
    private static final String DELETE_USERS_SQL = "DELETE FROM userinfo WHERE id = ?";
    private static final String UPDATE_USERS_SQL = "UPDATE userinfo SET username = ?, password = ?, address = ?, billamount = ?, billstatus = ? WHERE id = ?";
    private static final String SEARCH_USERS_BY_NAME ="SELECT id, username, password, address, billamount, billstatus FROM userinfo WHERE username LIKE ?";

    
    private ServletContext servletContext;

    public AdminDAO(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

 // Admin authentication logic using database
    public boolean isAdminUser(String username, String password) {
        try (Connection connection = DBConnect.getConn(servletContext);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT username,password FROM admininfo WHERE username = ? AND password = ?")) {
        	preparedStatement.setString(1, username);
        	preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next(); // Check if user exists in the database
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error occurred, treat as invalid user
        }
    }
    
    public void insertUser(Admin user) throws SQLException {
        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setDouble(4, user.getBillAmount());
            preparedStatement.setString(5, user.getBillStatus());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Admin selectUser(int id) {
        Admin user = null;
        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String address = rs.getString("address");
                double billAmount = rs.getDouble("billamount");
                String billStatus = rs.getString("billstatus");
                user = new Admin(id, username, password, address, billAmount, billStatus);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<Admin> selectAllUsers(int limit, int offset) {
        List<Admin> users = new ArrayList<>();
        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
        	preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
        	System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String address = rs.getString("address");
                double billAmount = rs.getDouble("billamount");
                String billStatus = rs.getString("billstatus");
                users.add(new Admin(id, username, password, address, billAmount, billStatus));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(Admin user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAddress());
            statement.setDouble(4, user.getBillAmount());
            statement.setString(5, user.getBillStatus());
            statement.setInt(6, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public List<Admin> searchUserById(int id) {
        List<Admin> users = new ArrayList<>();
        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String address = rs.getString("address");
                double billAmount = rs.getDouble("billamount");
                String billStatus = rs.getString("billstatus");
                users.add(new Admin(userId, username, password, address, billAmount, billStatus));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public List<Admin> searchUserByName(String name) {
        List<Admin> users = new ArrayList<>();
        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USERS_BY_NAME)) {
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String address = rs.getString("address");
                double billAmount = rs.getDouble("billamount");
                String billStatus = rs.getString("billstatus");
                users.add(new Admin(id, username, password, address, billAmount, billStatus));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }


    public List<Admin> sortUsers(String sortBy, String sortOrder) {
        List<Admin> users = new ArrayList<>();
        String query = "SELECT id, username, password, address, billamount, billstatus FROM userinfo ORDER BY ";
        switch (sortBy) {
            case "id":
                query += "id ";
                break;
            case "userName":
                query += "username ";
                break;
            default:
                return users;
        }

        // Add sorting order
        switch (sortOrder) {
            case "asc":
                query += "ASC";
                break;
            case "desc":
                query += "DESC";
                break;
            default:
                return users;
        }

        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String address = rs.getString("address");
                double billAmount = rs.getDouble("billamount");
                String billStatus = rs.getString("billstatus");
                users.add(new Admin(id, username, password, address, billAmount, billStatus));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }
    
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public int getNoOfRecords() {
        int noOfRecords=0;
        try (Connection connection = DBConnect.getConn(servletContext);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM userinfo");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                noOfRecords = resultSet.getInt(1); // Get the count from the first column of the result set
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return noOfRecords;
    }

}
