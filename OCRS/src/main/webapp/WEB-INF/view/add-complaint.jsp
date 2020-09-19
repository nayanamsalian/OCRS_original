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
				<div id="templatemo_header">
					<a href="#"><img src="images/templatemo_logo.png"
						alt="Mini Social" /></a>
				</div>
				<!-- end of header -->
				<div class="table_div" class="home">
					<table class="table_div">
						<tr>
							<td><p>
									<a id="aId"
										href="${pageContext.request.contextPath}/addComplaint">Add
										complaint</a>
								</p></td>
							<td>
								<p>
									<a id="aId"
										href="${pageContext.request.contextPath}/viewAllComplaints?user_name=<security:authentication property="principal.username" />">View
										my complaints</a>
								</p>

							</td>

						</tr>
					</table>
				</div>

			</div>
			<!-- end of sidebar -->

			<div id="templatemo_main">

				<ul id="social_box">
					<h4 style="color: black; padding: 9px 0px 25px 24px;">
						Online Crime<br>Reporting System
					</h4>
					<li><a href="logout"><img src="images/logout.png"
							alt="myspace" /></a></li>
					<li><a
						href="${pageContext.request.contextPath}/personDetails?userName=<security:authentication property='principal.username'/>">
							<img src="images/templatemo_aboutus.png" alt="about me" />
					</a> <br>Hi <security:authentication property='principal.username'/>!</li>

					<li><a href="${pageContext.request.contextPath}/"><img
							src="images/templatemo_home_hover.png" /></a></li>
					<li><br>
					<a style="color: green; font-size: 15;"
						href="${pageContext.request.contextPath}/myNotifications?userName=<security:authentication property='principal.username'/>">Notifications</a></li>
				</ul>

				<div id="content">

					<!-- scroll -->


					<div class="scroll">
						<div class="scrollContainer">

							<div class="panel" id="home">

								<div>

									<div class="panel-title">Add your complaint here</div>

									<div style="padding-top: 30px" class="panel-body">

										<!-- Registration Form -->
										<form:form method="POST"
											action="${pageContext.request.contextPath}/postComplaint"
											modelAttribute="fileBucket" enctype="multipart/form-data"
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
											<%
												String url = request.getRequestURL() + "?" + request.getQueryString();
											System.out.println(url);
											%>
											<input type="hidden" name="url" value="<%=url%>">
											<!-- User name -->
											
											<input type="hidden" name="user_name"
												value="<security:authentication property="principal.username" />">

											<%
												ResourceBundle resource = ResourceBundle.getBundle("police_station_codes");
											%>
											<%
												String codes[] = resource.getString("code").split(",");
											%>
											<table>
												<div style="margin-bottom: 25px;" class="input-group">
													<tr style="color: black;">
														<td><label for="p_id">Select police station
																code</label></td>
														<td>:</td>
														<td><select id="p_id" name="p_id">
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
														</select></td>
													</tr>
													<tr></tr>

												</div>

												<div>
													<%-- <a href="${pageContext.request.contextPath}/gotoUploadPage">Attach file</a> --%>
													<c:if test="${complaint.getAttached_file_path() != null}">
													<tr style="color:blue; font-size: 12;">
														<td>Attached file- 
														${complaint.getAttached_file_path()}
														 </td><td></td>
														 <td> (if you want to replace please upload another file)
														</td>
													</tr>
													</c:if>
													<tr style="color: black;">
														<td>Attach file(< 4MB)</td>
														<td>:</td>
														<td><input type="file" name="file" /></td>
													</tr>
												</div>


												<div>
													<tr style="color: black;">
														<td>Priority</td>
														<td>:</td>
														<td><select id="priority" name="priority">
																<c:if test="${complaint.getPriority()!= null}">
																	<option value="${complaint.getPriority()}">${complaint.getPriority()}</option>
																</c:if>
																<option value="Low">Low</option>
																<option value="Medium">Medium</option>
																<option value="High">High</option>
														</select></td>
													</tr>

												</div>
											</table>

											<!-- complaint -->
											<div style="margin-bottom: 25px" class="input-group">
												<br>
												<label for="complaint">Complaint:</label><br>
												<br>
												<textarea rows="5" cols="30" name="complaint"
													style="width: 800;"><c:if
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