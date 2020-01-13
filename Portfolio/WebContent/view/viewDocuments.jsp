<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="Dokumenty"></c:param></c:import>

<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h1>Dostępne dokumenty</h1>
					
					<div>
					
						<p>Debug requestedDocument	${requestedDocument}</p>
						<p>Debug accessableDocuments	${accessableDocuments}</p>
 					<c:forEach items="${accessableDocuments}" var="document">
						<form:form action="${pageContext.request.contextPath}/Accessor/readDocument/" modelAttribute="requestedDocument" method="post" >
							<form:hidden path="name" value="${document.name}"/>
							<p>
								${document.name}
								<input type="submit" value="Otwórz"/>
							</p>
						</form:form>
					</c:forEach>
					
					</div>
					
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
				<p><h3>Przegląd dokumentów</h3></p>
				<p>Na tej stronie użytkownik otrzymuje listę wszystkich plików dostępnych przy posiadanych uprawnieniach</p>
				<p>Ze względu na przesyłanie wyboru pliku metodą POST, nie ma możliwości podesłania linku do pliku i ominięcia w ten sposób zabezpieczeń </p>

			</div>
		</div>

<br><br>


<c:import url="/view/accessorFooter.jsp"></c:import>