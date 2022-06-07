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
                            <div class="card-header"><h3 class="text-center font-weight-light my-4">로그인</h3></div>
                            <div class="card-body">
                                <form>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="inputId" type="email" placeholder="name@example.com" required/>
                                        <label for="inputId">아이디</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="inputPassword" type="password" placeholder="Password" required/>
                                        <label for="inputPassword">비밀번호</label>
                                    </div>
                                    <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                        <a class="small" href="findPw">비밀번호가 기억나지 않나요?</a>
                                        <a class="btn btn-primary" onclick="doLogin()">로그인</a>
                                    </div>
                                </form>
                                <div class="mt-2" id="capcha">
                                    <div id="mes"></div>
                                    <canvas id="captchaCanvas" style="background-color: #dee2e6;"></canvas>
                                </div>


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

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script>
    let capchaTF = false;
    function doLogin() {
        if(capchaTF) {
            let reqId = document.getElementById('inputId').value;
            let reqPw = document.getElementById('inputPassword').value;

            console.log(reqId);
            console.log(reqPw);

            $.ajax({
                url: "dologin",
                type: 'get',
                data: {
                    reqId: reqId,
                    reqPw: reqPw
                },
                dataType: "text",
                contentType: "application/json; charset=utf-8",

                success: function(result) {
                    if(result == "success") {
                        location.href = "/";
                    } else if(result == "fail") {
                        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
                        canvasReset();
                        shuffle_color(color);
                        shuffle(colorName, colorClass);
                        mes.className = colorClass[0];
                        mes.innerText = colorName[0] + "을 클릭하십시오";
                        fill();
                        capcha.style.display = 'block';
                    }
                },
                error: function(request,status,error) {
                    //alert("아이디 또는 비밀번호가 일치하지 않습니다.");
                    //location.reload();
                }

            });
        } else {
            alert("캡챠인증을 통과해주세요.");
        }
    }



    const capcha = document.getElementById("capcha");
    const canvas = document.querySelector("#captchaCanvas");
    const ctx = canvas.getContext("2d");
    const mes = document.querySelector("#mes");
    const CANVASSIZE = 250;
    canvas.width =  CANVASSIZE;
    canvas.height = CANVASSIZE;

    //도형 사이즈 : canvas 사이즈 / 10
    const figsize = CANVASSIZE / 10;


    let color = ['#2c2c2c', '#FF3B30', '#0579FF'];
    let colorName = ["빨강색", "검정색", "파랑색"];
    let colorClass = ["alert alert-danger", "alert alert-dark", "alert alert-primary"];
    function shuffle(array_1, array_2) {
        for (let i = array_1.length - 1; i > 0; i--) {
            let j = Math.floor(Math.random() * (i + 1));
            [array_1[i], array_1[j]] = [array_1[j], array_1[i]];
            [array_2[i], array_2[j]] = [array_2[j], array_2[i]];
        }
    }
    function shuffle_color(array_1) {
        for (let i = array_1.length - 1; i > 0; i--) {
            let j = Math.floor(Math.random() * (i + 1));
            [array_1[i], array_1[j]] = [array_1[j], array_1[i]];
        }
    }


    function canvasReset() {
        ctx.clearRect(0, 0, CANVASSIZE, CANVASSIZE);
    }

    function fill() {
        for(let i = 0; i < color.length; i++) {
            let x = Math.round(Math.random() * (figsize * 7)) + figsize;
            let y;
            if(i==0) {
                y = Math.round(Math.random() * figsize) + figsize;
            } else if(i==1) {
                y = Math.round(Math.random() * figsize) + figsize * 4;
            } else {
                y = Math.round(Math.random() * figsize) + figsize * 7;
            }

            ctx.fillStyle = color[i];
            ctx.fillRect(x,y, figsize, figsize);
        }
    }
    shuffle(colorName, colorClass);
    mes.className = colorClass[0];
    mes.innerText = colorName[0] + "을 클릭하십시오";
    shuffle_color(color);
    fill();


    function handleFig(event) {
        const x = event.offsetX;
        const y = event.offsetY;
        const clickedColor = ctx.getImageData(x, y, 1, 1);
        let test = clickedColor.data[0];
        if(colorName[0] == "빨강색" && test == 255) {
            capchaTF = true;
            capcha.style.display = 'none';
        } else if(colorName[0] == "파랑색" && test == 5) {
            capchaTF = true;
            capcha.style.display = 'none';
        } else if(colorName[0] == "검정색" && test == 44) {
            capchaTF = true;
            capcha.style.display = 'none';
        } else {
            canvasReset();
            shuffle_color(color);
            shuffle(colorName, colorClass);
            mes.className = colorClass[0];
            mes.innerText = colorName[0] + "을 클릭하십시오";
            fill();

        }
    }
    canvas.addEventListener("click", handleFig);
</script>
</body>
</html>
