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
    <title>Tables - SB Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body class="sb-nav">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="/">Modu Calendar</a>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <div class="row justify-content-center">
                    <div class="col-lg-5">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header"><h3 class="text-center font-weight-light my-4">비밀번호 찾기</h3></div>
                            <div class="card-body">
                                <div class="small mb-3 text-muted">가입 시 작성한 이메일을 기입해주세요.</div>
                                <form action="doFindPw" id="findPwForm" method="post">
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="inputEmail" name="email" type="email" placeholder="name@example.com" required/>
                                        <label for="inputEmail">이메일</label>
                                    </div>
                                    <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                        <a class="small" href="login">로그인 하러가기</a>
                                        <button type="button" class="btn btn-primary" onclick="checkUser()">메일 발송하기</button>
                                    </div>
                                </form>
                            </div>
                            <div class="card-footer text-center py-3">
                                <div class="small"><a href="register">아직 계정이 없으신가요? 회원가입 하러가기</a></div>
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

<!-- 데이트피커용 j쿼리 -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>

    function checkUser() {
        let inputEmail = document.getElementById("inputEmail").value;
        let form = document.getElementById("findPwForm");
        $.ajax({
            url: "checkEmail",
            type: 'get',
            data: {
                checkEmail : inputEmail
            },
            dataType: "text",
            contentType: "application/json; charset=utf-8",

            success: function(result) {
                if (result == 1) {
                    alert("해당하는 이메일로 가입된 계정이 없습니다.");
                } else {
                    form.submit();
                }
            },
            error: function(error) {
            }
        });
    }
</script>
</body>
</html>
