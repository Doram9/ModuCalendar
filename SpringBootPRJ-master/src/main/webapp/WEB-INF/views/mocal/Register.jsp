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
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="index.html">Start Bootstrap</a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
    <!-- 여백-->
    <div class="ms-auto"></div>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <a class="nav-link" href="index.html">
                        로그인 후 이용해주세요
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
                <div class="row justify-content-center">
                    <div class="col-lg-7">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header"><h3 class="text-center font-weight-light my-4">회원가입</h3></div>
                            <div class="card-body">
                                <form onsubmit="regUser(event)" name="registerForm" action="doRegister" method="post">
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="inputName" name="inputName" type="text" required/>
                                        <label for="inputEmail">이름</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="inputEmail" name="inputEmail" type="email" required/>
                                        <label for="inputEmail">이메일</label>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3">
                                                <input class="form-control" id="inputId" name="inputId" type="text" required/>
                                                <label for="inputEmail">아이디</label>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="d-grid"><button type="button" class="btn btn-primary btn-block" onclick="checkId()">아이디 중복 확인하기 </button></div>
                                        </div>
                                    </div>

                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <input class="form-control" id="inputPassword" name="inputPassword" type="password" required/>
                                                <label for="inputPassword">비밀번호</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <input class="form-control" id="inputPasswordConfirm" type="password" required/>
                                                <label for="inputPasswordConfirm">비밀번호 확인</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="mt-4 mb-0">
                                        <div class="d-grid"><button type="submit" class="btn btn-primary btn-block">회원가입하기</button></div>
                                    </div>
                                </form>
                            </div>
                            <div class="card-footer text-center py-3">
                                <div class="small"><a href="login">아이디가 있으신가요? 로그인 하러가기</a></div>
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

    //0 = 체크 안한상태, 1 = 중복검사 통과, 2 = 중복검사 실패
    let idCheck = 0;


    //id 중복검사
    function checkId() {
        let inputId = document.getElementById("inputId").value;

        $.ajax({
            url: "checkId",
            type: 'get',
            data: {
                checkId : inputId
            },
            dataType: "text",
            contentType: "application/json; charset=utf-8",

            success: function(result) {
                if(result == 1) {
                    idCheck = 1;
                    alert("해당 아이디를 사용하실 수 있습니다.");
                } else {
                    idCheck = 2;
                    alert("해당 아이디가 이미 존재합니다. 다른 아이디를 입력해주세요.");
                }
            },
            error: function(error) {
            }
        });
    }

    //id 값 변화시 다시 중복확인하게끔 false로 변경
    $('#inputId').keyup( function(){
        idCheck = 0;

    });

    $('#inputId').keyup( function(){
        idCheck = 0;

    });

    //회원가입 함수
    function regUser(event) {

        let pw_1 = document.getElementById("inputPassword").value;
        let pw_2 = document.getElementById("inputPasswordConfirm").value;

        if(pw_1.length < 9) {
            event.preventDefault();
            alert("비밀번호가 너무 짧습니다. 9글자 이상 작성해주세요.");
        }
        if(pw_1 != pw_2) {
            event.preventDefault();
            alert("비밀번호가 일치하지 않습니다.");
        }

        if(idCheck == 0) {
            event.preventDefault();
            alert("아이디 중복확인을 진행해주세요.");
        } else if (idCheck == 2){
            event.preventDefault();
            alert("해당 아이디가 이미 존재합니다. 다른 아이디를 입력해주세요.");
        }

    }

</script>
</body>
</html>
