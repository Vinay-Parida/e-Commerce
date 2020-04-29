<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP Login</title>
</head>

<body>
<form method="POST", action = "${contextPath}/login">
UserName: <input type="text" name="username" required><br/>
Password: <input type="password" name="password" required/><br/>
<div hidden>
Grant type: <input type= "text", name= "grant_type" value="password" required><br/>
Client Id: <input type= "text", name= "client_id" value="live-test" required><br/>
Client Secret: <input type= "text", name= "client_secret" value="abcde" required><br/>
</div>

<input type="submit" value="Submit" />
</form>

</body>
</html>