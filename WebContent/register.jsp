<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
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

        .login-link {
            margin-top: 20px;
            font-size: 14px;
            color: #007BFF;
            text-decoration: none;
        }

        .login-link:hover {
            text-decoration: underline;
        }

        .success-message {
            color: green;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div>
        <h2>User Registration</h2>
        <!-- Form for user registration -->
        <form id="registrationForm" action="RegisterServlet" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address" required>

            <input type="submit" value="Register">
        </form>

        <!-- Success message -->
        <div class="success-message">
            <%-- Check if the successMessage attribute exists in the request --%>
            <% if (request.getAttribute("successMessage") != null) { %>
                <%= request.getAttribute("successMessage") %>
            <% } %>
        </div>

        <!-- Links for navigation -->
        <a href="<%=request.getContextPath()%>/index.jsp" class="goback-btn">Go Back</a>
        <a href="<%=request.getContextPath()%>/user_login.jsp" class="login-link">Already have an account? Login here</a>
    </div>
</body>
</html>
