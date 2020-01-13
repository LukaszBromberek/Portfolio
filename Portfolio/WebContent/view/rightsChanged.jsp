<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="Uprawnienia zmienione"></c:param></c:import>

<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h1>Uprawnienia zmienione</h1>
					<h2>Dostępy do plików użytkownika ${choosenUser.firstName} ${choosenUser.lastName}</h2>
						<c:forEach items="${choosenUser.accessRights }" var="accessRight">
							<p>${accessRight.department.prefix } : Dział ${accessRight.department.name } poziom : ${accessRight.level}</p>
						</c:forEach>
						<h2>Prawa administracyjne użytkownika ${choosenUser.firstName} ${choosenUser.lastName}</h2>
						<c:forEach items="${choosenUser.adminRights }" var="adminRight">
							<p>${adminRight.department.prefix } : Dział ${adminRight.department.name } poziom : ${adminRight.level}</p>
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
				<p><h3>Dynamiczne formularze</h3></p>

			</div>
		</div>

<br><br>


<c:import url="/view/accessorFooter.jsp"></c:import>