<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List,java.util.ArrayList,accessor.model.users.User,accessor.model.users.User"%>

	<c:import url="/view/accessorHeader.jsp"><c:param name="title" value="Accessor"></c:param></c:import>

<div class="accessor-container w-100">
			<div class="accessor-main-container flex-just">
				<div class="accessor-content-box">
					<h1>Zaloguj się</h1>
					
					<form:form action="${pageContext.request.contextPath}/Accessor/login/" modelAttribute="user" method="post">
					<div class="row flex-spbt">
						<div class="left-cut">Login : </div>
						<div><form:input path="login"/></div>
					</div>
					<div class="row flex-spbt">
						<div class="left-cut">Hasło : </div>
						<div><form:password path="password"/></div>
					</div>
					<div class="row accessor-content-box flex-just">
						<div class="flex-right"><input type="submit"  value="Zaloguj"/></div>
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
				<p><h3>Dane logowania</h3></p>
					<table>
						<thead>
							<tr>
								<td>Login</td>
								<td>Hasło</td>
								<td>Funkcja</td>
							</tr>
						</thead>
						<tbody>
							<tr><td>GRZSLO</td><td>kozak123!</td><td>Administrator</td></tr>
							<tr><td>KAJKOZ</td><td>asdf5%56</td><td>Kier. Personalny</td></tr>
							<tr><td>GRZPRZ</td><td>porszak_42</td><td>Kier. IT</td></tr>
							<tr><td>TOMPSI</td><td>bezEs_12</td><td>Kier. Finansów</td></tr>
							<tr><td>HENSIE</td><td>Kmitzitz_8F</td><td>Kier. Produkcji</td></tr>
							<tr><td>ELIORZ</td><td>Niemno_%$#</td><td>Prac. Finansów</td></tr>
							<tr><td>ALEHAM</td><td>Ju5T_yOu_Wa1T</td><td>Prac. Produkcji</td></tr>
							<tr><td>AARBUR</td><td>Wait_for_1T</td><td>Prac. Produkcji</td></tr>
							<tr><td>ANDSCH</td><td>Hell_ple55</td><td>Prac. Personalny</td></tr>
							<tr><td>CONDUM</td><td>darkne55_S</td><td>Prac. Produkcji</td></tr>
							<tr><td>PAWKOM</td><td>Mar_but$4</td><td>Prac. Finansów</td></tr>
						</tbody>
					</table>
					<p></p>
					<p>Hasła w bazie danych przechowywane są wyłącznie w postaci zakodowanej</p>
			</div>
		</div>

<br><br>


<c:import url="/view/accessorFooter.jsp"></c:import>