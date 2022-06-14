<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String appoCode = (String) request.getAttribute("roomcode");
    String title = (String) request.getAttribute("title");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Modu Calendar_resister</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="css/styles.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="/">Modu Calendar</a>
    <!-- 여백-->
    <div class="ms-auto"></div>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <div class="row justify-content-center">
                    <div class="col-lg-7">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header"><h3 class="text-center font-weight-light my-4">초대 코드 입력하기</h3></div>
                            <form class="card-body" onsubmit="inputCode(event)">
                                <div class="mb-3">
                                    <label class="form-label">약속 방 제목 : <%= title%></label>
                                    <input type="text" name="code" class="form-control" autocomplete="off" id="inviteCode" required>
                                </div>
                                <div style="text-align: center">
                                    <button type="button" class="btn btn-secondary">취소</button>
                                    <button type="submit" class="btn btn-warning">참가</button>
                                </div>
                            </form>
                            <div class="card-footer text-center py-3">
                                <div class="small"><a href="/">메인페이지로 돌아가기</a></div>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
<script src="js/datatables-simple-demo.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script>
    window.onload = function () {
        let appoCode = '<%= appoCode%>';
        document.getElementById('inviteCode').value = appoCode;
    }
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
</body>
</html>
