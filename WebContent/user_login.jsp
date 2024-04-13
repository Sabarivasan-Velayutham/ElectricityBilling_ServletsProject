<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Login</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        div {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        h2 {
            color: #007BFF;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 20px;
        }

        label {
            margin-bottom: 10px;
            font-weight: bold;
            color: #333;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .goback-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #6c757d;
            color: #fff;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
        }

        .goback-btn:hover {
            background-color: #495057;
        }

        .register-link {
            font-size: 14px;
            color: #007BFF;
            text-decoration: none;
            margin-top: 10px;
            display: inline-block;
        }

        .register-link:hover {
            text-decoration: underline;
        }
        
        .success-message {
            color: green;
            font-weight: bold;
            margin-bottom: 15px;
         
    </style>
</head>
<body>
<%
	// this makes the previous page out of the cached pages.
	response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");

	String username = (String) session.getAttribute("Username");
	String role = (String) session.getAttribute("role");
	// before asking for credentials, check the session.
	if (username != null) {
		session.setAttribute("Username", username);
		session.setAttribute("role", role);
		response.sendRedirect("user_action.jsp");
		return;
	} else {
		System.out.println("Login page(session): " + username);
		System.out.println("Login page role (session): " + role);
	}

	// check for any cookies.
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("Username")) {
		username = cookie.getValue();
		System.out.println("Login page(cookie): " + username);
		System.out.println("Login page(session): " + role);
		break;
			}
		}
	}
	if (username != null) {
		response.sendRedirect("user_action.jsp");
		// https://stackoverflow.com/questions/2123514/java-lang-illegalstateexception-cannot-forward-sendredirect-create-session
		return;
	}
	%>
	
    <div>
        <h2>User Login</h2>
        <% if (request.getAttribute("successMessage") != null) { %>
            <p class="success-message"><%= request.getAttribute("successMessage") %></p>
        <% } %>
        
        <form action="./user" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        
            <input type="submit" value="Login" name="button">
        </form>
        
        <a href="<%=request.getContextPath()%>/index.jsp" class="goback-btn">Go Back</a>
        
        <a href="<%=request.getContextPath()%>/register.jsp" class="register-link">Register</a>
    </div>
</body>
</html>
