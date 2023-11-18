<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/dashboard.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
</head>
<body>
	<div class="dashboard">
		<div class="sidebar">
			<ul>
				<li><a href="#" id="home">Home</a></li>
				<li><a href="/userProfile" id="user profile">User profile</a></li>
				<li><a href="#" id="settings">Settings</a></li>
			</ul>
		</div>
		<div class="content">
			<h1>Welcome to the Dashboard</h1>
			<p>This is a basic example of a three-layer HTM dashboard.</p>
		</div>
		<div class="content-body">
		<h1>Login response JSON</h1>
			<table border="table-container">
				<tr>
					<th>Status</th>
					<th>Message</th>
					<th>Response Data</th>
					<th>Message</th>
				</tr>
				<tr>
					<td>${loginResponse.success}</td>
					<td>${loginResponse.error}</td>
					<td>${loginResponse.data}</td>
					<td>${loginResponse.message}</td>
				</tr>
			</table>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>
</body>
</html>