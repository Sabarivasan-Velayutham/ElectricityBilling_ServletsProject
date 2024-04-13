<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pay Bill</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: #fff;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center; /* Center the content */
        }

        h1 {
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            display: block; /* Ensure each label is on a new line */
            margin-bottom: 5px; /* Add some space below each label */
            text-align: left; /* Align labels to the left */
        }

        input[type="text"], select {
            width: calc(100% - 22px);
            padding: 6px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            display: block;
        }

        button[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            padding: 10px 20px;
            cursor: pointer;
            width: 100%;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Pay Bill</h1>
        <form action="<%=request.getContextPath()%>/user-action/paybill" method="post">
            <label for="paymentMethod">Payment Method:</label>
            <select id="paymentMethod" name="paymentMethod" required>
                <option value="" selected disabled>Choose</option>
                <option value="credit">Credit Card</option>
                <option value="upi">UPI</option>
                <option value="debitcard">Debit Card</option>
            </select>
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>
