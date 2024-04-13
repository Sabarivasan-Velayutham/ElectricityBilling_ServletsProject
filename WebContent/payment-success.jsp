<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 600px;
            margin: auto;
            text-align: center;
        }
        .success-msg {
            color: green;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .home-link {
            text-decoration: none;
            color: blue;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="success-msg">Payment Successful!</h2>
    <p>Your payment has been successfully processed.</p>
    <a href="<%=request.getContextPath()%>/user_action.jsp" class="home-link">Go to Homepage</a>
</div>

</body>
</html>
