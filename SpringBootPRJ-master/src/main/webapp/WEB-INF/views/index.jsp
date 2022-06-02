<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.dto.EventDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.EncryptUtil" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
	UserInfoDTO pDTO = (UserInfoDTO) request.getAttribute("UserInfoDTO");

	if (pDTO == null) {
		pDTO = new UserInfoDTO();
	}
	List<EventDTO> eList = pDTO.getEventList();

	if(eList == null) {
		eList = new ArrayList<>();
	}
	List<String> appoList = pDTO.getAppoList();

	if(appoList == null) {
		appoList = new ArrayList<>();
	}
%>
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
				<li><a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#userInfo">내정보</a></li>
				<li><hr class="dropdown-divider" /></li>
				<li><a class="dropdown-item" href="logout">로그아웃</a></li>
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
					<a class="nav-link" data-bs-toggle="modal" data-bs-target="#userInfo">
						<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
						<%= pDTO.getUserName()%>
					</a>
					<div class="sb-sidenav-menu-heading">약속 목록</div>
					<%
						int i = 0;
						for(String appoCode : appoList) {
							String parse[] = appoCode.split("\\*_\\*");
							String title = parse[0];
							String code = parse[1];
					%>
						<div class="nav-link collapsed">
							<div class="sb-nav-link-icon">
								<i class="fas fa-table"></i>
							</div>
							<a class="link-warning" style="text-decoration-line: none" href="appo?code=<%= code%>&title=<%= title%>">
								<%= title%>
							</a>
							<div class="sb-sidenav-collapse-arrow" data-bs-toggle="collapse" data-bs-target="#collapseLayout<%=i%>" aria-expanded="false" aria-controls="collapseLayouts">
								<i class="fas fa-angle-down"></i>
							</div>
						</div>
						<div class="collapse" id="collapseLayout<%=i%>" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
							<nav class="sb-sidenav-menu-nested nav">
								<button class="nav-link btn btn-outline-danger" onclick="deleteAppo('<%= title%>', '<%= code%>')">방 나가기</button>
							</nav>
						</div>
					<%
							i++;
						}
					%>

					<button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#addAppo">
						약속 추가하기
					</button>
					<button class="btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#inputCode">초대코드 입력하기</button>
					<div class="sb-sidenav-menu-heading">프로젝트 목록</div>
					<a class="nav-link" href="charts.html">
						<div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
						팀프로젝트
					</a>

					<button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#teamPrj">
						프로젝트 추가하기
					</button>
					<button class="btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#appoCode">프로젝트 참가하기</button>
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
					<div class="col-xl-5">
						<div class="card mb-4">
							<div class="card-header">
								<i class="fas fa-chart-area me-1"></i>
								내 일정
							</div>
							<div class="card-body">
								<div id="calendar" class="mt-2"></div> <!-- 캘린더 -->
							</div>
						</div>
					</div>
					<div class="col-xl-7">
						<div class="card mb-4">
							<div class="card-header">
								<i class="fas fa-chart-bar me-1"></i>
								내 프로젝트
							</div>

							<!--마일스톤 -->
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

<!-- UserInfo Modal -->
<div class="modal fade" id="userInfo" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabelUserInfo" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabelUserInfo">내 정보</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="userName" class="form-label">이름</label>
					<p name="userName" class="form-control" id="userName"><%= pDTO.getUserName()%></p>
				</div>
				<div class="mb-3">
					<label for="userRegDt" class="form-label">가입일</label>
					<p name="userRegDt" class="form-control" id="userRegDt"><%= pDTO.getRegDt()%></p>
				</div>
				<div class="mb-3">
					<label for="userPw" class="form-label">비밀번호</label> <button class="btn btn-sm btn-outline-success" onclick="chgPw()">비밀번호 변경하기</button>
					<p name="userPw" class="form-control" id="userPw">**********</p>
				</div>
				<div class="mb-3 row justify-content-end">
					<button class="btn btn-sm btn-warning btn-outline-danger col-6" onclick="deleteUser()">회원탈퇴하기</button>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Appo Modal -->
<div class="modal fade" id="addAppo" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content" action="createAppo" method="get">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel">새 약속</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="exampleFormControlInput1" class="form-label">제목</label>
					<input type="text" name="title" class="form-control" autocomplete="off" id="exampleFormControlInput1" placeholder="~~에 만나요" required>

				</div>
				<p>만나는 년, 월 <input type="text" name="month" autocomplete="off" id="monthpicker" required></p>
				<p>투표 기한</p>
				<select name="deadline" class="form-select form-select-sm" aria-label=".form-select-sm example">
					<option value="1">앞으로 1일</option>
					<option value="3">앞으로 3일</option>
					<option value="5">앞으로 5일</option>
					<option value="7">앞으로 7일</option>
				</select>

				<p>투표 기한</p>
				<select name="region" class="form-select form-select-sm" aria-label=".form-select-sm example">
					<option value="서울">서울</option>
					<option value="인천">인천</option>
					<option value="경기북부">경기북부</option>
					<option value="경기남부">경기남부</option>
					<option value="강원영서">강원영서</option>
					<option value="강원영동">강원영동</option>
					<option value="대전">대전</option>
					<option value="세종">세종</option>
					<option value="충남">충남</option>
					<option value="충북">충북</option>
					<option value="광주">광주</option>
					<option value="전북">전북</option>
					<option value="전남">전남</option>
					<option value="부산">부산</option>
					<option value="대구">대구</option>
					<option value="울산">울산</option>
					<option value="경북">경북</option>
					<option value="경남">경남</option>
					<option value="제주">제주</option>
				</select>
			</div>


			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
				<button type="submit" class="btn btn-primary">생성</button>
			</div>
		</form>
	</div>
</div>

<!-- teamPrj Modal -->
<div class="modal fade" id="teamPrj" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel4" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content" action="mkPlan.do" method="post">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel4">새 프로젝트</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="exampleFormControlInput4" class="form-label">프로젝트 제목</label>
					<input type="text" name="title" class="form-control" autocomplete="off" id="exampleFormControlInput4" placeholder="" required>

				</div>
			</div>


			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
				<button type="submit" class="btn btn-primary">생성</button>
			</div>
		</form>
	</div>
</div>

<!-- Event Modal -->
<div class="modal fade" id="addEvent" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content" onsubmit="makeEvent(event)">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel3">새 일정</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="title" class="form-label">일정 제목</label>
					<input type="text" name="title" class="form-control" autocomplete="off" id="title" required>

				</div>
				<label for="startdatepicker" class="form-label">시작날짜</label>
				<input type="text" name="startMonth" autocomplete="off" id="startdatepicker" required>
				<br />
				<label for="enddatepicker" class="form-label">종료날짜</label>
				<input type="text" name="endMonth" autocomplete="off" id="enddatepicker">
				<br />
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
				<button type="submit" class="btn btn-primary">생성</button>
			</div>
		</form>
	</div>
</div>

<!-- inputCode Modal -->
<div class="modal fade" id="inputCode" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content" onsubmit="inputCode(event)">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel2">초대 코드 입력하기</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="exampleFormControlInput1" class="form-label">초대 코드</label>
					<input type="text" name="code" class="form-control" autocomplete="off" id="inviteCode" required>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
				<button type="submit" class="btn btn-primary">참가</button>
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

<!-- monthpicker -->
<script src="js/jquery.mtz.monthpicker.js"></script>

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
		events: [
				<%
					for(EventDTO eDTO : eList) {
						String id = eDTO.getEvent_id();
						String title = eDTO.getTitle();
						String start = eDTO.getStart();
						String end = eDTO.getEnd();
						%>
			{
				id : '<%=id%>',
				title  : '<%=title%>',
				start  : '<%=start%>',
				end    : '<%=end%>'
			},

				<%
					}
				%>
		],
		eventClick: function(info) {
			console.log(info.event.id);
			console.log(info.event.title);
			console.log(info.event.startStr);
			console.log(info.event.endStr);
			let eventId = info.event.id;
			let answer = confirm("일정을 삭제하시겠습니까?");
			if (answer) {
				$.ajax({
					url: "deleteEvent",
					contentType: 'application/json',
					type: 'get',
					data: {
						eventId : eventId
					},
					contentType: "application/json; charset=utf-8",
					dataType: "text",
					success: function(data) {
						location.href = '/';
					},
					error: function(error) {
						location.href = '';
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

		console.log(title);
		console.log(start);
		console.log(end);

		$.ajax({
			url: "addEvent",
			type: 'get',
			data: {
				"title": title,
				"start": start,
				"end": end
			},
			contentType: "application/json; charset=utf-8",
			dataType: "text",
			success: function(data) {
				location.href = '/';
			},
			error: function(error) {
				location.href = '/';
			}

		});
	}
</script>

<script>
	function inputCode(event) {
		event.preventDefault();
		let appoCode = document.getElementById('inviteCode').value;

		$.ajax({
			url: "inviteAppo",
			type: 'get',
			data: {
				"appoCode": appoCode,
			},
			contentType: "application/json; charset=utf-8",
			dataType: "text",
			success: function(data) {
				if(data == 0) {
					alert("해당하는 방이 존재하지 않습니다.");
				}else if(data == 2) {
					alert("이미 해당 방에 참가중입니다.");
				}
				location.href = '/';
			},
			error: function(error) {
				location.href = '/';
			}

		});
	}

</script>

<script>


	let now = new Date();
	let options = {
		pattern: 'yyyy-mm',
		selectedYear : now.getFullYear(),
		startYear: now.getFullYear(),
		finalYear: now.getFullYear() + 5,
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		openOnFocus: true,
		disableMonths: []
	};

	$("#monthpicker").monthpicker(options);


</script>

<script>
	$.datepicker.setDefaults({
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear: true,
		yearSuffix: '년',
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true

	});

</script>

<script>
	function deleteAppo(title, code) {

		if(confirm("방을 나가시겠습니까?")){

			$.ajax({
				url: "delAppo",
				type: 'get',
				data: {
					"title": title,
					"code": code
				},
				contentType: "application/json; charset=utf-8",
				dataType: "text",
				success: function(data) {
					if(data != 1) {
						alert("해당하는 방이 존재하지않습니다.");
					}
					location.href = '/';
				},
				error: function(error) {
					location.href = '/';
				}

			});
		}
	}

</script>

<script>
	function chgPw() {
		location.href = "resetPw?code=<%=CmmUtil.nvl(pDTO.getUserId())%>";
	}

	function deleteUser() {
		if(confirm("정말로 회원을 탈퇴하시겠습니까? (주의! 되돌릴 수 없습니다.)")){
			location.href = "deleteUser";
		}
	}
</script>

</body>

</html>