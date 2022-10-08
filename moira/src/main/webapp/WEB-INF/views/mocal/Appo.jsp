<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    AppoInfoDTO aDTO = (AppoInfoDTO) request.getAttribute("AppoInfoDTO");
    UserInfoDTO pDTO = (UserInfoDTO) request.getAttribute("UserInfoDTO");
    DustInfoDTO wDTO = (DustInfoDTO) request.getAttribute("DustInfoDTO");

    if(wDTO == null) {
        wDTO = new DustInfoDTO();
    }

    if (pDTO == null) {
        pDTO = new UserInfoDTO();
    }
    List<EventDTO> eList = pDTO.getEventList();

    if (eList == null) {
        eList = new ArrayList<>();
    }
    List<String> appoList = pDTO.getAppoList();

    if (appoList == null) {
        appoList = new ArrayList<>();
    }

%>

        <%
            List<VoteInfoDTO> userlist = aDTO.getUserlist();
            String yyyymm = CmmUtil.nvl(aDTO.getYyyymm());
            String deadline = CmmUtil.nvl(aDTO.getDeadline());
            String firdate = CmmUtil.nvl(aDTO.getFirdate());
            String secdate = CmmUtil.nvl(aDTO.getSecdate());
            String thidate = CmmUtil.nvl(aDTO.getThidate());
            String appoTitle = CmmUtil.nvl(aDTO.getTitle());

//            if (firdate == "") {
//                firdate = "아직 만나기 적당한 날짜가 없습니다.";
//            }
//            if (secdate == "") {
//                secdate = "아직 만나기 적당한 날짜가 없습니다.";
//            }
//            if (thidate == "") {
//                thidate = "아직 만나기 적당한 날짜가 없습니다.";
//            }

            if (userlist == null) {
                userlist = new ArrayList<>();
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
    <title>Modu Calendar_appo</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
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
                        String code = null;
                        int i = 0;
                        for (String appoCode : appoList) {
                            String parse[] = appoCode.split("\\*_\\*");
                            String title = parse[0];
                            code = parse[1];
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
                <div class="row">
                    <div class="col-xl-7">
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-area me-1"></i>
                                만나기 좋은 날
                                <button class="btn btn-outline-warning" onclick="copyAppoCode()">약속방 코드 복사하기</button>
                            </div>
                            <div class="card-body" style="text-align: center;">
                                <div class="row justify-content-center">
                                    <div class="card text-dark bg-warning mb-3" style="max-width: 18rem;">
                                        <div class="card-header">
                                            1st
                                        </div>
                                        <div class="card-body">
                                            <h5 class="card-title"><%=firdate%></h5>
                                            <%
                                                if(firdate != "") {
                                            %>
                                            <button class="btn btn-warning" onclick="addEventFromAppo('<%=firdate%>')">내 캘린더에 추가하기</button>
                                            <%
                                                }
                                            %>

                                        </div>
                                    </div>
                                </div>
                                <div class="row justify-content-center">
                                    <div class="card text-dark bg-light mb-3" style="max-width: 18rem;">
                                        <div class="card-header">2nd</div>
                                        <div class="card-body">
                                            <h5 class="card-title"><%=secdate%></h5>
                                            <%
                                                if(secdate != "") {
                                            %>
                                            <button class="btn btn-light" onclick="addEventFromAppo('<%=secdate%>')">내 캘린더에 추가하기</button>
                                            <%
                                                }
                                            %>
                                        </div>
                                    </div>

                                    <div class="col-1"></div><!-- 공백 -->
                                    <div class="card text-white bg-secondary mb-3" style="max-width: 18rem;">
                                        <div class="card-header">3rd</div>
                                        <div class="card-body">
                                            <h5 class="card-title"><%=thidate%></h5>
                                            <%
                                                if(thidate != "") {
                                            %>
                                            <button class="btn btn-secondary" onclick="addEventFromAppo('<%=thidate%>')">내 캘린더에 추가하기</button>
                                            <%
                                                }
                                            %>
                                        </div>
                                    </div>
                                </div>



                            </div>
                        </div>

                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                참여자 명단
                            </div>
                            <div class="card-body row">
                                <%
                                    for (VoteInfoDTO vDTO : userlist) {
                                        String username = vDTO.getUsername();
                                        boolean votetf = vDTO.isVotetf();
                                        String checktag = "";
                                        if (votetf) {
                                            checktag = "<i class=\"bi bi-check2-circle\" style=\"color: red\"></i>";
                                        }

                                %>
                                <div class="col-2">
                                    <div class="p-2 border bg-light rounded-pill"><%=username%><%=checktag%></div>
                                </div>
                                <%
                                    }
                                %>
                                <div class="row mt-2 justify-content-center">
                                    <button class="btn btn-warning col-3 offset-1" onclick="kakaoInvite()" id="create-kakao-link-btn">카카오톡으로 초대하기 <i class="bi bi-person-plus"></i></button>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="col-xl-5">
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-bar me-1"></i>
                                투표 기한
                            </div>
                            <div class="row mt-3 justify-content-center">
                                <div class="col-12 h1" style="text-align: center"><%= appoTitle%></div>
                                <div class="col-5 h4" style="text-align: center">투표기한까지</div>
                                <div class="time col-7">
                                    <span class="col h4" id="d-day-day">00</span>
                                    <span class="col h4">일</span>
                                    <span class="col h4" id="d-day-hour">00</span>
                                    <span class="col h4">시간</span>
                                    <span class="col h4" id="d-day-min">00</span>
                                    <span class="col h4">분</span>
                                    <span class="col h4" id="d-day-sec">00</span>
                                    <span class="col h4">초</span>
                                </div>
                                <div class="spinner-border text-primary" role="status" id="load">
                                    <span class="visually-hidden">Loading...</span>
                                </div>
                                <button type="button" id="voteBtn" data-bs-toggle="modal" data-bs-target="#voteModal" hidden>
                                </button>
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
                    <label for="userPw" class="form-label">비밀번호</label> <button class="btn btn-sm btn-outline-info" onclick="chgPw()">비밀번호 변경하기</button>
                    <p name="userPw" class="form-control" id="userPw">**********</p>
                </div>
                <div class="mb-3 row justify-content-end">
                    <button class="btn btn-sm btn-warning btn-outline-danger col-6" data-bs-toggle="modal" data-bs-target="#deleteUser">회원탈퇴하기</button>
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
                <input type="text" class="form-control" name="userName" value="<%=pDTO.getUserName()%>" hidden />

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
<!-- ---------------------투표모달--------------------------------- -->
<!-- Modal -->
<div class="modal fade" id="voteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">투표하기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <i class="bi bi-square-fill" style="color : #004EA2"></i>미세먼지 정보
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
<!-- deleteUser Modal -->
<div class="modal fade" id="deleteUser" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" onsubmit="deletePrj()">
            <div class="modal-header">
                <h5 class="modal-title" >회원 탈퇴하기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <div class="alert alert-danger" role="alert">
                    <p>경고! 탈퇴하면 다시 돌이킬 수 없습니다.</p>
                    <p>정말 탈퇴하시겠습니까?</p>
                </div>
                <label class="form-label">정말로 삭제하시려면 아래에 유저명을 입력해주세요.</label>
                <div class="form-floating mb-3">
                    <input type="text" name="code" class="form-control" autocomplete="off" placeholder="<%= pDTO.getUserName()%>" id="userNameForDelete" required>
                    <label for="userNameForDelete"><%= pDTO.getUserName()%></label>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-danger" onclick="deleteUser()" id="deleteUserBtn" disabled>삭제</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
<script src="js/datatables-simple-demo.js"></script>

<!-- 풀캘린더 -->
<script src='js/fullcal/main.js'></script>
<script src='js/fullcal/locales-all.js'></script>

<!-- 데이트피커용 j쿼리 -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- 카카오 api-->
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>


<script>
    Kakao.init('8fcf17bb04b908af3af02d2234b9f1dc');
    let data = 'inviteByKakao?roomcode=<%= code %>&title=<%= appoTitle %>';
    function kakaoInvite() {
        Kakao.Link.sendCustom({
            templateId: 70212,
            templateArgs: {
                'path': data
            }
        });
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
            initialDate: "<%=yyyymm%>",
            height: 'auto',
            initialView: 'dayGridMonth',
            selectable: false,
            events: [
                {
                    title  : '<%=CmmUtil.nvl(wDTO.getFrcstOneCn())%>',
                    start  : '<%=CmmUtil.nvl(wDTO.getFrcstOneDt())%>'
                },
                {
                    title  : '<%=CmmUtil.nvl(wDTO.getFrcstTwoCn())%>',
                    start  : '<%=CmmUtil.nvl(wDTO.getFrcstTwoDt())%>'
                },
                {
                    title  : '<%=CmmUtil.nvl(wDTO.getFrcstThreeCn())%>',
                    start  : '<%=CmmUtil.nvl(wDTO.getFrcstThreeDt())%>'
                },
                {
                    title  : '<%=CmmUtil.nvl(wDTO.getFrcstFourCn())%>',
                    start  : '<%=CmmUtil.nvl(wDTO.getFrcstFourDt())%>'
                },
            ],
            eventColor: '#004EA2',
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
        let appoCode = "<%=code%>";
        let obj = {
            "posdays" : sendPosdays,
            "negdays" : sendNegdays,
            "appoCode" : appoCode
        };
        let sendObj = JSON.stringify(obj);

        let url = "appo?code=" + "<%=code%>";
        $.ajax({
            url: "voteDate",
            type: 'get',
            data: {
                "voteInfo" : sendObj
            },
            contentType: "application/json; charset=utf-8",
            dataType: "text",
            success: function(data) {
                if(data == 1) {
                    location.href = url;
                } else {
                    location.href = "/";
                }
            },
            error: function(error) {
            }

        });
    }

</script>

<script>

    <%
                String dl = deadline.replace(".", ",");
                %>
    const countDownTimer = function () {
        var _vDate = new Date(<%=dl%>); // 전달 받은 일자
        console.log(_vDate);
        var _second = 1000;
        var _minute = _second * 60;
        var _hour = _minute * 60;
        var _day = _hour * 24;
        var timer;
        let voteBtn = document.getElementById("voteBtn");
        function showRemaining() {
            var now = new Date();
            var distDt = _vDate - now;
            if (distDt < 0) { //기한 종료되면
                clearInterval(timer);
                $("#load").hide();
                voteBtn.textContent = "투표마감";
                voteBtn.className = "btn btn-outline-secondary col-10";
                voteBtn.disabled = true;
                voteBtn.hidden = false;
                return;
            }
            voteBtn.textContent = "투표하기";
            voteBtn.className = "btn btn-outline-danger col-10";
            voteBtn.disabled = false;
            voteBtn.hidden = false;
            var days = Math.floor(distDt / _day);
            var hours = Math.floor((distDt % _day) / _hour);
            var minutes = Math.floor((distDt % _hour) / _minute);
            var seconds = Math.floor((distDt % _minute) / _second);
            $("#load").hide();
            document.getElementById("d-day-day").textContent = days;
            document.getElementById("d-day-hour").textContent = hours;
            document.getElementById("d-day-min").textContent = minutes;
            document.getElementById("d-day-sec").textContent = seconds;
        }
        timer = setInterval(showRemaining, 1000);
    }
    countDownTimer();


</script>

<script>
    function addEventFromAppo(date) {

        if(confirm("내 캘린더에 " + date + " 일자로 약속 일정을 추가하시겠습니까?")){

            $.ajax({
                url: "addEventFromAppo",
                type: 'get',
                data: {
                    "date": date,
                    "title" : "<%= aDTO.getTitle()%>",
                    "eventId": "<%= aDTO.getAppoCode()%>"
                },
                contentType: "application/json; charset=utf-8",
                dataType: "text",
                success: function(data) {
                    if(data != 1) {
                        alert("해당하는 방이 존재하지않습니다.");
                        location.href = '/';
                    } else {
                        alert(data + "개의 일정을 추가했습니다.");
                    }

                },
                error: function(error) {
                    location.href = '/';
                }

            });
        }
    }

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

    function copyAppoCode() {
        let tempElem = document.createElement('textarea');
        tempElem.value = '<%= aDTO.getAppoCode()%>';
        document.body.appendChild(tempElem);
        tempElem.select();
        document.execCommand("copy");
        document.body.removeChild(tempElem);
        alert("코드 복사 완료");
    }
</script>

<script>
    function chgPw() {
        location.href = "resetPw?code=<%=CmmUtil.nvl(pDTO.getUserId())%>";
    }

    $("#userNameForDelete").on("propertychange change paste input", function() {
        if(document.getElementById("userNameForDelete").value == "<%= pDTO.getUserName()%>") {
            document.getElementById("deleteUserBtn").disabled = false;
        } else {
            document.getElementById("deleteUserBtn").disabled = true;
        }
    });

    function deleteUser() {
        location.href = "deleteUser";
    }
</script>

</body>

</html>