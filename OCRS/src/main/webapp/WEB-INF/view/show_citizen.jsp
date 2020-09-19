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

								<hr>
								<h2 style="color: black">
									Welcome
									<security:authentication property="principal.username" />
									!! <br> <br>

								</h2>
								<hr>
								First Name: ${userPojo.getFirstName()} <br><br> Last Name :
								${userPojo.getLastName()} <br><br> Email:
								${userPojo.getEmail()} <br><br> Gender: ${userPojo.getGender()}<br>
								<br> Police station id: ${policePojo.getP_id()}<br>

								<%
									String url = request.getRequestURL() + "?" + request.getQueryString();
								System.out.println(url);
								%>

								<c:if test="${isPolice == 1 }">
									<a
										href="${pageContext.request.contextPath}/deletePolice?user_name=${userPojo.getUserName()}&noti_id=${noti_id}">Delete
										police Account</a>
								</c:if>



								<hr>

								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />


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