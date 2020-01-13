<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BloodReserves</title>

  <link type="text/css" href="${pageContext.request.contextPath}/template/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <link type="text/css" href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:500,700" rel="stylesheet">
  <link type="text/css" href="https://fonts.googleapis.com/css?family=Muli:400,400i,800,800i" rel="stylesheet">

<link type="text/css"
	href="${pageContext.request.contextPath}/template/css/portfolio.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.request.contextPath}/template/css/accessor.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.request.contextPath}/template/css/resume.min.css"
	rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
</head>
<body class="project-template">
	<div class="w-100 return-header">
		<a href="${pageContext.request.contextPath}/">
			<div class="return-button align-left">
				<i class="fa fa-arrow-left" aria-hidden="true"></i> Powrót do
				portfolio
			</div>
		</a>
		
	</div>
<div class="under-header"></div>

	<div class="accessor-container w-100">
		<div class="accessor-main-container flex-just">
			<div class="accessor-content-box">
				<h2>
					Zapasy krwi w polskich krwiodawstwach
					</h2>
					<br>
					<div>
						<form
							action="${pageContext.request.contextPath}/BloodReservesController"
							method="get">
							Data : <select name="date">
								<c:forEach items="${dates}" var="dateIter">
									<option value="${dateIter}">${dateIter }</option>
								</c:forEach>
							</select> Miasto : <select name="city">
								<c:forEach items="${cities}" var="cityIter">
									<option value="${cityIter}">${cityIter}</option>
								</c:forEach>
							</select> Typ krwi : <select name=bloodType>
								<c:forEach begin="0" end="${types.size()-1}" step="1"
									var="typeIter">
									<option value="${escapedTypes[typeIter]}">${types[typeIter]}</option>
								</c:forEach>
							</select> <input type="submit" value="Filtruj" /> <input type="hidden"
								name="page" value="select">
						</form>
					</div>

					<div>
						<table>
							<thead>
								<tr>
									<td class="blood-td">Data</td>
									<td class="blood-td">Miasto</td>
									<td class="blood-td">Typ krwi</td>
									<td class="blood-td">Poziom krwi</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${results}" var="record">
									<tr>
										<td class="blood-td">${record.date}</td>
										<td class="blood-td">${record.city}</td>
										<td class="blood-td">${record.type}</td>
										<td class="blood-td">${record.level}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
			</div>
		</div>
		<div class="accessor-aside">
				<h3>Strona główna projektu BloodReserves</h3>
				<p>Lista wyświetla ostatnie 50 wyników z bazy danych.</p>
				<p>Wyniki można filtrować wg miast, dat aktualizacji na stronie i grup krwi</p>
			</div>
	</div>

<div class="footer">
	Łukasz Bromberek
	       <a href="https://www.linkedin.com/in/%C5%82ukasz-bromberek-9a5335164?originalSubdomain=pl">
          	 <i class="fab fa-linkedin-in"></i>
          </a>
          <a href="https://github.com/LukaszBromberek/Portfolio">
          	<i class="fab fa-github"></i>
          </a>
          <a href="mailto:lbromberek@gmail.com">
          	<i class="far fa-envelope"></i>
          </a>
</div>

</body>
</html>