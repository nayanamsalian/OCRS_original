<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html style="background-color:lightskyblue;">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here
</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/webresources/masters/style/register.css"/>

<style>
.nav{
border:1px solid black;
border-color:lightskyblue;
padding-right:50px;
padding-left:100px;
font-size:25px;
margin-left:450px;
margin-right:400px;
background-color:grey;

}

input{
height:30px;
width:200px;
text-size:60px;
}

.fstnm{

margin-left:120px;
}
.lstnm{

margin-left:125px;
}
.usrnm{
margin-left:123px;
}

.eml{

margin-left:169px;
}
.phn{

margin-left:79px;
}


.gnd{

margin-left:70px;
}
.male{

margin-left:-80px;
}
.gndr{
margin-right:160px;
margin-left:160px;
}
.female{

margin-left:-245px;
}


.pswd{

margin-left:133px;
}
.cpswd{

margin-left:37px;
}
.sbmt{
margin-left:160px;
width:130px;
height:40px;
background-color:lightblue;
font-size:25px;
}
</style>
</head>

<body>

	<form action="register" method="post">
	<div class="nav">
		<br>
		<div style="color:red">
		${error_msg}
		</div>
	    <br>
	    
		<b>Firstname:</b><input type="text" class="fstnm" name="firstname" ><br><br>
		<b>Lastname:</b><input type="text" class="lstnm" name="lastname" ><br><br>
		<b>Username:</b><input type="text" class="usrnm" name="username" ><br><br>
		
		
		<b>Email:</b><input type="text" class="eml" name="email"><br><br>
		<b>PhoneNumber:</b><input type="text" class="phn" name="phno"><br><br>
		<b>Password:</b><input type="password"class="pswd" name="passwd"><br><br>
		<b>Confirm Password:</b><input type="password" class="cpswd" name="cpasswd"><br><br>
		<b>Gender:</b><input type="radio" id="male"class="gnd" name="gender" value="male"><label for="male" class="male">Male</label><br>
		<input type="radio" id="female" class="gndr" name="gender" value="female"> <label for="female" class="female">Female</label><br><br>
		
		
		<input type="submit" class="sbmt" value="register"><br>
		<b style="font-size:20px;margin-left:80px;">Already Have An Account?</b><a href="showMyLoginPage" style="color:white;font-size:20px;margin-left:20px;text-decoration:none;">LOGIN</a>
		</div>
	</form>
 
</body>
</html>