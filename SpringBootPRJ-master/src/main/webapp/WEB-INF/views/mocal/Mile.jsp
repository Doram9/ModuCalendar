<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.dto.EventDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
    UserInfoDTO pDTO = (UserInfoDTO) request.getAttribute("UserInfoDTO");
    String prjCode = (String) request.getParameter("prjCode");

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
                <form class="card mb-4" onsubmit="send(event)">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        내 마일스톤

                        <button type="button" class="btn btn-outline-danger">취소</button>
                        <button type="submit" class="btn btn-outline-success">저장</button>
                    </div>

                    <!-- 마일스톤 container -->
                    <div class="card-body container-fluid" id="milestone">
                        <!-- 첫줄 row-->
                        <div class="row mb-1">
                            <div class="offset-6 col-6 btn-primary">
                                <!-- 3:9 비율 -->
                                기간 :
                                <input type="text" name="month" autocomplete="off" id="startdatepicker" required>

                                <input type="text" name="month" autocomplete="off" id="enddatepicker" required>
                            </div>

                        </div>

                        <!-- 기간 추가 row-->
                        <div class="row">
                            <div class="col-5">
                                <div class="row">
                                    <div class="col-5">
                                        <div class="btn-primary" style="text-align: center">
                                            항목
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="btn-primary" style="text-align: center">
                                            시작일
                                        </div>
                                    </div>
                                    <div class="col-1"></div>
                                    <div class="col-3">
                                        <div class="btn-primary" style="text-align: center">
                                            종료일
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-7">
                                <div class="row" id="m_period" style="text-align: center">

                                </div>
                            </div>
                        </div>

                        <!-- object 추가 row -->
                        <form id="mileForm">
                            <div class="row mt-2" id="item_0">
                                <div class="col-5">
                                    <div class="row justify-content-around">
                                        <div class="col-5">
                                            <!-- 항목 obj 추가 row -->
                                            <div class="justify-content-center btn-group">
                                                <button type="button" class="btn btn-xs btn-secondary" onclick="addStep('item_0')">+</button>

                                                <input class="form-control" id="itemValue_0" type="text" placeholder="항목명" onchange="chgStepValue('item_0')" required>

                                                <button type="button" class="btn btn-secondary" disabled>-</button>
                                            </div>
                                        </div>
                                        <div class="col-3">
                                            <input class="form-control" autocomplete="off" required>
                                        </div>
                                        <div class="col-1">
                                            <p style="text-align: center">~</p>
                                        </div>
                                        <div class="col-3">
                                            <input class="form-control" autocomplete="off" required>
                                        </div>

                                    </div>
                                </div>

                                <!-- 마일스톤 생성col -->
                                <div class="col-7">
                                    <div class="row" id="stone_0">
                                        <div class="col-1 btn btn-outline-primary"></div>
                                    </div>


                                </div>
                            </div>
                        </form>


                    </div>
                </form>
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

    $("#startdatepicker").datepicker();
    $("#enddatepicker").datepicker();
</script>

<script>
    //시작일 선택시 종료일 제한걸기-선택일자로 부터 1년뒤 전달의 마지막날로
    $("#startdatepicker").datepicker("option", "onClose", function(selectedDate) {
        if(document.getElementById("startdatepicker").value != '') {

            allInfo.startDate = document.getElementById("startdatepicker").value;

            //yyyy-mm-dd를 split으로 나누고 년,월에 하나씩 넣기, 일은 전달의 마지막 날을 넣기 때문에 0
            let yyyymmdd = selectedDate.split('-');
            let maxDate = new Date(Number(yyyymmdd[0]) + 1, Number(yyyymmdd[1]) - 1, 0);
            $("#enddatepicker").datepicker("option", "minDate", selectedDate);
            $("#enddatepicker").datepicker("option", "maxDate", maxDate);

        }

    });

    //종료일 선택시 마일스톤 생성
    $("#enddatepicker").datepicker("option", "onClose", function(selectedDate) {
        if(document.getElementById("enddatepicker").value != '' && document.getElementById("startdatepicker").value != '') {

            allInfo.endDate = document.getElementById("enddatepicker").value;

            //#m_period 하위의 모든 태그 제거
            $("#m_period").empty();
            //기간이 총 몇달인지 계산
            let startY = $("#startdatepicker").datepicker('getDate').getFullYear();
            let startM = $("#startdatepicker").datepicker('getDate').getMonth();
            let endY = $("#enddatepicker").datepicker('getDate').getFullYear();
            let endM = $("#enddatepicker").datepicker('getDate').getMonth();

            //기간 = 년도가 같으면 끝달 - 시작달, 년도가 다르면 12 - (시작달 - 끝달)
            let periodM = (Number(startY) - Number(endY) == 0) ? Number(endM) - Number(startM) : 12 - (Number(startM) - Number(endM));

            console.log(periodM);

            //달 개수 만큼 div 생성, 텍스트에는 해당 월입력
            //periodM까지 반복 즉, 최소 1번은 반복
            for (let i = 0; i <= periodM; i++) {
                let mileM = new Date(Number(startY), Number(startM + i), 1).getMonth() + 1;
                let attr = $("<div>")
                $("#m_period").append($("<div class='col-1 btn-primary'>").text(mileM));
            }
        }
    });
</script>

<script>
    //마일스톤 정보
    let mileInfo = [{
        "itemValue" : "",
        "itemStartDate" : "", //프로젝트 시작일
        "itemEndDate" : "", //프로젝트 마감일
    }];




    //단계 생성 count 변수
    let stepCnt = 0;


    function addStep(event) {
        console.log(event);
        // event = step_n
        let splitedEvent = event.split('_');


        let stepInfo = {
            "itemValue" : "",
            "itemStartDate" : "", //프로젝트 시작일
            "itemEndDate" : "", //프로젝트 마감일
        };

        //+1 하는 이유는 splice 시작위치를 event객체 바로 뒤로 지정해주기위해
        mileInfo.splice(splitedEvent[1] + 1, 0, stepInfo);

        //count + 1
        stepCnt += 1;

        $("#" + event).after($(
            `<div class="row mt-2" id="item_\${stepCnt}">
                                <div class="col-5">
                                    <div class="row justify-content-around">
                                        <div class="col-5">
                                            <!-- 항목 obj 추가 row -->
                                            <div class="justify-content-center btn-group">
                                                <button type="button" class="btn btn-xs btn-secondary" onclick="addStep('item_\${stepCnt}')">+</button>

                                                <input class="form-control" id="itemValue_\${stepCnt}" type="text" placeholder="항목명" onchange="chgStepValue('item_\${stepCnt}')" required>

                                                <button type="button" class="btn btn-secondary" onclick="rmStep('item_\${stepCnt}')">-</button>
                                            </div>
                                        </div>
                                        <div class="col-3">
                                            <input class="form-control" autocomplete="off" required>
                                        </div>
                                        <div class="col-1">
                                            <p style="text-align: center">~</p>
                                        </div>
                                        <div class="col-3">
                                            <input class="form-control" autocomplete="off" required>
                                        </div>

                                    </div>
                                </div>

                                <!-- 마일스톤 생성col -->
                                <div class="col-7">
                                    <div class="row" id="stone_\${stepCnt}">
                                        <div class="col-1 btn btn-outline-primary"></div>
                                    </div>


                                </div>

                            </div>`

        ));
    }

    function rmStep(event) {
        console.log(event);
        // event = step_n
        let splitedEvent = event.split('_');

        //데이터 삭제
        mileInfo.splice(splitedEvent[1], 1, );
        //태그 삭제
        $("#" + event).remove();

    }


    function chgStepValue(event) {
        // event = step_n
        let splitedEvent = event.split('_');


        //단계명
        let stepValue = document.getElementById("itemValue_" + splitedEvent[1]).value;
        console.log(stepValue);

        mileInfo[splitedEvent[1]].itemValue = stepValue;

    }

    function send(event) {
        event.preventDefault();
        let obj = JSON.stringify({"allInfo" : allInfo, "prjCode" : <%= prjCode%>});
        alert(obj);

        $.ajax({
            url: "updateMile",
            type: 'get',
            data: obj,
            dataType: "text",
            contentType: "application/json; charset=utf-8",

            success: function(result) {
                location.href = "/";
            },
            error: function(request,status,error) {
                //alert("아이디 또는 비밀번호가 일치하지 않습니다.");
                //location.reload();
            }

        });
    }

</script>


</body>
</html>