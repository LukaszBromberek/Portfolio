<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="Zarządzaj dostępami"></c:param></c:import>

<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h1>Zarządzaj dostępami</h1>
					<h2>Wybierz użytkownika docelowego</h2>
					<form:form action="${pageContext.request.contextPath}/Accessor/setRights/" modelAttribute="choosenUser" method="post">
						<form:select path="id" >
							<form:options items="${usersMap}"/> 
						</form:select>
				
					
					<div class="row accessor-content-box flex-just">
						<div class="flex-right"><input type="submit" class="big-button"  value="Wybierz użytkownika"/></div>
						<a href="${pageContext.request.contextPath}/Accessor/">
							<div class="big-button">
								Powrót
							</div>
						</a>
					</div>
					</form:form>
					
				</div>
			</div>
			<div class="accessor-aside">
				<p><h3>Dynamiczne formularze</h3></p>
				<p>Formularz ten wykorzystuje możliwości frameworku Spring w zakresie dynamicznego generowania formularzy.</p>
				<p>Użytkownik z prawami do edycji poziomów dostępów u innych użytkowników może nadawać lub odbierać prawa dostępu do plików.</p>
				<p>Można dodać prawa dostępu maksymalnie do poziomu posiadanych praw administracyjnych</p>
				<p>Nie można odebrać użytkownikowi praw dostępu do dokumentów, jeśli posiada tak samo wysokie prawa administracyjne jak aktualny użytkownik</p>]
				<p>Wyłącznie administratorzy i kierownicy mogą zmieniać prawa użytkowników do zmiany uprawnień innych.</p>
			</div>
		</div>

<br><br>


<c:import url="/view/accessorFooter.jsp"></c:import>