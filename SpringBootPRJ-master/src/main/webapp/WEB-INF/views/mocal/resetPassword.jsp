<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String resetCode = (String) request.getAttribute("resetCode");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Modu Calendar_resetPassword</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="css/styles.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="/">Start Bootstrap</a>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <div class="row justify-content-center">
                    <div class="col-lg-5">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header"><h3 class="text-center font-weight-light my-4">비밀번호 재설정</h3></div>
                            <div class="card-body">
                                <form>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="inputPassword" type="password" placeholder="Password" required/>
                                        <label for="inputPassword">변경할 비밀번호</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="inputPasswordConfirm" type="password" placeholder="Password" required/>
                                        <label for="inputPasswordConfirm">변경할 비밀번호확인</label>
                                    </div>
                                    <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                        <button type="button" class="btn btn-primary" onclick="doResetPw()">비밀번호 재설정</button>
                                    </div>
                                </form>
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
    function doResetPw() {
        let pw1 = document.getElementById('inputPassword').value;
        let pw2 = document.getElementById('inputPasswordConfirm').value;
        if(pw1 != pw2) {
            alert("비밀번호와 비밀번호확인이 일치하지않습니다.");
            return 0;
        } else if(pw1 == "" || pw1 == null) {
            alert("비밀번호가 입력되지 않았습니다.");
            return 0;
        }
        let resetCode = "<%=resetCode%>";
        $.ajax({
            url: "doResetPw",
            type: 'get',
            data: {
                resetCode: resetCode,
                resetPw: pw1
            },
            dataType: "text",
            contentType: "application/json; charset=utf-8",

            success: function(result) {
                if(result == 1) {
                    alert("비밀번호 변경이 완료되었습니다. 다시 로그인해주세요");
                    location.href = "/login";
                } else {
                    alert("에러!");
                    location.href = "/";
                }

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
