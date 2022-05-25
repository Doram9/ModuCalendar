<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>Dashboard - SB Admin</title>
	<link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
	<link href="css/styles.css" rel="stylesheet" />
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link href='css/fullcal/main.css' rel='stylesheet' />
	<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>

	<style>
		.calendar * {
			border: solid 1px black;
		}

		#kakaoLogin {
			border: 0;
			outline: 0;
		}

		/* 월화수목금 */
		.fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
			color: black;
		}

		.fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
			color: black;
		}

		/* 토요일 */
		.fc-day-sat .fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
			color: blue;
		}

		.fc-day-sat .fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
			color: blue;
		}

		/* 일요일 */
		.fc-day-sun .fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
			color: red;
		}

		.fc-day-sun .fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
			color: red;
		}
	</style>

</head>

<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
	<!-- Navbar Brand-->
	<a class="navbar-brand ps-3" href="/">Modu Calendar</a>
	<!-- Sidebar Toggle-->
	<button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
	<!-- 여백-->
	<div class="ms-auto"></div>
	<!-- Navbar-->
	<ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
			<ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
				<li><a class="dropdown-item" href="#!">내정보</a></li>
				<li>
					<hr class="dropdown-divider" />
				</li>
			</ul>
		</li>
	</ul>
</nav>
<div id="layoutSidenav">
	<div id="layoutSidenav_nav">
		<nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
			<div class="sb-sidenav-menu">
				<div class="nav">
					<div class="sb-sidenav-menu-heading">내 정보</div>
					<a class="nav-link" href="index.html">
						<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
						My Calendar
					</a>
					<div class="sb-sidenav-menu-heading">약속 목록</div>
					<a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
						<div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
						Layouts
						<div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
					</a>
					<div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
						<nav class="sb-sidenav-menu-nested nav">
							<a class="nav-link" href="layout-static.html">Static Navigation</a>
							<a class="nav-link" href="layout-sidenav-light.html">Light Sidenav</a>
						</nav>
					</div>
					<a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
						<div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
						Pages
						<div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
					</a>
					<div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
						<nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
							<a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
								Authentication
								<div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
							</a>
							<div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
								<nav class="sb-sidenav-menu-nested nav">
									<a class="nav-link" href="login.html">Login</a>
									<a class="nav-link" href="register.html">Register</a>
									<a class="nav-link" href="password.html">Forgot Password</a>
								</nav>
							</div>
							<a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseError" aria-expanded="false" aria-controls="pagesCollapseError">
								Error
								<div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
							</a>
							<div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
								<nav class="sb-sidenav-menu-nested nav">
									<a class="nav-link" href="401.html">401 Page</a>
									<a class="nav-link" href="404.html">404 Page</a>
									<a class="nav-link" href="500.html">500 Page</a>
								</nav>
							</div>
						</nav>
					</div>
					<div class="sb-sidenav-menu-heading">팀 목록</div>
					<a class="nav-link" href="charts.html">
						<div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
						Charts
					</a>
					<a class="nav-link" href="tables.html">
						<div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
						Tables
					</a>
				</div>
			</div>
			<div class="sb-sidenav-footer">
				<div class="small">Logged in as:</div>
				Start Bootstrap
			</div>
		</nav>
	</div>
	<div id="layoutSidenav_content">
		<main>
			<div class="container-fluid px-4">
				<div class="mt-4"></div>
				<div class="row">
					<div class="col-xl-6">
						<div class="card mb-4">
							<div class="card-header">
								<i class="fas fa-chart-area me-1"></i>
								내 일정
							</div>
							<div class="card-body">
								<div id="calendar" class="mt-2"></div>
							</div>
						</div>
					</div>
					<div class="col-xl-6">
						<div class="card mb-4">
							<div class="card-header">
								<i class="fas fa-chart-bar me-1"></i>
								내 프로젝트
							</div>

							<div class="card-body row justify-content-center">

								<div class="card" style="width: 18rem;">
									<div class="card-body btn btn-warning">
										<h6 class="card-subtitle mb-2 text-muted">아직 마일스톤이 없습니다</h6>
										<p class="card-text">마일스톤 추가하기</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        DataTable Example
                    </div>
                    <div class="card-body">
                        <table id="datatablesSimple">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Position</th>
                                    <th>Office</th>
                                    <th>Age</th>
                                    <th>Start date</th>
                                    <th>Salary</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
-->
			</div>
		</main>
		<footer class="py-4 bg-light mt-auto">
			<div class="container-fluid px-4">
				<div class="d-flex align-items-center justify-content-between small">
					<div class="text-muted">Copyright &copy; Your Website 2022</div>
					<div>
						<a href="#">Privacy Policy</a>
						&middot;
						<a href="#">Terms &amp; Conditions</a>
					</div>
				</div>
			</div>
		</footer>
	</div>
</div>

<!-- Event Modal -->
<div class="modal fade" id="addEvent" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content" onsubmit="makeEvent(event)">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel">새 일정</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="month" class="form-label">일정 제목</label>
					<input type="text" name="title" class="form-control" autocomplete="off" id="title" required>

				</div>
				<label for="month" class="form-label">시작날짜</label>
				<input type="text" name="month" autocomplete="off" id="startdatepicker" required>
				<br />
				<label for="month" class="form-label">종료날짜</label>
				<input type="text" name="month" autocomplete="off" id="enddatepicker">
				<br />
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
				<button type="submit" class="btn btn-primary">생성</button>
			</div>
		</form>
	</div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<!--<script src="assets/demo/chart-area-demo.js"></script>
<script src="assets/demo/chart-bar-demo.js"></script>-->
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
<script src="js/datatables-simple-demo.js"></script>

<!-- 풀캘린더 -->
<script src='js/fullcal/main.js'></script>
<script src='js/fullcal/locales-all.js'></script>

<!-- 데이트피커용 j쿼리 -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>

	//document.addEventListener('DOMContentLoaded', function() {
	let calendarEl = document.getElementById('calendar');
	let calendar = new FullCalendar.Calendar(calendarEl, {
		locale: 'ko',
		headerToolbar: {
			left: 'title',
			center: '',
			right: 'prev,next,today'
		},
		height: 'auto',
		selectable: true,
		select: function(info) {
			$("#startdatepicker").datepicker();
			$("#enddatepicker").datepicker();
			//시작일 설정
			$("#startdatepicker").datepicker("setDate", info.start);
			$("#enddatepicker").datepicker("setDate", info.end);

			$('#startdatepicker').datepicker("option", "onClose", function(selectedDate) {
				$("#enddatepicker").datepicker("option", "minDate", selectedDate);
			});

			$('#enddatepicker').datepicker("option", "minDate", $("#startdatepicker").val());


			$('#addEvent').modal('show');
		},
		weekends: true,
		eventClick: function(info) {
			let title = info.event.startStr;
			let start = info.event.endStr;
			let end = info.event.endStr;
			let obj = JSON.stringify({
				"title": title,
				"start": start,
				"end": end
			});
			let answer = confirm("일정을 삭제하시겠습니까?");
			if (answer) {
				$.ajax({
					url: "delEvent.do",
					contentType: 'application/json',
					type: 'post',
					data: obj,
					contentType: "application/json; charset=utf-8",
					dataType: "text",
					success: function(data) {
						location.href = 'index.do';
					},
					error: function(error) {
						location.href = 'index.do';
					}

				});
			}
		}
	});
	calendar.render();


	//});

	function makeEvent(event) {
		event.preventDefault();

		let title = document.getElementById('title').value;
		let start = document.getElementById('startdatepicker').value;
		let end = document.getElementById('enddatepicker').value;


		let obj = JSON.stringify({
			"title": title,
			"start": start,
			"end": end
		});
		$.ajax({
			url: "addEvent.do",
			contentType: 'application/json',
			type: 'post',
			data: obj,
			contentType: "application/json; charset=utf-8",
			dataType: "text",
			success: function(data) {
				location.href = 'index.do';
			},
			error: function(error) {
				location.href = 'index.do';
			}

		});
	}
</script>

</body>

</html>