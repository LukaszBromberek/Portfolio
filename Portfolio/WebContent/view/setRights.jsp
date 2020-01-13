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
					
<%-- 					Debug<br>
					<p>accessRightsToChangeOption</p>
					<c:forEach items="${accessRightsToChangeOption}" var="right">
						${right.department.prefix} ${right.level}<br/>
					</c:forEach>
					<p>adminRightsToChangeOption</p>
					<c:forEach items="${adminRightsToChangeOption}" var="right">
						${right.department.prefix} ${right.level}<br/>
					</c:forEach>
					<p>rightsList</p>
					<c:forEach items="${rightsList}" var="right">
						${right.department.prefix} ${right.level}<br/>
					</c:forEach>
					<p>numberOfAccessRights : ${numberOfAccessRights }</p>
					<p>numberOfAdminRights : ${numberOfAdminRights }</p>
					/Debug --%>
					
					<h2>Dostępy do plików użytkownika ${choosenUser.firstName} ${choosenUser.lastName}</h2>
						<c:forEach items="${choosenUser.accessRights }" var="accessRight">
							<p>${accessRight.department.prefix } : Dział ${accessRight.department.name } poziom : ${accessRight.level}</p>
						</c:forEach>
						
					<h3>Edycja dostępów</h3>
					<form:form action="${pageContext.request.contextPath}/Accessor/changeRights/" modelAttribute="changedRightsList" method="post" >
						<form:hidden path="chosenUserId" value="${choosenUser.id}" />
						<form:hidden path="numberOfAccessRights" value="${numberOfAccessRights}" />
						<form:hidden path="numberOfAdminRights" value="${numberOfAdminRights}" />
						<c:forEach begin="${0}" end="${numberOfAccessRights-1}" step="1"  var="i">
							<p>
								${rightsList.get(i).department.prefix } : Dział ${rightsList.get(i).department.name } poziom 
								
								<form:select path="list[${i}]" multiple="false">
									<c:forEach items="${accessRightsToChangeOption}" var="right">
										<c:if test="${right.department.prefix.equals(rightsList.get(i).department.prefix)==true }">
											<form:option value="${right.simplyCode()}" label="${right.level }"></form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</p>
						</c:forEach>

					
						<h2>Prawa administracyjne użytkownika ${choosenUser.firstName} ${choosenUser.lastName}</h2>
						<c:forEach items="${choosenUser.adminRights }" var="adminRight">
							<p>${adminRight.department.prefix } : Dział ${adminRight.department.name } poziom : ${adminRight.level}</p>
						</c:forEach>
						
					<c:if test="${sessionScope.currentUser.userClass.equals('user')==false }">
						<h3>Edytuj prawa administracyjne</h3>
						<c:forEach begin="${numberOfAccessRights}" end="${numberOfAdminRights-1}" step="1"  var="i">
							<p>
								${rightsList.get(i).department.prefix } : Dział ${rightsList.get(i).department.name } poziom 
								
								<form:select path="list[${i}]" multiple="false">
									<c:forEach items="${adminRightsToChangeOption}" var="right">
										<c:if test="${right.department.prefix.equals(rightsList.get(i).department.prefix)==true }">
											<form:option value="${right.simplyCode()}"   label="${right.level }"></form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</p>
						</c:forEach>
					</c:if>
					
					<div class="row accessor-content-box flex-just">
						<div class="flex-right"><input type="submit" class="big-button"  value="Wprowadź zmiany"/></div>

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