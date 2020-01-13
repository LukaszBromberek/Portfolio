<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="Twoje uprawnienia"></c:param></c:import>

<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h1>Twoje uprawnienia</h1>
					<h2>Dostępy do plików</h2>
					<c:forEach items="${sessionScope.currentUser.accessRights }" var="accessRight">
						<p>${accessRight.department.prefix } : Dział ${accessRight.department.name } poziom : ${accessRight.level}</p>
					</c:forEach>
					<h2>Nadawanie dostępów</h2>
					<c:forEach items="${sessionScope.currentUser.adminRights }" var="adminRights">
						<p>${adminRights.department.prefix } : Dział ${adminRights.department.name } poziom : ${adminRights.level}</p>
					</c:forEach>
					
					<div class="row accessor-content-box flex-just">
						<a href="${pageContext.request.contextPath}/Accessor/">
							<div class="big-button">
								Powrót
							</div>
						</a>
					</div>
					
				</div>
			</div>
			<div class="accessor-aside">
				<p><h3>JSTL</h3></p>
				<p>Poniższa strona służy podglądowi uprawnień użytkownika zgromadzonych w listach.</p>
				<p>W celu zmniejszenia objętości kodu do przeglądu danych z list nie zastosowano skrypletów Javy, a tagów JSTL.</p>

			</div>
		</div>

<br><br>


<c:import url="/view/accessorFooter.jsp"></c:import>