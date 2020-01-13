<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="Dodaj użytkownika"></c:param></c:import>

<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h1>Dodaj użytkownika</h1>
					
					<form:form action="${pageContext.request.contextPath}/Accessor/createUser/" modelAttribute="createdUser" method="post" >
					
					<div class="form-error"><form:errors path="firstName"/></div>
					<div class="row flex-spbt">
						<div class="left-cut">Imię : </div>
						<div><form:input path="firstName"/></div>
					</div>
					
					<div class="form-error"><form:errors path="lastName"/></div>
					<div class="row flex-spbt">
						<div class="left-cut">Nazwisko : </div>
						<div><form:input path="lastName"/></div>
					</div>
					
					<div class="form-error"><form:errors path="password"/></div>
					<div class="row flex-spbt">
						<div class="left-cut">Hasło : </div>
						<div><form:input path="password"/></div>
					</div>
					
					<div class="row flex-spbt">
						<div class="left-cut">Funkcja : </div>
						<div>
							<form:select path="userClass">
								<form:option value="User" label="Użytkownik"/>
								<form:option value="Boss" label="Kierownik"/>
								<form:option value="Admin" label="Administrator"/>
							</form:select>
						</div>
					</div>
					
					<div class="row accessor-content-box flex-just">
						<div class="flex-right"><input type="submit" class="big-button"  value="Stwórz użytkownika"/></div>
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
				<p><h3>Walidacja</h3></p>
				<p>Poniższy formularz weryfikuje wprowadzone dane pod względem poprawności.</p>
				<p>Imię oraz nazwisko musi zawierać od 2 do 32 liter i składać się wyłącznie z liter</p>
				<p>Hasło musi zawierać co najmniej jedną cyfrę oraz jeden znak specjalny</p>

			</div>
		</div>

<br><br>


<c:import url="/view/accessorFooter.jsp"></c:import>