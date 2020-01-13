<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pl">
<head>
<meta charset="UTF-8">
<title>${param.title}</title>

  <!-- Bootstrap core CSS -->
  <link type="text/css" href="${pageContext.request.contextPath}/template/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link type="text/css" href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:500,700" rel="stylesheet">
  <link type="text/css" href="https://fonts.googleapis.com/css?family=Muli:400,400i,800,800i" rel="stylesheet">
  <link type="text/css" href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link type="text/css" href="${pageContext.request.contextPath}/template/css/accessor.css" rel="stylesheet"/>
  <link type="text/css" href="${pageContext.request.contextPath}/template/css/portfolio.css" rel="stylesheet"/>
  <link type="text/css" href="${pageContext.request.contextPath}/template/css/resume.min.css" rel="stylesheet"/>
</head>
<body class="project-template">


<div class="w-100 return-header flex-spbt">
	<a href="${pageContext.request.contextPath}/">
		<div class="return-button align-left"> 
			<i class="fa fa-arrow-left" aria-hidden="true"></i> Powrót do portfolio 
		</div>
	</a>
	
	
	
	<c:if test="${sessionScope.isLogged==true}">  
		<div class="logged-user-container">
			<div class="user-login-container">
				${sessionScope.currentUser.firstName } ${sessionScope.currentUser.lastName }
			</div>
			<div class="login-header-box">
				<a href="${pageContext.request.contextPath}/Accessor/logout/">	
					<div class="return-button align-right">
						Wyloguj
					</div>
				</a>
			</div>
		</div>
	</c:if>
	
	<c:if test="${sessionScope.isLogged==false}">  
		<a href="${pageContext.request.contextPath}/Accessor/login/">	
		<div class="return-button align-right">
			Zaloguj się
		</div>
		</a>
	</c:if>
	
	</div>
	<div class="under-header"></div>
</div>