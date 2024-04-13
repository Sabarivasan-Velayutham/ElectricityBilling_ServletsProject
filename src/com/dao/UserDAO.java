package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.db.DBConnect;
import com.entity.Admin;
import com.entity.User;

import jakarta.servlet.ServletContext;

public class UserDAO {
	private Connection conn;
	private ServletContext servletContext;

	private static final String SELECT_USER_BY_ID = "SELECT * FROM userinfo WHERE id = ?";

	public UserDAO(ServletContext servletContext) {
		this.servletContext = servletContext;
		conn = DBConnect.getConn(servletContext); // Initialize the connection
	}

//	// User authentication logic using database
	public int isValidUser(String username, String password) {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT id FROM userinfo WHERE username = ? AND password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("id"); // Return user ID if exists
			} else {
				return -1; // User not found
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1; // Error occurred
		}
	}
	
	// User authentication logic using database
//		public boolean isValidUser(String username, String password) {
//			try {
//				PreparedStatement ps = conn.prepareStatement("SELECT id FROM userinfo WHERE username = ? AND password = ?");
//				ps.setString(1, username);
//				ps.setString(2, password);
//				ResultSet rs = ps.executeQuery();
//				return rs.next();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				return false; // Error occurred
//			}
//		}
		

	public boolean updateBillStatus(int userId, String status) {
		boolean success = false;
		try {
			String updateStatusSql = "UPDATE userinfo SET billstatus = ? WHERE id = ?";
			PreparedStatement updateStatusStmt = conn.prepareStatement(updateStatusSql);
			updateStatusStmt.setString(1, status);
			updateStatusStmt.setInt(2, userId);
			System.out.println(updateStatusStmt);
			int rowsAffected = updateStatusStmt.executeUpdate();

			if (rowsAffected > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public User selectUser(int id) {
		User user = null;
		try (Connection connection = DBConnect.getConn(servletContext);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt("id");
				String username = rs.getString("username");
				double billAmount = rs.getDouble("billamount");
				String billStatus = rs.getString("billstatus");
				user = new User(userId, username, billAmount, billStatus);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

	public double getBillAmount(int userId) {
		double billAmount = 0.0;
		try {
			String getBillAmountSql = "SELECT billamount FROM userinfo WHERE id = ?";
			try (PreparedStatement getBillAmountStmt = conn.prepareStatement(getBillAmountSql)) {
				getBillAmountStmt.setInt(1, userId);
				ResultSet rs = getBillAmountStmt.executeQuery();
				if (rs.next()) {
					billAmount = rs.getDouble("billamount");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billAmount;
	}

	public boolean isBillPaid(int userId) {
		boolean isPaid = false;
		try {
			String isBillPaidSql = "SELECT billstatus FROM userinfo WHERE id = ?";
			try (PreparedStatement isBillPaidStmt = conn.prepareStatement(isBillPaidSql)) {
				isBillPaidStmt.setInt(1, userId);
				ResultSet rs = isBillPaidStmt.executeQuery();
				if (rs.next()) {
					String billStatus = rs.getString("billstatus");
					isPaid = "paid".equalsIgnoreCase(billStatus);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isPaid;
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

}
