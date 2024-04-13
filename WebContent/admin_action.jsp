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
<title>Admin Management</title>
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
	position: relative;
	cursor: pointer;
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
	justify-content: space-between;
}

.search-container {
	margin-bottom: 10px;
}

.search-input {
	padding: 5px;
}

.sort-icon {
	position: absolute;
	right: 5px;
	top: 50%;
	transform: translateY(-50%);
}

.pagination-link {
	display: inline-block;
	padding: 5px 10px;
	margin-right: 5px;
	color: #007bff; /* Change color as needed */
	text-decoration: none;
	border: 1px solid #007bff; /* Change border color as needed */
	border-radius: 3px;
}

.pagination-link:hover {
	background-color: #f2f2f2;
	/* Change background color on hover as needed */
}

.current-page {
	display: inline-block;
	padding: 5px 10px;
	margin-right: 5px;
	color: #fff; /* Change color of current page indicator */
	background-color: #007bff;
	/* Change background color of current page indicator */
	border: 1px solid #007bff; /* Change border color as needed */
	border-radius: 3px;
}

.error-message {
	color: #ff0000;
	font-size: 14px;
	position: absolute;
	top: calc(50% - 3cm);
	left: 50%;
	transform: translate(-50%, -50%);
	background-color: rgba(255, 255, 255, 0.9);
	padding: 10px 20px;
	border-radius: 5px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>

<script>
	// Function to hide error messages after 3 seconds
	function hideErrorMessage() {
		var errorMessages = document.querySelectorAll('.error-message');
		errorMessages.forEach(function(message) {
			setTimeout(function() {
				message.style.display = 'none';
			}, 3000); // Hide after  seconds
		});
	}

	// Call the function to hide error messages on page load
	window.onload = function() {
		hideErrorMessage();
	};
</script>

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
				response.sendRedirect("admin_login.jsp");
			}
		} else {
			response.sendRedirect("admin_login.jsp");
		}
	}
	%>

	<header>
		<div>
			<a href="<%=request.getContextPath()%>/list">View All Users</a>
		</div>
	</header>
	<br>

	<div>
		<h3>List of Users</h3>
		<hr>
		<div class="logout-container">
			<form action="<%=request.getContextPath()%>/new">
				<button type="submit" class="btn btn-success">Add New User</button>
			</form>
			<form action="./admin" method="post">
				<input type='submit' value='Logout' name="button"
					class="btn btn-danger">
			</form>
		</div>

		<div class="search-container">
			<form action="<%=request.getContextPath()%>/searchById" method="post">
				<label for="searchById">Search by User ID:</label> <input
					type="text" id="searchById" class="search-input" name="userId" />
				<button type="submit" class="btn btn-primary">Search</button>
				<%-- Display error message for user ID validation --%>
				<c:if test="${not empty errorMessage}">
					<div class="error-message">${errorMessage}</div>
				</c:if>
			</form>

			<form action="<%=request.getContextPath()%>/searchByName"
				method="post">
				<label for="searchByName">Search by User Name:</label> <input
					type="text" id="searchByName" class="search-input" name="userName" />
				<button type="submit" class="btn btn-primary">Search</button>
				<c:if test="${not empty errorMessage}">
					<div class="error-message">${errorMessage}</div>
				</c:if>
			</form>
		</div>

		<form action="<%=request.getContextPath()%>/sort" method="post">
			<label for="sortBy">Sort By:</label> <select name="sortBy"
				id="sortBy" class="search-input">
				<option value="" selected>Choose</option>
				<option value="id">ID</option>
				<option value="userName">User Name</option>
			</select> <label for="sortOrder">Sort Order:</label> <input type="radio"
				id="asc" name="sortOrder" value="asc" checked> <label
				for="asc">Ascending</label> <input type="radio" id="desc"
				name="sortOrder" value="desc"> <label for="desc">Descending</label>
			<button type="submit" class="btn btn-primary">Sort</button>
		</form>

		<br>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>UserName</th>
					<th>Password</th>
					<th>Address</th>
					<th>Bill Amount</th>
					<th>Bill Status</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${listUser}">
					<tr>
						<td><c:out value="${user.id}" /></td>
						<td><c:out value="${user.userName}" /></td>
						<td><c:out value="${user.password}" /></td>
						<td><c:out value="${user.address}" /></td>
						<td><c:out value="${user.billAmount}" /></td>
						<td><c:out value="${user.billStatus}" /></td>
						<!-- New column -->
						<td><a href="edit?id=<c:out value='${user.id}' />"
							class="btn btn-primary">Edit</a> <a
							href="delete?id=<c:out value='${user.id}' />"
							class="btn btn-danger">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<c:if test="${currentPage != 1}">
			<a href="?page=${currentPage - 1}" class="pagination-link">Previous</a>
		</c:if>

		<c:forEach begin="1" end="${noOfPages}" var="i">
			<c:choose>
				<c:when test="${currentPage eq i}">
					<span class="current-page">${i}</span>
				</c:when>
				<c:otherwise>
					<a href="?page=${i}" class="pagination-link">${i}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:if test="${currentPage lt noOfPages}">
			<a href="?page=${currentPage + 1}" class="pagination-link">Next</a>
		</c:if>

	</div>
	<script>
		function searchUserById() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("searchById");
			filter = input.value.toUpperCase();
			table = document.querySelector("table");
			tr = table.getElementsByTagName("tr");

			for (i = 1; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[0]; // Index 0 for ID column
				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}

		function searchUserByName() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("searchByName");
			filter = input.value.toUpperCase();
			table = document.querySelector("table");
			tr = table.getElementsByTagName("tr");

			for (i = 1; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[1]; // Index 1 for username
				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}

		function sortTable(columnIndex) {
			var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
			table = document.querySelector("table");
			switching = true;
			dir = "asc";

			while (switching) {
				switching = false;
				rows = table.rows;

				for (i = 1; i < rows.length - 1; i++) {
					shouldSwitch = false;

					x = rows[i].getElementsByTagName("td")[columnIndex];
					y = rows[i + 1].getElementsByTagName("td")[columnIndex];

					if (dir === "asc") {
						if (x.innerHTML.toLowerCase() > y.innerHTML
								.toLowerCase()) {
							shouldSwitch = true;
							break;
						}
					} else if (dir === "desc") {
						if (x.innerHTML.toLowerCase() < y.innerHTML
								.toLowerCase()) {
							shouldSwitch = true;
							break;
						}
					}
				}

				if (shouldSwitch) {
					rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
					switching = true;
					switchcount++;
				} else {
					if (switchcount === 0 && dir === "asc") {
						dir = "desc";
						switching = true;
					}
				}
			}

			// Reset sort icons
			var sortIcons = document.querySelectorAll(".sort-icon");
			sortIcons.forEach(function(icon) {
				icon.innerHTML = "&#x2195;";
			});

			// Update sort icon
			var currentIcon = document.querySelector("th:nth-child("
					+ (columnIndex + 1) + ") .sort-icon");
			currentIcon.innerHTML = dir === "asc" ? "&#x2191;" : "&#x2193;";
		}
	</script>

</body>
</html>
