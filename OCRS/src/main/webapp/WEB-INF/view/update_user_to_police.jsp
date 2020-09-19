<%@page import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
							<td><p>
									<a id="aId"
										href="${pageContext.request.contextPath}/makeAsPolice">Add
										Police</a>
								</p></td>
							<td>
								<p>
									<a id="aId"
										href="${pageContext.request.contextPath}/viewAllPolice">View
										All Police</a>
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
									<div>
										<form:form
											action="${pageContext.request.contextPath}/searchUser"
											method="POST">
											<input type="text" name="user_pattern">
											<security:authorize access="hasRole('POLICE')">
											<input type="hidden" name="isPolice" value="1">
											</security:authorize>
											<input type="submit" value="search user">
										</form:form>
									</div>
									<c:if test="${removed == 0}">
									<c:if test="${size > 6}">
										<a
											href="${pageContext.request.contextPath}/searchUser?pattern=${pattern}id=1">1</a>
										<a
											href="${pageContext.request.contextPath}/searchUser?pattern=${pattern}id=2">2</a>
										<c:if test="${size >12}">
											<a
												href="${pageContext.request.contextPath}/searchUser?pattern=${pattern}id=3">3</a>
										</c:if>
									</c:if>
									</c:if>
									<c:if test="${removed == 1}">
									<c:if test="${size > 10}">
										<a
											href="${pageContext.request.contextPath}/makeAsPolice?id=1">1</a>
										<a
											href="${pageContext.request.contextPath}/makeAsPolice?id=2">2</a>
										<c:if test="${size >20}">
											<a
												href="${pageContext.request.contextPath}/makeAsPolice?id=3">3</a>
										</c:if>
									</c:if>
									</c:if>
									<c:if test="${users !=null}">
										<div>
											<table style="color: black;" cellpadding="6">
												<tr>
													<th>User Name</th>
													<th>First Name</th>
													<th>Last Name</th>
													<th>Gender</th>
													<th>Email</th>
													<th>Select police_station</th>
												</tr>
												<c:forEach var="user" items="${users}">
													<tr>
														<td><c:out value="${user.getUserName()}" /></td>
														<td><c:out value="${user.getFirstName()}" /></td>
														<td><c:out value="${user.getLastName()}" /></td>
														<td><c:out value="${user.getGender()}" /></td>
														<td><c:out value="${user.getEmail()}" /></td>
														<td><form:form
																action="${pageContext.request.contextPath}/updateUserToPolice"
																method="POST">
																<%
																	ResourceBundle resource = ResourceBundle.getBundle("police_station_codes");
																%>
																<%
																	String codes[] = resource.getString("code").split(",");
																%>
																<select id="p_id" name="p_id">
																	<%
																		for (String code : codes) {
																	%>
																	<option value="<%=code%>"><%=code%></option>
																	<%
																		}
																	%>
																</select>

																<input type="hidden" name="user_name"
																	value="${user.getUserName()}">
																<input type="submit" value="Make as Police">
															</form:form>
														<td>
															<%-- <td><a
								href="${pageContext.request.contextPath}/updateUserToPolice?user_name=${user.getUserName()}">Make
									as Police</a>
									
									</td> --%>
													</tr>
												</c:forEach>
											</table>
										</div>
									</c:if>
									<c:if test="${users ==null }">
	No reords found
	</c:if>

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