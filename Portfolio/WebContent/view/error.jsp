<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

<c:import url="/view/accessorHeader.jsp">
	<c:param name="title" value="Błąd"></c:param>
</c:import>

<div class="accessor-container w-100">
	<div class="accessor-main-container flex-just">
		<div class="accessor-content-box">
			<h1>Błąd</h1>
			<h2>${errorMsg}</h2>
			<div class="row accessor-content-box flex-just">
				<a href="${pageContext.request.contextPath}/">
					<div class="big-button">Strona główna</div>
				</a> <a href="${pageContext.request.contextPath}/Accessor/">
					<div class="big-button">Accessor</div>
				</a>
				<a href="${pageContext.request.contextPath}/bloodReservesController/">
					<div class="big-button">BloodReserves</div>
				</a>
			</div>
		</div>


		<div class="accessor-aside">
			<h3>Strona błędu</h3>
			<p>W przypadku wystąpienia błędu po stronie klienta lub serwera
				użytkownik zostaje odesłany na poniższą stronę, z krótką informacją
				o rodzaju błędu.</p>

		</div>
	</div>
</div>
	<c:import url="/view/accessorFooter.jsp"></c:import>