<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.*"%>
<%@ page import="jakarta.servlet.http.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="jakarta.servlet.http.Cookie"%>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Management</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: left;
}

th {
	background-color: #f2f2f2;
}

.btn {
	display: inline-block;
	padding: 6px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	cursor: pointer;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
}

.btn-success {
	color: #fff;
	background-color: #5cb85c;
	border-color: #4cae4c;
}

.btn-primary {
	color: #fff;
	background-color: #337ab7;
	border-color: #2e6da4;
}

.btn-danger {
	color: #fff;
	background-color: #d9534f;
	border-color: #d43f3a;
}

.logout-container {
	display: flex;
	justify-content: flex-end; /* Align items to the right */
	align-items: center; /* Center vertically */
}

/* Style for blue header */
header {
	color: #007bff; /* Blue color */
}
</style>
</head>
<body>

	<%
	// making for a perfect Logout functionality.
	// this makes the previous page out of the cached pages.
	// https://stackoverflow.com/questions/3976624/java-servlet-how-to-disable-caching-of-page
	response.setHeader("Cache-control", "private, no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");

	String username = (String) session.getAttribute("Username");
	String role = (String) session.getAttribute("role");

	if (username != null) {
		System.out.println("Home page(session): " + username);
		System.out.println("Home page role (session): " + role);

	} else if (username == null) {
		// if no session, then check for cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
		if (cookie.getName().equals("Username")) {
			username = cookie.getValue();
		}
			}
			System.out.println("Home page(cookie): " + username);
			System.out.println("Home page role (session): " + role);
			
			if (username == null) {
		response.sendRedirect("user_login.jsp");
			}
		} else {
			response.sendRedirect("user_login.jsp");
		}
	}
	%>

	<header>
		<div>
			<a href="<%=request.getContextPath()%>/listBills">View Bill Details</a>
		</div>
	</header>
	<br>

	<div>
		<h3>Electricity Bill Payment</h3>
		<hr>
		<div class="logout-container">
			<form action="./user" method="post">
				<input type='submit' value='Logout' name="button"
					class="btn btn-danger">`
			</form>
		</div>
		<br>

		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>BillAmount</th>
					<th>BillStatus</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><c:out value="${listUser.id}" /></td>
					<td><c:out value="${listUser.userName}" /></td>
					<td><c:out value="${listUser.billamount}" /></td>
					<td><c:out value="${listUser.billstatus}" /></td>
					<td><a
						href="<%=request.getContextPath()%>/paybill.jsp?id=${listUser.id}"
						class="btn btn-primary">Pay Bill</a></td>
				</tr>

			</tbody>
		</table>
	</div>

</body>
</html>
