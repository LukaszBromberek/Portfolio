<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="Accessor"></c:param></c:import>

		<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h1>Accessor</h1>
					<c:if test="${sessionScope.isLogged==false}">
					
						<h3> Zaloguj się, by poznać jego działanie</h3>
						<p>Dane logowania dostępne będą po przejściu do formularza</p>
						<div class="accessor-content-box">
							<a href="${pageContext.request.contextPath}/Accessor/login/">
							<div class="big-button">
								Zaloguj się
							</div>
							</a>
						</div>
					
					
					</c:if>
					
					<c:if test="${sessionScope.isLogged==true}">
					

						<div class="accessor-content-box flex-just">
							
							<c:if test="${sessionScope.currentUser.getUserClass().equals('Admin')==true}">
								<a href="${pageContext.request.contextPath}/Accessor/createUser/">
								<div class="big-button">
									Dodaj użytkownika
								</div>
								</a>
							</c:if>
							
							<c:if test="${sessionScope.currentUser.adminRights.isEmpty()==false}">
								<a href="${pageContext.request.contextPath}/Accessor/setRights/" >
								<div class="big-button">
									Zarządzaj dostępami
								</div>
								</a>
							</c:if>
							

							<a href="${pageContext.request.contextPath}/Accessor/documents/">
							<div class="big-button">
								Przeglądaj dokumenty
							</div>
							</a>

							<a href="${pageContext.request.contextPath}/Accessor/rights/">
							<div class="big-button">
								Twoje uprawnienia
							</div>
							</a>
							
							
							
							
						</div>
					
					
					</c:if>
					
				</div>
			</div>
		
			
			<div class="accessor-aside">
				<h3>Strona główna projektu Accessor</h3>
				<p>Projekt ten zarządza dostępami pracowników do dokumentów, oraz umożliwia dynamiczne zarządzanie tymi dostępami przez administratorów systemu, kierowników oraz upoważnionych pracowników.</p>
				<p>Prawa dostępu są definiowane przez oznaczenie działu i poziomu dostępu, np. Dział Finansowy - poziom 2</p>
				<p>Wykorzystane języki, frameworki i narzędzia :</p>
				<ul>
					<li>Java EE</li>
					<li>Spring</li>
					<li>JSTL</li>
					<li>Hibernate</li>
				</ul>
				<p>Zaimplementowane funkcje : </p>
				<ul>
					<li>Mechanizm logowania</li>
					<li>Kodowanie hasła z użyciem MD5</li>
					<li>Zabezpieczenie przed wstrzykiwaniem SQL</li>
					<li>Dynamiczne dopasowanie formularzy do użytkownika</li>
					<li>Obsługa znaków polskich (UTF-8) w funkcjach</li>
					<li>Resetowanie bazy danych co godzinę</li>
				</ul>
				<p>Projekt wykonano w architekturze MVC.</p>	
				
			</div>
		</div>
<c:import url="/view/accessorFooter.jsp"></c:import>