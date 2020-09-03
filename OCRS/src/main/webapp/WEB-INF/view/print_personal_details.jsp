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
				<div id="templatemo_header">
				</div>
				<hr class="color">
				<!-- end of header -->
				<div class="table_div" class="home">
					<table class="table_div">
						<tr>
							<td><security:authorize access="hasRole('POLICE')">
									<p>
										<a id="aId" href="${pageContext.request.contextPath}/publicComplaints">public
											complaints</a>
									</p>
								</security:authorize></td>
							<td>
								<p>
									<a id="aId" href="${pageContext.request.contextPath}/complaintDetails">Complaint
										Details</a>
								</p>

							</td>
							<security:authorize access="hasRole('ADMIN')"><td>
									<p>
										<a id="aId" href="${pageContext.request.contextPath}/manageUser">Manage
											User</a>
									</p>
								</td></security:authorize>
							<security:authorize access="hasRole('ADMIN')"><td>
									<p>
										<a id="aId" href="${pageContext.request.contextPath}/managePolice">Manage
											Police</a>
									</p>
								</td></security:authorize>
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
					<li><a href="logout"><img
							src="images/logout.png" alt="myspace" /></a></li>
					<li><a href="${pageContext.request.contextPath}/personDetails?userName=<security:authentication property="principal.username" />"><img
							src="images/templatemo_aboutus.png" alt="twitter" /></a></li>
					<li><a href="${pageContext.request.contextPath}/"><img
							src="images/templatemo_home_hover.png" /></a></li>
				</ul>

				<div id="content">

					<!-- scroll -->


					<div class="scroll">
						<div class="scrollContainer">

							<div class="panel" id="home">

								<hr>
								<h2 style="color:black">
									Welcome
									<security:authentication property="principal.username" />!!
									<br> <br> 
									
									</h2>
								<hr>
								<form:form action="${pageContext.request.contextPath}/updateUser" method="POST">
								First Name:<c:if test="${id!=1}"> ${userPojo.getFirstName()}   <a href="${pageContext.request.contextPath}/printUser?userName=<security:authentication property="principal.username" />&id=1">Edit</a><br><br></c:if>
								<c:if test="${id==1}"><input type="text" name="element" value="${userPojo.getFirstName()}"/>
								<input type="hidden" name="id" value="${id}">
								<input type="hidden" name="userName" value="<security:authentication property="principal.username" />">
								<input type="submit" value="update">	<br><br></c:if>
								
								
								Last Name: <c:if test="${id!=2}">${userPojo.getLastName()}  <a href="${pageContext.request.contextPath}/printUser?userName=<security:authentication property="principal.username" />&id=2">Edit</a><br><br></c:if>
								<c:if test="${id==2}"><input type="text" name="element" value="${userPojo.getLastName()}"/>
								<input type="hidden" name="id" value="${id}">
								<input type="hidden" name="userName" value="<security:authentication property="principal.username" />">
								<input type="submit" value="update">	<br><br></c:if>
								
								
								Email: <c:if test="${id!=3}">${userPojo.getEmail()}    <a href="${pageContext.request.contextPath}/printUser?userName=<security:authentication property="principal.username" />&id=3">Edit</a><br><br></c:if>
								<c:if test="${id==3}"><input type="text" name="element" value="${userPojo.getEmail()}"/>
								<input type="hidden" name="id" value="${id}">
								<input type="hidden" name="userName" value="<security:authentication property="principal.username" />">
								<input type="submit" value="update">	<br><br></c:if>
								
								
								Gender: <c:if test="${id!=4}">${userPojo.getGender()} <a href="${pageContext.request.contextPath}/printUser?userName=<security:authentication property="principal.username" />&id=4">Edit</a><br><br></c:if>
								<c:if test="${id==4}"><input type="text" name="element" value="${userPojo.getGender()}"/>
								<input type="hidden" name="id" value="${id}">
								<input type="hidden" name="userName" value="<security:authentication property="principal.username" />">
								<input type="submit" value="update">	<br><br></c:if>
								
								</form:form>
								<a href="${pageContext.request.contextPath}/deactivateUser?userName=<security:authentication property="principal.username" />">Deactivate Account</a>
								
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