<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard - Contact Application</title>
<s:url var="url_css" value="/static/css/style.css" />
<link href="${url_css}" rel="stylesheet" type="text/css" />
</head>
<s:url var="url_bg" value="/static/images/bg.jpg" />
<body background="${url_bg}">
	<table border='1' width="80%" align="center">
		<tr>
			<td height="80px">
				<jsp:include page="include/header.jsp"/>
				<h1> User Dashboard</h1>
			</td>
		</tr>
		<tr>
			<td height="25px">
				<jsp:include page="include/menu.jsp"/>
			</td>
		</tr>
		<tr>
			<td height="350px"></td>
		</tr>
		<tr>
			<td height="25px">
				<jsp:include page="include/footer.jsp"/>
			</td>
		</tr>
	</table>

</body>
</html>