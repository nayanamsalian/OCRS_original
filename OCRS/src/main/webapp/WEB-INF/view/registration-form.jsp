<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!doctype html>
<html lang="en">

<head>

<title>Register New User Form</title>


<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="<c:url value="/css/coda-slider.css" />"
	type="text/css" media="screen" charset="utf-8" />
<!-- Reference Bootstrap files -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
.error {
	color: red
}
</style>

</head>

<body id="templatemo_body_wrapper">

	<div class="regis_main">

		<div id="loginbox" style="margin-top: 100px;"
			class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">

			<div>

				<div style="padding-top: 0px">
					<h3>Register New User</h3>
				</div>

				<div style="padding-top: 30px" class="panel-body" class="">

					<!-- Registration Form -->
					<form:form
						action="${pageContext.request.contextPath}/processRegistrationForm" method="POST"
						class="form-horizontal">

						<!-- Place for messages: error, alert etc ... -->
						<div class="form-group">
							<div class="col-xs-15">
								<div>

									<!-- Check for registration error -->
									<c:if test="${registrationError != null}">

										<div class="alert alert-danger col-xs-offset-1 col-xs-10">
											${registrationError}</div>

									</c:if>

								</div>
							</div>
						</div>

						<!-- User name -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span>
							<%-- <form:errors path="userName" cssClass="error" /> --%>
							<input type="text" placeholder="username (*)"
								class="form-control" name="userName" />
						</div>

						<!-- Password -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span>
							<%-- <form:errors path="password" cssClass="error" /> --%>
							<input type="password" name="password" placeholder="password (*)"
								class="form-control" />
						</div>

						<!-- Confirm Password -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span>
							<%-- <form:errors path="matchingPassword" cssClass="error" /> --%>
							<input type="password" name="matchingPassword"
								placeholder="confirm password (*)" class="form-control" />
						</div>


						<!-- First name -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span>
							<%-- <form:errors path="firstName" cssClass="error" /> --%>
							<input type="text" name="firstName" placeholder="first name (*)"
								class="form-control" />
						</div>

						<!-- Last name -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span>
							<%-- <form:errors path="lastName" cssClass="error" /> --%>
							<input type="text" name="lastName" placeholder="last name (*)"
								class="form-control" />
						</div>

						<!-- Gender -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <label> <input
								type="radio" name="gender" value="male" checked> <span>
									Male </span>
							</label> <label> <input type="radio" name="gender" value="female">
								<span>Female </span>
							</label>
						</div>

						<!-- Email -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span>
							<%-- <form:errors path="email" cssClass="error" /> --%>
							<input type="text" name="email" placeholder="email (*)"
								class="form-control" />
						</div>



						<!-- Register Button -->
						<div style="margin-top: 10px" class="form-group">
							<div class="col-sm-6 controls">
							<c:if test="${adminName != null}">
							<input type="hidden" value="${adminName}" name="adminName" ></c:if>
								<button type="submit" class="btn btn-primary">Register</button>
							</div>
						</div>
						
					</form:form>

				</div>

			</div>

		</div>

	</div>
	
	<div>
	
	<br>
	<div class="already_reg">
	Already registered? <a href="${pageContext.request.contextPath}/showMyLoginPage">LOGIN</a>
	</div>
	</div>

</body>
</html>