<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Login</title>
<!-- Include Bootstrap CSS (optional) -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.5.0/dist/css/bootstrap.min.css">
<!-- Custom CSS for background color -->
<link rel="stylesheet" type="text/css" href="resources/css/index.css">
<body>
	<div class="container">
		<h2 class="text-center">User Login</h2>
		<c:if test="${error!=null}">
			<p style="color: red">${error}</p>
		</c:if>
		<form action="dashboard" method="post" modelAttribute="user">
			<div class="form-group">
				<label for="userId">USER ID</label> <input type="text"
					class="form-control" name="clientcode"
					placeholder="Enter your User ID">
			</div>
			<div class="form-group">
				<label for="userPin">USER PIN</label> <input type="password"
					class="form-control" name="password"
					placeholder="Enter your User PIN">
			</div>
			<div class="form-group">
				<label for="userTotp">USER TOTP</label> <input type="number"
					class="form-control" name="totp" placeholder="Enter your User TOTP">
			</div>
			<div class="btn-login">
				<button type="submit" class="btn btn-primary">Login</button>
			</div>
		</form>
	</div>

	<!-- Include Bootstrap JS (optional) -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.5.0/dist/js/bootstrap.min.js"></script>
</body>
</html>