<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="${requestedDocument.name }"></c:param></c:import>

<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h2>${requestedDocument.name  }</h2>
					
					
					
					<div>
   						<pre><c:import url="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/Accessor/documents/${requestedDocument.name}.txt"></c:import></pre>
<%-- 	<%-- Kaczka - do korekty na serwerze -->					<pre><c:import url="http://${pageContext.request.serverName}${pageContext.request.contextPath}/Accessor/documents/${requestedDocument.name}.txt"></c:import></pre> --%>
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
				<p>Na tej stronie użytkownik przegląda wybrany plik</p>
				<p>Ze względu na przesyłanie wyboru pliku metodą POST, nie ma możliwości podesłania linku do pliku i ominięcia w ten sposób zabezpieczeń </p>

			</div>
		</div>

<br><br>


<c:import url="/view/accessorFooter.jsp"></c:import>