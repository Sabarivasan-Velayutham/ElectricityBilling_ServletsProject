<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Failure</title>
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
        .failure-msg {
            color: red;
            font-size: 24px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="failure-msg">Payment Failed!</h2>
    <p>Sorry, your payment could not be processed.</p>
    <a href="<%=request.getContextPath()%>/user_action.jsp" class="home-link">Go to Homepage</a>
</div>

</body>
</html>
