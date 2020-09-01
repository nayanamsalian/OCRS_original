<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="en">

<head>
<link rel="stylesheet" href="<c:url value="/css/coda-slider.css" />"
	type="text/css" media="screen" charset="utf-8" />
<title>Login Page</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Reference Bootstrap files -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>

<body id="templatemo_body_wrapper">

	<div>

		<div id="loginbox" style="margin-top: 110px; margin-left: 100px"
			class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">

			<div class="register">
				<a href="showRegistrationForm" class="btn btn-primary" role="button"
					aria-pressed="true">Register New User</a>
			</div>

<br>
			<div class="panel panel-info">

				<div style="padding-top: 30px" class="panel-body">
					
					<i style="color:red;">${message}</i>
					<!-- Login Form -->
					<form
						action="${pageContext.request.contextPath}/forgotPassword"
						method="POST" class="form-horizontal">

												<!-- User name -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input type="text"
								name="username" placeholder="username" class="form-control">
						</div>

									<!-- Login/Submit Button -->
						<div style="margin-top: 10px" class="form-group">
							<div class="col-sm-6 controls">
								<button type="submit" class="btn btn-success">submit</button>
							</div>
						</div>

						<!-- I'm manually adding tokens ... Bro! -->

						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

					</form>

				</div>

			</div>

		</div>

	</div>



	<div></div>

</body>
</html>