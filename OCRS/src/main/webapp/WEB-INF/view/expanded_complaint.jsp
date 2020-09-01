<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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
					<a href="#"><img src="images/templatemo_logo.png"
						alt="Mini Social" /></a>
				</div>
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
								<div>
									<caption>
										<h2>Complaint</h2>
									</caption>
									Police station Id: ${complaint.getP_code()} <br>
									Priority: ${complaint.getPriority()} <br>
									<form:form
										action="${pageContext.request.contextPath}/updateComplaintStatus"
										method="POST">
									Status:	
									<security:authorize access="hasRole('POLICE')">
											<select id="status" name="status">
												<c:if test="${complaint.getStatus()!= null}">
													<option value="${complaint.getStatus()}">${complaint.getStatus()}</option>
												</c:if>
												<option value="In progress">In progress</option>
												<option value="Completed">Completed</option>
												<option value="Rejected">Rejected</option>
											</select>
											<input type="hidden" name="c_id"
												value="${complaint.getComplaint_id()}">
											<input type="submit" value="update Status">
										</security:authorize>
										<c:if test="${complaint.getStatus()!= null}">
												${complaint.getStatus()}
										</c:if>
									</form:form>
									<br> Complaint details:<br>
									${complaint.getComplaint()}<br>
									<hr>
								</div>
								<div>
									<a
										href="${pageContext.request.contextPath}/getAllComments?complaint_id=${complaint.getComplaint_id()}">Read
										Comments</a><br>
										<c:forEach items="${comments}" var="comment">
											<div>
											<security:authorize access="hasRole('ADMIN')">
												<a href="${pageContext.request.contextPath}/deleteComment?comment_id=${comment.getComment_id()}&complaint_id=${complaint.getComplaint_id()}">
												<img src="images/delete.png" /></a>  
											</security:authorize> 
											 <c:out value="${comment.getFirstName()}"/>:: <c:out value="${comment.getComment()}"/><br>
											 </div>
										</c:forEach>
										<br>
										<c:if test="${flag!=null }">
										<form:form action="${pageContext.request.contextPath}/addComment" method="POST">
										<textarea rows="5" cols="40" name="comment">
										</textarea>
										<input type="hidden" name="complaint_id" value="${complaint.getComplaint_id()}">
										<input type="hidden" name="userName" value="<security:authentication property="principal.username" />"><br>
										<input type="submit" value="post"> 
										</form:form>
										</c:if>
									<c:if test="${flag==null }">
									<a
										href="${pageContext.request.contextPath}/reply?complaint_id=${complaint.getComplaint_id()}">Reply</a>
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