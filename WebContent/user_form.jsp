<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Electricity Billing System</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

.container {
	margin: 20px auto;
	width: 50%;
}

.card {
	border: 1px solid #ddd;
	border-radius: 5px;
	padding: 20px;
}

form {
	margin-top: 20px;
}

label {
	display: block;
	margin-bottom: 8px;
}

input[type="text"] {
	width: 100%;
	padding: 8px;
	margin-bottom: 16px;
	box-sizing: border-box;
}

button {
	padding: 8px 16px;
	background-color: #2ecc71;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.error {
	color: red;
	margin-top: 8px;
}
</style>
</head>
<body>

	<header>
		<div>
			<a href="<%=request.getContextPath()%>/list">Electricity Billing
				System</a>
		</div>
	</header>
	<br>

	<div class="container">
		<div class="card">
			<form
				action="
        <c:if test='${user != null}'>update</c:if>
        <c:if test='${user == null}'>insert</c:if>"
				method="post">
				<caption>
					<h2>
						<c:if test="${user != null}">
                        Edit User
                    </c:if>
						<c:if test="${user == null}">
                        Add New User
                    </c:if>
					</h2>
				</caption>

				<c:if test="${user != null}">
					<input type="hidden" name="id" value="<c:out value='${user.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>User Name</label> <input type="text"
						value="<c:out value='${user.userName}' />" class="form-control"
						name="name" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>User Password</label> <input type="text"
						value="<c:out value='${user.password}' />" class="form-control"
						name="password">
				</fieldset>

				<fieldset class="form-group">
					<label>User Address</label> <input type="text"
						value="<c:out value='${user.address}' />" class="form-control"
						name="address">
				</fieldset>

				<fieldset class="form-group">
					<label>Bill Amount</label> <input type="text"
						value="<c:out value='${user.billAmount}' />" class="form-control"
						name="billamount">
				</fieldset>

				<c:if test="${not empty errorMessage}">
					<p class="error">
						<c:out value="${errorMessage}" />
					</p>
				</c:if>

				<button type="submit" class="btn btn-success">Save</button>
			</form>
		</div>
	</div>

</body>
</html>
