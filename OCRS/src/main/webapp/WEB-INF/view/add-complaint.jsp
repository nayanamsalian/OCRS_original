<%@page import="java.util.ResourceBundle"%>
<%@page import="org.springframework.core.env.Environment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@ taglib prefix="form"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="<c:url value="/css/templatemo_style.css" />"
	rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="<c:url value="/css/coda-slider.css" />"
	type="text/css" media="screen" charset="utf-8" />

<script type="text/javascript"
	src="<c:url value="/js/jquery-1.2.6.js" />">
	
</script>
<script type="text/javascript"
	src="<c:url value="/js/jquery.scrollTo-1.3.3.js" />">
	
</script>
<script type="text/javascript"
	src="<c:url value="/js/jquery.localscroll-1.2.5.js" />">
	
</script>
<script type="text/javascript"
	src="<c:url value="/js/jquery.serialScroll-1.2.1.js" />">
	
</script>
<script type="text/javascript"
	src="<c:url value="/js/coda-slider.js" />">
	
</script>
<script type="text/javascript"
	src="<c:url value="/js/jquery.easing.1.3.js" />">
	
</script>

</head>
<body>
	<div id="templatemo_body_wrapper">
		<div id="slider">

			<div id="templatemo_sidebar">
				<div id="templatemo_header"></div>
				<hr class="color">
				<!-- end of header -->
				<div class="table_div" class="home">
					<table class="table_div">
						<tr>
							<td><security:authorize access="hasRole('POLICE')">
									<p>
										<a id="aId"
											href="${pageContext.request.contextPath}/publicComplaints">public
											complaints</a>
									</p>
								</security:authorize></td>
							<td>
								<p>
									<a id="aId"
										href="${pageContext.request.contextPath}/complaintDetails">Complaint
										Details</a>
								</p>

							</td>
							<security:authorize access="hasRole('ADMIN')">
								<td>
									<p>
										<a id="aId"
											href="${pageContext.request.contextPath}/manageUser">Manage
											User</a>
									</p>
								</td>
							</security:authorize>
							<security:authorize access="hasRole('ADMIN')">
								<td>
									<p>
										<a id="aId"
											href="${pageContext.request.contextPath}/managePolice">Manage
											Police</a>
									</p>
								</td>
							</security:authorize>
						</tr>
					</table>
				</div>

			</div>
			<!-- end of sidebar -->

			<div id="templatemo_main">

				<ul id="social_box">
					<h4 style="color: black; padding: 20px 0px 25px 24px;">
						Online Crime<br>Reporting System
					</h4>
					<li><a href="logout"><img src="images/logout.png"
							alt="myspace" /></a></li>
					<li><a
						href="${pageContext.request.contextPath}/personDetails?userName=<security:authentication property="principal.username" />"><img
							src="images/templatemo_aboutus.png" alt="twitter" /></a></li>

					<li><a href="${pageContext.request.contextPath}/"><img
							src="images/templatemo_home_hover.png" /></a></li>
				</ul>


				<div id="content">

					<!-- scroll -->


					<div class="scroll">
						<div class="scrollContainer">

							<div class="panel" id="home">
								<p>
									Welcome:
									<security:authentication property="principal.username" />
									<br> <br>
								</p>

								<div>

									<div class="panel-title">Add your complaint here</div>

									<div style="padding-top: 30px" class="panel-body">

										<!-- Registration Form -->
										<form:form method="POST"
											action="${pageContext.request.contextPath}/postComplaint"
											class="form-horizontal">
											<%-- <form:form method = "POST" action="postComplaint"
        								 enctype = "multipart/form-data">
       --%>

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
											<input type="hidden" name="user_name"
												value="<security:authentication property="principal.username" />">

											<%
												ResourceBundle resource = ResourceBundle.getBundle("police_station_codes");
											%>
											<%
												String codes[] = resource.getString("code").split(",");
											%>
											<div style="margin-bottom: 25px" class="input-group">
												<label for="p_id">Select police_station:</label> <select
													id="p_id" name="p_id">
													<c:if test="${complaint.getP_code()!= null}">
														<option value="${complaint.getP_code()}">${complaint.getP_code()}</option>
													</c:if>
													<%
														for (String code : codes) {
													%>
													<option value="<%=code%>"><%=code%></option>
													<%
														}
													%>
												</select>

											</div>

											<div>
												Attach file : <input type="file" name="file" />
											</div>


											<div>
												Priority : <select id="priority" name="priority">
													<c:if test="${complaint.getPriority()!= null}">
														<option value="${complaint.getPriority()}">${complaint.getPriority()}</option>
													</c:if>
													<option value="Low">Low</option>
													<option value="Medium">Medium</option>
													<option value="High">High</option>
												</select>

											</div>

											<!-- complaint -->
											<div style="margin-bottom: 25px" class="input-group">
												<label for="complaint">Complaint:</label>
												<textarea rows="5" cols="30" name="complaint"><c:if
														test="${complaint.getComplaint()!= null}">${complaint.getComplaint()}</c:if></textarea>
												<!-- <input type="text" name="firstName" placeholder="first name (*)"
								class="form-control" /> -->
											</div>

											<!-- Register Button -->
											<c:if test="${complaint == null}">

												<div style="margin-top: 10px" class="form-group">
													<div class="col-sm-6 controls">
														<button type="submit" class="btn btn-primary">Post</button>
													</div>
												</div>
											</c:if>

											<c:if test="${complaint != null}">

												<div style="margin-top: 10px" class="form-group">
													<div class="col-sm-6 controls">
														<input type="hidden" name="c_id"
															value="${complaint.getComplaint_id()}">
														<button type="submit" class="btn btn-primary">Update</button>
													</div>
												</div>
											</c:if>

											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />


										</form:form>

									</div>


								</div>

							</div>
						</div>

					</div>
				</div>
				<!-- end of scroll -->

			</div>
			<!-- end of content -->

			<div id="templatemo_footer">

				Copyright © 2048 <a href="#">Your Company Name</a>

			</div>
			<!-- end of templatemo_footer -->

		</div>
		<!-- end of main -->
	</div>
</body>
</html>