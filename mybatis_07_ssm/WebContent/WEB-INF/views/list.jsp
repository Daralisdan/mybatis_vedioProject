<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 遍历时导入标签 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工列表</title>
</head>
<body>
	<table>
		<!-- items:要遍历的集合 ，var：每一个数据的值-->
		<tr>
			<td>id</td>
			<td>用户名</td>
			<td>性别</td>
			<td>邮箱</td>
		</tr>
		<c:forEach items="${allEmp}" var="list">
			<tr>
				<td>${list.id }</td>
				<td>${list.lastName }</td>
				<td>${list.gender }</td>
				<td>${list.email }</td>
			</tr>


		</c:forEach>
	</table>


</body>
</html>