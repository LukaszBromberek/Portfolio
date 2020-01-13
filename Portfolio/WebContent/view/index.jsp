<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pl">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Łukasz Bromberek - Portfolio</title>

  <!-- Bootstrap core CSS -->
  <link type="text/css" href="${pageContext.request.contextPath}/template/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link type="text/css" href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:500,700" rel="stylesheet">
  <link type="text/css" href="https://fonts.googleapis.com/css?family=Muli:400,400i,800,800i" rel="stylesheet">
  <link type="text/css" href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link type="text/css" href="${pageContext.request.contextPath}/template/css/resume.min.css" rel="stylesheet">
  <link type="text/css" href="${pageContext.request.contextPath}/template/css/portfolio.css" rel="stylesheet"/>
  <link type="text/css" href="${pageContext.request.contextPath}/template/css/accessor.css" rel="stylesheet"/>

</head>

<body id="page-top">

  <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top" id="sideNav">
    <a class="navbar-brand js-scroll-trigger" href="#page-top">
      <span class="d-block d-lg-none">Łukasz Bromberek</span>
      <span class="d-none d-lg-block">
        <img class="img-fluid img-profile rounded-circle mx-auto mb-2" src="template/img/profile.jpg" alt="">
      </span>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link js-scroll-trigger" href="#about">O mnie</a>
        </li>
        <li class="nav-item">
          <a class="nav-link js-scroll-trigger" href="#experience">Doświadczenie</a>
        </li>
        <li class="nav-item">
          <a class="nav-link js-scroll-trigger" href="#education">Wykształcenie</a>
        </li>
        <li class="nav-item">
          <a class="nav-link js-scroll-trigger" href="#skills">Umiejętności</a>
        </li>
        <li class="nav-item">
          <a class="nav-link js-scroll-trigger" href="#bloodreserves">Projekt : Stany krwi</a>
        </li>
        <li class="nav-item">
          <a class="nav-link js-scroll-trigger" href="#accessor">Projekt : Accessor</a>
        </li> 
        <li class="nav-item">
          <a class="nav-link js-scroll-trigger" href="#interests">Zainteresowania</a>
        </li>
      </ul>
    </div>
  </nav>

  <div class="container-fluid p-0">

    <section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="about">
      <div class="w-100">
        <h1 class="mb-0">Łukasz
          <span class="text-primary">Bromberek</span>
        </h1>
        <div class="subheading mb-5"> +48 721-838-454
          <a href="mailto:lbromberek@gmail.com">lbromberek@gmail.com</a>
        </div>
        <p class="lead mb-5">Jestem początkującym programistą, poszukującym okazji do wejścia w branżę IT. 
        					Zdolność szybkiej nauki łączę z zapałem do ciężkiej pracy i uporem przy rozwiązywaniu problemów.
        					Posiadam również praktykę w kontakcie z klientem i rozwoju oprogramowania dla niego.
        					Z pracy z ludźmi w różnym wieku wyniosłem umiejętność tłumaczenia spraw technicznych w sposób możliwie przystępny, oraz szkoleń z zakresu obsługi oprogramowania </p>
        <div class="social-icons">
          <a href="https://www.linkedin.com/in/%C5%82ukasz-bromberek-9a5335164?originalSubdomain=pl">
            <i class="fab fa-linkedin-in"></i>
          </a>
          <a href="https://github.com/LukaszBromberek/Portfolio">
            <i class="fab fa-github"></i>
          </a>
        </div>
      </div>
    </section>

    <hr class="m-0">

    <section class="resume-section p-3 p-lg-5 d-flex justify-content-center" id="experience">
      <div class="w-100">
        <h2 class="mb-5">Doświadczenie</h2>

        <div class="resume-item d-flex flex-column flex-md-row justify-content-between mb-5">
          <div class="resume-content">
            <h3 class="mb-0">Specjalista Zapewnienia Jakości</h3>
            <div class="subheading mb-3">Volkswagen Poznań</div>
            <p>Będąc odpowiedzialnym za nadzór nad połączeniami śrubowymi korzystałem z dostępu do urządzeń przemysłu 4.0 i zbieranych przez nie danych. 
            	Umiejętności analizy danych i podstaw programowania wykorzystałem do stworzenia aplikacji przetwarzających wyniki kontroli do postaci raportów.
            	W trakcie długich dyskusji z klientami wewnętrznymi ulepszałem ją oraz pomniejsze zautomatyzowane systemy raportowania w celu maksymalnego usprawnienia pracy</p>
          </div>
          <div class="resume-date text-md-right">
            <span class="text-primary">2016 - Obecnie</span>
          </div>
        </div>

        <div class="resume-item d-flex flex-column flex-md-row justify-content-between mb-5">
          <div class="resume-content">
            <h3 class="mb-0">Praktykant Utrzymania Ruchu</h3>
            <div class="subheading mb-3">Volkswagen Poznań</div>
            <p>W trakcie praktyk miałem okazję zapoznać się z szerokim zakresem technologii stosowanych w przemyśle motoryzacyjnym : sterownikami PLC, sieciami przemysłowymi, robotami i oprogramowaniem OLP.</p>
          </div>
          <div class="resume-date text-md-right">
            <span class="text-primary">2013 - 2016</span>
          </div>
        </div>

      </div>

    </section>

    <hr class="m-0">

    <section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="education">
      <div class="w-100">
        <h2 class="mb-5">Wykształcenie</h2>

        <div class="resume-item d-flex flex-column flex-md-row justify-content-between mb-5">
          <div class="resume-content">
            <h3 class="mb-0">Politechnika Poznańska</h3>
            <div class="subheading mb-3">Mgr. Inż.</div>
            <div>Zarządzanie i Inżynieria Produkcji - specjalizacja Zarządzanie jakością<br><br>
            	Temat pracy dyplomowej : Doskonalenie procesów kontroli połączeń śrubowych z zastosowaniem metod statystycznych
            </div>
          </div>
          <div class="resume-date text-md-right">
            <span class="text-primary">2016 - 2018</span>
          </div>
        </div>

        <div class="resume-item d-flex flex-column flex-md-row justify-content-between">
          <div class="resume-content">
            <h3 class="mb-0">Politechnika Poznańska</h3>
            <div class="subheading mb-3">Inż.</div>
            <div>Automatyka i Robotyka - specjalizacja Robotyka<br><br>
            	Temat pracy dyplomowej : Koncepcja i weryfikacja symulacyjna robotyzacji stanowiska klejenia poszyć bocznych
            </div>
          </div>
          <div class="resume-date text-md-right">
            <span class="text-primary">2012 - 2016</span>
          </div>
        </div>

      </div>
    </section>

    <hr class="m-0">

    <section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="skills">
      <div class="w-100">
        <h2 class="mb-5">Umiejętności</h2>

        <p><h3 class="mb-0">Języki programowania</h3>
        <ul class="list-inline dev-icons">
        <li class="list-inline-item">
            <i class="fab fa-java"></i>
          </li>
          <li class="list-inline-item">
            <i class="fab fa-html5"></i>
          </li>
          <li class="list-inline-item">
            <i class="fab fa-css3-alt"></i>
          </li>
        </ul>
        </p><p>
       <h3 class="mb-0">Narzędzia i Frameworki</h3>
         <ul class="fa-ul mb-0">
           <li>
          	<i class="fa-li fa fa-caret-right"></i>
          	Spring
          </li>
          <li>
          	<i class="fa-li fa fa-caret-right"></i>
          	Hibernate
          </li>
          <li>
          	<i class="fa-li fa fa-caret-right"></i>
          	Maven
          </li>
          <li>
          	<i class="fa-li fa fa-caret-right"></i>
          	JSTL
          </li>
        </ul>
        </p><p>
         <h3 class="mb-0">Języki obce</h3>
         <ul class="fa-ul mb-0">
           <li>
          	<i class="fa-li fa fa-caret-right"></i>
          	J. angielski -  komunikatywny ~ B2
          </li>
          <li>
          	<i class="fa-li fa fa-caret-right"></i>
          	J. niemiecki -  podstawowy ~ A2/B1
          </li>
        </ul>
         </p><p>
        <h3 class="mb-0">Umiejętności miękkie</h3>
         <ul class="fa-ul mb-0">
           <li>
          	<i class="fa-li fa fa-caret-right"></i>
          	Szkolenie pracowników nietechnicznych z obsługi systemów informatycznych
          </li>
          <li>
          	<i class="fa-li fa fa-caret-right"></i>
          	Przygotowywanie materiałów szkoleniowych
          </li>
        </ul></p>
    </section>

<section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="bloodreserves">
      <div class="w-100">
      	<h2 class="mb-5">Projekt : Blood Reserves</h2>
      	<p>Aplikacja BloodReserves komunikuję się ze stroną krew.info/zapasy i pobiera aktualny stan zapasów krwi we wszystkich krwiodawstwach w Polsce.</p>
      	<p>Dane na stronie zawierają tylko ostatnią aktualizację. Aplikacja umożliwia regularne sprawdzanie aktualizacji i przesyłanie danych do bazy.</p>
      	<p>Zgromadzone informacje mogę posłużyć do np. analizy danych w zakresie stanu krwiodawstwa w Polsce.</p><br>
      	<h3>Jeśli możesz oddawaj krew! Może ona komuś uratować życie</h3>
      	<a href="${pageContext.request.contextPath}/BloodReservesController"><div class="big-button">
					BloodReserves
				</div></a>
      </div>
</section>

<section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="accessor">
      <div class="w-100">
      	<h2 class="mb-5">Projekt : Accessor</h2>
      	<p>Aplikacja Accessor jest prostym systemem zarząrzadania dostępami użytkowników do plików w systemie.</p>
      	<p>Użytkownicy na różnych poziomach dysponują różnymi uprawnieniami, które można na bieżąco edytować</p>
      	<p>Projekt koncentruje się na prezentacji możliwości tworzenia dynamicznych formularzy w frameworku Spring i zastosowaniu Hibernate w architekturze MVC</p><br/>
      	      	<a href="${pageContext.request.contextPath}/Accessor/"><div class="big-button">
					Accessor
				</div></a>
      </div>
      </div>
      <p>
</section>

    <hr class="m-0">

    <section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="interests">
      <div class="w-100">
        <h2 class="mb-5">Zainteresowania</h2>
	 		<p>Interesuję się fantastyką, grami RPG oraz planszowymi. Mam za sobą kilka kampanii przeprowadzonych jako mistrz gry w systemach oraz Neuroshima, Warhammer.</p>
	 		<p>Regularnie uaktualniam biblioteczkę o nowe pozycje książkowe z działu historycznego.</p>
	 		<p>W dobry nastrój w trakcie programowania wprawia mnie dobry folk-metal, od celtyckiego po mongolski.</p>
	 		<p>Jestem bębniarzem-amatorem, z radością chwytającym za każdy dostępny pod ręką instrument, djembę, daf, tank-drum lub nawet butlę z wodą, jeśli kolejka do kawy jest zbyt długa.</p>
	 		
	     </div>
    </section>

    <hr class="m-0">

  </div>

  <!-- Bootstrap core JavaScript -->
  <script src="${pageContext.request.contextPath}/template/vendor/jquery/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/template/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Plugin JavaScript -->
  <script src="${pageContext.request.contextPath}/template/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for this template -->
  <script src="${pageContext.request.contextPath}/template/js/resume.min.js"></script>
  
  <!-- Prefix Free init -->
  <script src="${pageContext.request.contextPath}/template/prefixFree/prefixfree.min.js"></script>

</body>

</html>
