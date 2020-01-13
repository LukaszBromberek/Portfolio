<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="Accessor"></c:param></c:import>

<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h1>Użytkownik dodany</h1>
					
					<div class="flex-spbt">
						<div>
						<p>Imię : ${createdUser.firstName }</p>
						<p>Nazwisko : ${createdUser.lastName }</p>
						<p>Funkcja : 
							<c:if test="${createdUser.userClass.equals('Admin')}">Administrator</c:if>
							<c:if test="${createdUser.userClass.equals('Boss')}">Kierownik</c:if>
							<c:if test="${createdUser.userClass.equals('User')}">Użytkownik</c:if>
						</p>
						<p>Login : ${createdUser.login }</p>
						<p>Hasło : ${param.password }</p>
						</div>
					</div>
					<div>
						<a href="${pageContext.request.contextPath}/Accessor/setRights/">
								<div class="big-button">
									Zarządzaj dostępami
								</div>
						</a>
						<a href="${pageContext.request.contextPath}/Accessor/">
								<div class="big-button">
									Powrót
								</div>
						</a>
					</div>
					

				</div>
			</div>
			<div class="accessor-aside">
				<p><h3>Unikalność użytkowników</h3></p>
				<p>Login generowany jest na podstawie imienia i nazwiska, z konwersją znaków polskich na znaki podstawowe</p>
				<p>Program sprawdza, czy wygenerowany login nie występuje już w bazie danych. </p>
				<p>Jeśli występuje kolizja, do loginu zostanie dodany numer np. ANDKOZ2</p>

			</div>
		</div>

<br><br>


<c:import url="/view/accessorFooter.jsp"></c:import>