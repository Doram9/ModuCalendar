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
    <a class="navbar-brand ps-3" href="index.html">Modu Calendar</a>
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
                <li><a class="dropdown-item" href="#!">로그인</a></li>
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
                                만나기 좋은 날
                                <button class="btn btn-link">결과 공유하기</button>
                            </div>
                            <div class="card-body">
                                <div class="row justify-content-center">
                                    <div class="card text-dark bg-warning mb-3" style="max-width: 18rem;">
                                        <div class="card-header">1st</div>
                                        <div class="card-body">
                                            <h5 class="card-title">00-00-00</h5>
                                            <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="row justify-content-center">
                                    <div class="card text-white bg-secondary mb-3" style="max-width: 18rem;">
                                        <div class="card-header">2nd</div>
                                        <div class="card-body">
                                            <h5 class="card-title">00-00-00</h5>
                                            <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                        </div>
                                    </div>

                                    <div class="col-1"></div><!-- 공백 -->
                                    <div class="card text-dark bg-light mb-3" style="max-width: 18rem;">
                                        <div class="card-header">3rd</div>
                                        <div class="card-body">
                                            <h5 class="card-title">00-00-00</h5>
                                            <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                        </div>
                                    </div>
                                </div>



                            </div>
                        </div>
                    </div>
                    <div class="col-xl-6">
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-bar me-1"></i>
                                투표 기한
                            </div>
                            <div class="row mt-3 justify-content-center">
                                <div class="col-4 h3">투표기한까지</div>
                                <div class="time col-4">
                                    <span id="d-day-day">00</span>
                                    <span class="col h3">일</span>
                                    <span id="d-day-hour">00</span>
                                    <span class="col h3">시간</span>
                                    <span id="d-day-min">00</span>
                                    <span class="col h3">분</span>
                                    <span id="d-day-sec">00</span>
                                    <span class="col h3">초</span>
                                </div>

                                <button type="button" class="btn btn-outline-danger col-6" id="voteBtn" data-bs-toggle="modal" data-bs-target="#voteModal">
                                    투표하기
                                </button>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        참여자 명단
                    </div>
                    <div class="card-body">
                        <div class="col-2">
                            <div class="p-2 border bg-light rounded-pill">username</div>
                        </div>
                        <div class="row mt-2 justify-content-center">
                            <button class="btn btn-warning col-3 offset-1" onclick="kakaoInvite()" id="create-kakao-link-btn">초대 <i class="bi bi-share"></i></button>
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
                    <label for="exampleFormControlInput1" class="form-label">일정 제목</label>
                    <input type="text" name="title" class="form-control" autocomplete="off" id="title" required>

                </div>
                <label for="exampleFormControlInput1" class="form-label">시작날짜</label>
                <input type="text" name="month" autocomplete="off" id="startdatepicker" required>
                <br />
                <label for="exampleFormControlInput1" class="form-label">종료날짜</label>
                <input type="text" name="month" autocomplete="off" id="enddatepicker">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="submit" class="btn btn-primary">생성</button>
            </div>
        </form>
    </div>
</div>

<!-- ---------------------투표모달--------------------------------- -->
<!-- Modal -->
<div class="modal fade" id="voteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" action="planPage.html">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">투표하기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="modalCal" class="mt-2"></div>
                <!-- Page content-->
                <div class="container-fluid">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked>
                        <label class="form-check-label" for="inlineRadio1">가능날표시</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
                        <label class="form-check-label" for="inlineRadio2">불가능날표시</label>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-danger" onclick="vote()">투표</button>
            </div>
        </form>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="assets/demo/chart-area-demo.js"></script>
<script src="assets/demo/chart-bar-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
<script src="js/datatables-simple-demo.js"></script>

<!-- 풀캘린더 -->
<script src='js/fullcal/main.js'></script>
<script src='js/fullcal/locales-all.js'></script>

<!-- 데이트피커용 j쿼리 -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
    function deleteRoom(roomcode, title) {
        let answer = confirm("정말로 방을 삭제하시겠습니까?(다른 사람의 약속방 리스트에서는 삭제되지 않습니다.)");
        if(answer) {
            location.href = 'delPlan.do?roomcode=' + roomcode + "&title=" + title;
        }
    }

</script>

<script>
    let posdays = new Set();
    let negdays = new Set();
    let redcol = 'rgba(232, 38, 37, 0.9)';
    let greencol = 'rgba(113, 232, 42, 0.9)';
    $('#voteModal').on('shown.bs.modal', function(e) { //모달 실행이 끝나면 실행되는 function
        let calendarEl = document.getElementById('modalCal');
        let calendar = new FullCalendar.Calendar(calendarEl, {
            locale: 'ko',
            headerToolbar: {
                left: '',
                center: 'title',
                right: ''
            },
            height: 'auto',
            initialView: 'dayGridMonth',
            selectable: false,
            dateClick: function DateClick(info) {
                let day = String(info.dateStr);
                if(document.getElementById("inlineRadio1").checked) {
                    if(info.dayEl.style.backgroundColor == greencol) {
                        posdays.delete(day);
                        info.dayEl.style.backgroundColor = '';
                        console.log(info.dayEl.style.backgroundColor);
                    } else if(info.dayEl.style.backgroundColor == redcol) {
                        negdays.delete(day);
                        posdays.add(day);
                        info.dayEl.style.backgroundColor = greencol;
                        console.log(info.dayEl.style.backgroundColor);
                    } else {
                        posdays.add(day);
                        info.dayEl.style.backgroundColor = greencol;
                        console.log(info.dayEl.style.backgroundColor);
                    }
                } else {
                    if(info.dayEl.style.backgroundColor == redcol) {
                        negdays.delete(day);
                        info.dayEl.style.backgroundColor = '';
                        console.log(info.dayEl.style.backgroundColor);
                    } else if(info.dayEl.style.backgroundColor == greencol) {
                        posdays.delete(day);
                        negdays.add(day);
                        info.dayEl.style.backgroundColor = redcol;
                        console.log(info.dayEl.style.backgroundColor);
                    } else {
                        negdays.add(day);
                        info.dayEl.style.backgroundColor = redcol;
                        console.log(info.dayEl.style.backgroundColor);
                    }

                }
            }
        });
        calendar.render();
    });

    function vote() {
        let sendPosdays = Array.from(posdays);
        let sendNegdays = Array.from(negdays);
        let roomcode = "";
        let obj = JSON.stringify({"posdays" : sendPosdays, "negdays" : sendNegdays, "roomcode" : roomcode});
        let url = "planPage.do?id=" + "";
        $.ajax({
            url: "voteDate.do",
            contentType: 'application/json',
            type: 'post',
            data : obj,
            contentType: "application/json; charset=utf-8",
            dataType: "text",
            success: function(data) {
                alert("성공");
                location.href = url;
            },
            error: function(error) {
                alert("에러");
                location.href = url;
            }

        });
    }

</script>


</body>

</html>