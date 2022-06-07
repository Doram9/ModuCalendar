<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.dto.EventDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.PrjInfoDTO" %>
<%@ page import="kopo.poly.dto.MileDTO" %>
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

    PrjInfoDTO rDTO = (PrjInfoDTO) request.getAttribute("PrjInfoDTO");
    if(rDTO == null) {
        rDTO = new PrjInfoDTO();
    }
    List<MileDTO> mList = rDTO.getPrjMileInfo();
    if(mList == null) {
        mList = new ArrayList<>();
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
                    <div class="mt-2"></div>
                    <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#addAppo">
                        약속 추가하기
                    </button>
                    <div class="mt-2"></div>
                    <button type="button" class="btn btn-dark btn-outline-warning" data-bs-toggle="modal" data-bs-target="#inputCode">초대코드 입력하기</button>
                </div>
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

                        <a type="button" class="btn btn-outline-danger" href="prj?code=<%= rDTO.getPrjCode()%>">취소</a>
                        <button type="submit" class="btn btn-outline-success">저장</button>
                    </div>

                    <!-- 마일스톤 container -->
                    <div class="card-body container-fluid" id="milestone">
                        <!-- 첫줄 row-->
                        <div class="row mb-1 btn-primary justify-content-end">
                            <div class="col-8">
                                <!-- 5:7 비율 -->
                                기간 :
                                <input type="text" name="month" autocomplete="off" id="startdatepicker" value="<%=rDTO.getPrjStartDate()%>" required>

                                <input type="text" name="month" autocomplete="off" id="enddatepicker" value="<%=rDTO.getPrjEndDate()%>" required>
                            </div>

                        </div>

                        <!-- 기간 추가 row-->
                        <div class="row">
                            <div class="col-4">
                                <div class="btn-primary" style="text-align: center">
                                    항목
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="btn-primary" style="text-align: center">
                                    시작일
                                </div>
                            </div>
                            <div class="col-1"></div>
                            <div class="col-2">
                                <div class="btn-primary" style="text-align: center">
                                    종료일
                                </div>
                            </div>
<%--                            <div class="col-7">--%>
<%--                                <div class="row" id="m_period" style="text-align: center">--%>

<%--                                </div>--%>
<%--                            </div>--%>
                        </div>

                        <!-- object 추가 row -->
                        <div id="mileForm">
                            <div class="row mt-2" id="item_0">
                                        <div class="col-4">
                                            <!-- 항목 obj 추가 row -->
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-xs btn-secondary" onclick="addStep('item_0')">+</button>

                                                <input class="form-control" id="itemValue_0" type="text" placeholder="항목명" onchange="chgStepValue('item_0')" required>

                                                <button type="button" class="btn btn-secondary" disabled>-</button>
                                            </div>
                                        </div>
                                        <div class="col-2">
                                            <input class="form-control" name="month" id="itemStartDate_0" onchange="chgDateValue('itemStartDate_0')" type="text" autocomplete="off" required>
                                        </div>
                                        <div class="col-1">
                                            <p style="text-align: center">~</p>
                                        </div>
                                        <div class="col-2">
                                            <input class="form-control" name="month" id="itemEndDate_0" onchange="chgDateValue('itemEndDate_0')" type="text" autocomplete="off" required>
                                        </div>

                                <!-- 마일스톤 생성col -->
<%--                                <div class="col-7">--%>
<%--                                    <div class="row" id="stone_0">--%>
<%--                                        <div class="col-1" style="background-color: #0a58ca">a</div>--%>
<%--                                    </div>--%>


<%--                                </div>--%>
                            </div>
                        </div>


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
                    <label for="userPw" class="form-label">비밀번호</label> <button class="btn btn-sm btn-outline-info" onclick="chgPw()">비밀번호 변경하기</button>
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
                <div class="mb-3"></div>
                <p>약속 장소</p>
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
                <button type="submit" class="btn btn-warning">생성</button>
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
    $("#itemStartDate_0").datepicker();
    $("#itemEndDate_0").datepicker();

</script>

<script>
    //시작일 선택시 종료일 제한걸기-선택일자로 부터 1년뒤 전달의 마지막날로
    $("#startdatepicker").datepicker("option", "onClose", function(selectedDate) {
        if(document.getElementById("startdatepicker").value != '') {

            //allInfo.startDate = document.getElementById("startdatepicker").value;

            //yyyy-mm-dd를 split으로 나누고 년,월에 하나씩 넣기, 일은 전달의 마지막 날을 넣기 때문에 0
            let yyyymmdd = selectedDate.split('-');
            let maxDate = new Date(Number(yyyymmdd[0]) + 1, Number(yyyymmdd[1]) - 1, 0);
            $("#enddatepicker").datepicker("option", "minDate", selectedDate);
            $("#enddatepicker").datepicker("option", "maxDate", maxDate);

        }

    });

    // //종료일 선택시 마일스톤 생성
    // $("#enddatepicker").datepicker("option", "onClose", function(selectedDate) {
    //     if(document.getElementById("enddatepicker").value != '' && document.getElementById("startdatepicker").value != '') {
    //
    //         //allInfo.endDate = document.getElementById("enddatepicker").value;
    //
    //         //#m_period 하위의 모든 태그 제거
    //         $("#m_period").empty();
    //         //기간이 총 몇달인지 계산
    //         let startY = $("#startdatepicker").datepicker('getDate').getFullYear();
    //         let startM = $("#startdatepicker").datepicker('getDate').getMonth();
    //         let endY = $("#enddatepicker").datepicker('getDate').getFullYear();
    //         let endM = $("#enddatepicker").datepicker('getDate').getMonth();
    //
    //         //기간 = 년도가 같으면 끝달 - 시작달, 년도가 다르면 12 - (시작달 - 끝달)
    //         let periodM = (Number(startY) - Number(endY) == 0) ? Number(endM) - Number(startM) : 12 - (Number(startM) - Number(endM));
    //
    //         console.log(periodM);
    //
    //         //달 개수 만큼 div 생성, 텍스트에는 해당 월입력
    //         //periodM까지 반복 즉, 최소 1번은 반복
    //         for (let i = 0; i <= periodM; i++) {
    //             let mileM = new Date(Number(startY), Number(startM + i), 1).getMonth() + 1;
    //             let attr = $("<div>")
    //             $("#m_period").append($("<div class='col-1 btn-primary'>").text(mileM));
    //         }
    //     }
    // });
</script>

<script>
    //마일스톤 정보
    let mileInfo = [{
        "itemIdx" : 0,
        "itemValue" : "",
        "itemStartDate" : "", //프로젝트 시작일
        "itemEndDate" : "", //프로젝트 마감일
    }];




    //단계 생성 count 변수
    let stepCnt = 0;


    function addStep(event) {
        console.log(event);
        // event = step_n
        let splitedEvent = event.split('_'); //splitedEvent[1] 에는 번호가 들어감

        let indexNum = mileInfo.findIndex(i => i.itemIdx == splitedEvent[1]);

        //count + 1
        stepCnt += 1;

        let stepInfo = {
            "itemIdx" : stepCnt,
            "itemValue" : "",
            "itemStartDate" : "", //프로젝트 시작일
            "itemEndDate" : "", //프로젝트 마감일
        };

        //+1 하는 이유는 splice 시작위치를 event객체 바로 뒤로 지정해주기위해
        mileInfo.splice(indexNum + 1, 0, stepInfo);



        $("#" + event).after($(
            `<div class="row mt-2" id="item_\${stepCnt}">
                                        <div class="col-4">
                                            <!-- 항목 obj 추가 row -->
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-xs btn-secondary" onclick="addStep('item_\${stepCnt}')">+</button>

                                                <input class="form-control" id="itemValue_\${stepCnt}" type="text" placeholder="항목명" onchange="chgStepValue('item_\${stepCnt}')" required>

                                                <button type="button" class="btn btn-secondary" onclick="rmStep('item_\${stepCnt}')">-</button>
                                            </div>
                                        </div>
                                        <div class="col-2">
                                            <input class="form-control" name="month" id="itemStartDate_\${stepCnt}" onchange="chgDateValue('itemStartDate_\${stepCnt}')" type="text" autocomplete="off" required>
                                        </div>
                                        <div class="col-1">
                                            <p style="text-align: center">~</p>
                                        </div>
                                        <div class="col-2">
                                            <input class="form-control" name="month" id="itemEndDate_\${stepCnt}" onchange="chgDateValue('itemEndDate_\${stepCnt}')" type="text" autocomplete="off" required>
                                        </div>
                            </div>`

        ));
        //yyyy-mm-dd를 split으로 나누고 년,월에 하나씩 넣기, 일은 전달의 마지막 날을 넣기 때문에 0

        $(`#itemStartDate_\${stepCnt}`).datepicker();
        $(`#itemEndDate_\${stepCnt}`).datepicker();
    }

    function rmStep(event) {
        console.log(event);
        // event = step_n
        let splitedEvent = event.split('_');

        let indexNum = mileInfo.findIndex(i => i.itemIdx == splitedEvent[1]);

        //데이터 삭제
        mileInfo.splice(indexNum, 1, );
        //태그 삭제
        $("#" + event).remove();

    }


    function chgStepValue(event) {
        // event = step_n
        let splitedEvent = event.split('_');

        let indexNum = mileInfo.findIndex(i => i.itemIdx == splitedEvent[1]);


        //단계명
        let stepValue = document.getElementById("itemValue_" + splitedEvent[1]).value;
        console.log(stepValue);

        mileInfo[indexNum].itemValue = stepValue;
    }

    function chgDateValue(event) {
        // event = step_n
        let splitedEvent = event.split('_');

        let indexNum = mileInfo.findIndex(i => i.itemIdx == splitedEvent[1]);

        //설정일자
        let stepValue = document.getElementById(event).value;

        let key = splitedEvent[0];
        if(key == 'itemStartDate') {
            mileInfo[indexNum].itemStartDate = stepValue;
        } else {
            mileInfo[indexNum].itemEndDate = stepValue;
        }

        console.log(stepValue);



    }

    function send(event) {
        event.preventDefault();

        let obj = JSON.stringify(mileInfo);
        console.log(obj);
        let startDate = document.getElementById("startdatepicker").value;
        let endDate = document.getElementById("enddatepicker").value;
        let prjCode = "<%= rDTO.getPrjCode()%>";

        $.ajax({
            url: "updateMile",
            type: 'get',
            data: {
                "mileInfo" : obj,
                "prjCode" : prjCode,
                "prjStartDate" : startDate,
                "prjEndDate" : endDate

            },
            dataType: "text",
            contentType: "application/json; charset=utf-8",

            success: function(result) {
                if(result == 1) {
                    alert("성공");
                }
                location.href = "/";
            },
            error: function(request,status,error) {

            }

        });
    }

</script>


</body>
</html>