<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.EncryptUtil" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.*" %>
<%
	UserInfoDTO pDTO = (UserInfoDTO) request.getAttribute("UserInfoDTO");



	if (pDTO == null) {
		pDTO = new UserInfoDTO();
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
	List<PlayerInfoDTO> plaList = rDTO.getPrjPlayer();
	if(plaList == null) {
		plaList = new ArrayList<>();
	}

	String userGrant = "";
	for(PlayerInfoDTO plaDTO : plaList) {
		String playerId = plaDTO.getUserId();
		String playerGrant = plaDTO.getUserGrant();

		if(pDTO.getUserId().equals(playerId)) {
			userGrant = playerGrant;
		}
	}

	List<ChatMessageDTO> chatList = (List<ChatMessageDTO>) request.getAttribute("chatList");
	if(chatList == null) {
		chatList = new ArrayList<>();
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
	<title>Modu Calendar_prj</title>
	<link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
	<link href="css/styles.css" rel="stylesheet" />
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link href='css/fullcal/main.css' rel='stylesheet' />
	<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>

	<style>


		@media (min-width: 1200px) {
			#scroll {
				position:absolute;
				right:0;
				top:10px;
			}
		}
	</style>
</head>

<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
	<!-- Navbar Brand-->
	<a class="navbar-brand ps-3" href="/">Modu Calendar</a>
	<!-- Sidebar Toggle-->
	<button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
	<!-- ??????-->
	<div class="ms-auto"></div>
	<!-- Navbar-->
	<ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
			<ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
				<li><a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#userInfo">?????????</a></li>
				<li><hr class="dropdown-divider" /></li>
				<li><a class="dropdown-item" href="logout">????????????</a></li>
			</ul>
		</li>
	</ul>
</nav>
<div id="layoutSidenav">
	<div id="layoutSidenav_nav">
		<nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
			<div class="sb-sidenav-menu">
				<div class="nav">
					<div class="sb-sidenav-menu-heading">??? ??????</div>
					<a class="nav-link" data-bs-toggle="modal" data-bs-target="#userInfo">
						<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
						<%= pDTO.getUserName()%>
					</a>
					<div class="sb-sidenav-menu-heading">?????? ??????</div>
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
								<button class="nav-link btn btn-outline-danger" onclick="deleteAppo('<%= title%>', '<%= code%>')">??? ?????????</button>
							</nav>
						</div>
					<%
							i++;
						}
					%>
					<div class="mt-2"></div>
					<button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#addAppo">
						?????? ????????????
					</button>
					<div class="mt-2"></div>
					<button type="button" class="btn btn-dark btn-outline-warning" data-bs-toggle="modal" data-bs-target="#inputCode">???????????? ????????????</button>
				</div>
			</div>
		</nav>
	</div>
	<div id="layoutSidenav_content">
		<main>
			<div class="container-fluid px-4">
				<div class="mt-4"></div>
				<div class="row">
					<div class="col-xl-8">
						<div class="card mb-4">
							<div class="card-header">
								<i class="fas fa-table me-1"></i>
								????????????(<%= rDTO.getPrjTitle()%>) ????????????
								<button class="btn btn-outline-success" onclick="updateMile()">??????</button>
								<button class="btn btn-outline-danger" onclick="deleteMile()">??????</button>

							</div>
							<div class="card-body" style="overflow:scroll; height: 400px;">
								<div class="row">
									<div class="row mb-3" style="text-align: center">
										<div class="col-2">
											???????????? ?????????
										</div>
										<div class="col-3">
											<input class="form-control" id="prjStartDate" type="text" value="<%=rDTO.getPrjStartDate()%>" disabled>
										</div>
										<div class="col-2">
											???????????? ?????????
										</div>
										<div class="col-3">
											<input class="form-control" id="prjEndDate" type="text" value="<%=rDTO.getPrjEndDate()%>" disabled>
										</div>
									</div>
									<div class="row mb-1">
										<div class="col-6">
											<div class="row justify-content-around">
												<div class="col-3 btn btn-primary" style="text-align: center">
													?????????
												</div>
												<div class="col-3 btn btn-primary" style="text-align: center">
													?????????
												</div>
												<div class="col-3 btn btn-primary" style="text-align: center">
													?????????
												</div>
											</div>
										</div>
										<div class="col-6">
											<div class="row" id="m_period"></div>
										</div>
									</div>
									<%
										int j = 0;
										int startMonth = Integer.parseInt(rDTO.getPrjStartDate().split("-")[1]);
										int endMonth = Integer.parseInt(rDTO.getPrjEndDate().split("-")[1]);
										for(MileDTO mDTO : mList) {
											String itemValue = mDTO.getItemValue();
											String itemStartDate = mDTO.getItemStartDate();
											int itemStartMonth = Integer.parseInt(itemStartDate.split("-")[1]);
											String itemEndDate = mDTO.getItemEndDate();
											int itemEndMonth = Integer.parseInt(itemEndDate.split("-")[1]);
									%>
									<div class="row mb-1">
										<div class="col-6" id="MileItem">
											<div class="row">
												<div class="col-4">
													<input class="form-control" style="text-align: center" type="text" value="<%=itemValue%>" disabled />
												</div>
												<div class="col-4">
													<input class="form-control" style="text-align: center" type="text" value="<%=itemStartDate%>" disabled />
												</div>
												<div class="col-4">
													<input class="form-control" style="text-align: center" type="text" value="<%=itemEndDate%>" disabled />
												</div>
											</div>
										</div>
										<div class="col-6">
											<div class="row" id="MileStone_<%= j%>" style="height: 100%">
												<%
													for(int k = startMonth; k <= endMonth; k++) {
														if(k >= itemStartMonth && k <= itemEndMonth) {
												%>
												<button class="btn btn-warning col-1" disabled></button>
												<%
												} else {
												%>
												<button class="col-1 btn btn-outline-dark disabled"></button>
												<%
														}
													}
												%>
											</div>
										</div>
									</div>
									<%
											j++;
										}
									%>
								</div>


							</div>
						</div>

						<div class="card mb-4">
							<div class="card-header">
								<i class="fas fa-chart-bar me-1"></i>
								???????????? ??????
								<%
									if(userGrant.equals("master")) {
								%>
								<button class="btn btn-warning btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deletePrj">???????????? ??????</button>
								<%
								} else {
								%>
								<button class="btn btn-warning btn-outline-danger" onclick="getoutPrj()">???????????? ?????????</button>
								<%
									}
								%>
							</div>
							<div class="card-body" style="overflow:scroll; height: 300px;">
								<div class="container-fluid">
									<div class="row">
										<div class="col-4">???????????? ??? :</div>
										<div class="col-4"><%=rDTO.getPrjTitle()%></div>
									</div>
									<div class="row">
										<div class="col-4">???????????? ????????? :</div>
										<div class="col-4"><%=rDTO.getPrjRegDt()%></div>
									</div>
									<div class="row">
										<div class="col-4">???????????? ?????? :</div>
										<button class="btn btn-info col-4" onclick="copyPrjCode()">?????????????????? ????????????</button>
									</div>

									<div class="row mt-3"><h5>????????????</h5></div>
									<hr/>
									<div class="row">
										<div class="col-2">??????</div>
										<div class="col-2">??????</div>
										<div class="col-5">??????</div>
										<div class="col-3">??????</div>
									</div>
									<hr/>
									<%
										for(PlayerInfoDTO plaDTO : plaList) {
											String playerName = plaDTO.getUserName();
											String playerId = plaDTO.getUserId();
											String playerGrant = plaDTO.getUserGrant();
											String playerRole = plaDTO.getUserRole();

									%>
									<div class="row">
										<div class="col-2"><%= playerName%></div>
										<div class="col-2"><%= playerGrant%></div>
										<div class="col-5"><%= playerRole%></div>
										<%
											if(userGrant.equals("master")) {

										%>
										<button class="col-1 btn btn-primary" data-bs-toggle="modal" data-bs-target="#playerInfo" onclick="showPlayerInfo('<%= playerId%>', '<%= playerName%>', '<%= playerGrant%>', '<%= playerRole%>')">??????</button>
										<div class="col-1"></div>

										<%
											if(!pDTO.getUserId().equals(playerId)) {

										%>
										<button class="col-1 btn btn-danger" onclick="deletePlayer('<%=playerId%>')">??????</button>
										<%
												}
											}
										%>
									</div>
									<hr/>
									<%
										}
									%>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-3" id="scroll">
						<div class="card mb-4">
							<div class="card-header">
								<i class="fas fa-chart-bar me-1"></i>
								??? ??????
								<button type="button" class="btn btn-primary" id="inChat" onclick="onOpen()">????????????</button>
								<button type="button" class="btn btn-secondary" id="outChat" onclick="onClose()">????????????</button>
							</div>
							<div class="card-body" id="chatDiv" style="overflow:scroll; height: 500px;">
								<div id="msgArea">
									<%
										for(ChatMessageDTO cDTO : chatList) {
											if(cDTO.getUserId().equals(pDTO.getUserId())) {
									%>
									<div><%=cDTO.getSendTime()%></div>
									<div class="input-group mb-3">
										<div type="text" class="form-control" aria-describedby="basic-addon3"><%=cDTO.getMessage()%></div>
										<div class="input-group-text"><%=cDTO.getUserName()%></div>
									</div>
										<%
											} else {
										%>
									<div><%=cDTO.getSendTime()%></div>
									<div class="input-group mb-3">
										<div class="input-group-text"><%=cDTO.getUserName()%></div>
										<div type="text" class="form-control" aria-describedby="basic-addon3"><%=cDTO.getMessage()%></div>
									</div>
									<%
											}
										}
									%>
								</div>
							</div>
							<div class="card-footer">
								<div class="input-group">
									<input type="text" id="msg" class="form-control" aria-label="Recipient's username" aria-describedby="button-addon2">
									<button class="btn btn-outline-secondary input-group-text" type="button" id="button-send" disabled>??????</button>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>
</div>

<!-- UserInfo Modal -->
<div class="modal fade" id="userInfo" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabelUserInfo" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabelUserInfo">??? ??????</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="userName" class="form-label">??????</label>
					<p name="userName" class="form-control" id="userName"><%= pDTO.getUserName()%></p>
				</div>
				<div class="mb-3">
					<label for="userRegDt" class="form-label">?????????</label>
					<p name="userRegDt" class="form-control" id="userRegDt"><%= pDTO.getRegDt()%></p>
				</div>
				<div class="mb-3">
					<label for="userPw" class="form-label">????????????</label> <button class="btn btn-sm btn-outline-info" onclick="chgPw()">???????????? ????????????</button>
					<p name="userPw" class="form-control" id="userPw">**********</p>
				</div>
				<div class="mb-3 row justify-content-end">
					<button class="btn btn-sm btn-warning btn-outline-danger col-6" data-bs-toggle="modal" data-bs-target="#deleteUser">??????????????????</button>
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
				<h5 class="modal-title" id="staticBackdropLabel">??? ??????</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="exampleFormControlInput1" class="form-label">??????</label>
					<input type="text" name="title" class="form-control" autocomplete="off" id="exampleFormControlInput1" placeholder="~~??? ?????????" required>

				</div>
				<p>????????? ???, ??? <input type="text" name="month" autocomplete="off" id="monthpicker" required></p>
				<p>?????? ??????</p>
				<select name="deadline" class="form-select form-select-sm" aria-label=".form-select-sm example">
					<option value="1">????????? 1???</option>
					<option value="3">????????? 3???</option>
					<option value="5">????????? 5???</option>
					<option value="7">????????? 7???</option>
				</select>
				<div class="mb-3"></div>
				<p>?????? ??????</p>
				<select name="region" class="form-select form-select-sm" aria-label=".form-select-sm example">
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="????????????">????????????</option>
					<option value="????????????">????????????</option>
					<option value="????????????">????????????</option>
					<option value="????????????">????????????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
					<option value="??????">??????</option>
				</select>
				<input type="text" class="form-control" name="userName" value="<%=pDTO.getUserName()%>" hidden />

			</div>


			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">??????</button>
				<button type="submit" class="btn btn-warning">??????</button>
			</div>
		</form>
	</div>
</div>

<!-- inputCode Modal -->
<div class="modal fade" id="inputCode" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content" onsubmit="inputCode(event)">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel2">?????? ?????? ????????????</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="exampleFormControlInput1" class="form-label">?????? ??????</label>
					<input type="text" name="code" class="form-control" autocomplete="off" id="inviteCode" required>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">??????</button>
				<button type="submit" class="btn btn-outline-dark btn-warning">??????</button>
			</div>
		</form>
	</div>
</div>

<!-- deletePrj Modal -->
<div class="modal fade" id="deletePrj" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content" onsubmit="deletePrj(event)">
			<div class="modal-header">
				<h5 class="modal-title" >???????????? ????????????</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="alert alert-danger" role="alert">
					<p>??????! ???????????? ????????? ??? ????????????.</p>
					<p>?????? ?????????????????????????</p>
				</div>
				<label class="form-label">????????? ?????????????????? ????????? ???????????? ?????? ??????????????????.</label>
				<div class="form-floating mb-3">
					<input type="text" name="code" class="form-control" autocomplete="off" placeholder="<%= rDTO.getPrjTitle()%>" id="prjTitleForDelete" required>
					<label for="prjTitleForDelete"><%= rDTO.getPrjTitle()%></label>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">??????</button>
				<button type="submit" class="btn btn-danger" id="deletePrjButton" disabled>??????</button>
			</div>
		</form>
	</div>
</div>

<!-- Player Modal -->
<div class="modal fade" id="playerInfo" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">?????? ?????? ????????????</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="mb-3">
					<label for="pName" class="form-label">??????</label>
					<input name="userName" class="form-control" id="pName" autocomplete="off" disabled />
				</div>
				<div class="mb-3" id="setGrant">
					<label for="pGrant" class="form-label">??????</label>
					<select name="userGrant" class="form-select" aria-label="Default select example" id="pGrant">
						<option value="master">Master(???????????? ?????? ??? ???????????? ?????? ??????)</option>
						<option value="senior">Senior(???????????? ????????????)</option>
						<option value="junior">Junior(???????????? ???????????????)</option>
					</select>
				</div>
				<div class="mb-3">
					<label for="pRole" class="form-label">??????</label>
					<input name="userRole" type="text" class="form-control" id="pRole" autocomplete="off" />
				</div>
				<input name="userId" type="text" id="pId" disabled hidden />
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">??????</button>
				<button type="button" class="btn btn-success" onclick="chgPlayerInfo()">??????</button>
			</div>
		</form>
	</div>
</div>

<!-- deleteUser Modal -->
<div class="modal fade" id="deleteUser" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" onsubmit="deletePrj(event)">
			<div class="modal-header">
				<h5 class="modal-title" >?????? ????????????</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="alert alert-danger" role="alert">
					<p>??????! ???????????? ?????? ????????? ??? ????????????.</p>
					<p>?????? ?????????????????????????</p>
				</div>
				<label class="form-label">????????? ?????????????????? ????????? ???????????? ??????????????????.</label>
				<div class="form-floating mb-3">
					<input type="text" name="code" class="form-control" autocomplete="off" placeholder="<%= pDTO.getUserName()%>" id="userNameForDelete" required>
					<label for="userNameForDelete"><%= pDTO.getUserName()%></label>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">??????</button>
				<button type="button" class="btn btn-danger" onclick="deleteUser()" id="deleteUserBtn" disabled>??????</button>
			</div>
		</div>
	</div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<!--<script src="assets/demo/chart-area-demo.js"></script>
<script src="assets/demo/chart-bar-demo.js"></script>-->
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
<script src="js/datatables-simple-demo.js"></script>


<!-- ?????????????????? j?????? -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- SockJS -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<!-- monthpicker -->
<script src="js/jquery.mtz.monthpicker.js"></script>

<script>

	        //????????? ??? ???????????? ??????
			const startDate = document.getElementById("prjStartDate").value.split('-');
	        let startY = startDate[0];


	        let startM = Number(startDate[1]);


			const endDate = document.getElementById("prjEndDate").value.split('-');

	        let endY = endDate[0];


	        let endM = Number(endDate[1]);

	        //?????? = ????????? ????????? ?????? - ?????????, ????????? ????????? 12 - (????????? - ??????)
	        let periodM = (Number(startY) - Number(endY) == 0) ? Number(endM) - Number(startM) : 12 - (Number(startM) - Number(endM));

	        //??? ?????? ?????? div ??????, ??????????????? ?????? ?????????
	        //periodM?????? ?????? ???, ?????? 1?????? ??????
	        for (let i = 0; i <= periodM; i++) {
	            let mileM = new Date(Number(startY), Number(startM + i), 1).getMonth();
				if(mileM == 0) {
					mileM = 12;
				}
	            $("#m_period").append($("<div class='col-1 btn btn-primary'>").text(mileM));
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
					alert("???????????? ?????? ???????????? ????????????.");
				}else if(data == 2) {
					alert("?????? ?????? ?????? ??????????????????.");
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
		monthNames: ['1???', '2???', '3???', '4???', '5???', '6???', '7???', '8???', '9???', '10???', '11???', '12???'],
		openOnFocus: true,
		disableMonths: []
	};

	$("#monthpicker").monthpicker(options);


</script>


<script>
	function deleteAppo(title, code) {

		if(confirm("?????? ??????????????????????")){

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
						alert("???????????? ?????? ????????????????????????.");
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
	function updateMile() {
		if("<%= userGrant%>" == "junior") {
			alert("master ?????? senior??? ???????????? ????????? ???????????????.");
		} else {
			location.href="mile?prjCode=<%=rDTO.getPrjCode()%>";
		}
	}
	function deleteMile() {
		if("<%= userGrant%>" == "junior") {
			alert("master ?????? senior??? ???????????? ????????? ???????????????.");
		} else {
			if(confirm("????????? ??????????????? ????????? ???????????????????")) {
				let prjCode = "<%= rDTO.getPrjCode()%>";
				let prjTitle = "<%= rDTO.getPrjTitle()%>";
				let prjStartDate = "<%= rDTO.getPrjStartDate()%>";
				let prjEndDate = "<%= rDTO.getPrjEndDate()%>";
				$.ajax({
					url: "deleteMile",
					type: 'get',
					data: {
						prjCode : prjCode,
						prjTitle : prjTitle,
						prjStartDate : prjStartDate,
						prjEndDate : prjEndDate
					},
					contentType: "application/json; charset=utf-8",
					dataType: "text",
					success: function(data) {
						if(data == 1) {
							location.reload();
						} else {
							location.href = "/";
						}

					},
					error: function(error) {
					}

				});
			}
		}

	}

	function showPlayerInfo(pId, pName, pGrant, pRole) {
		console.log(pId);
		console.log(pName);
		console.log(pGrant);
		console.log(pRole);
		if(pGrant == "master") {
			document.getElementById("setGrant").style.display = 'none';
		} else if(pGrant == "senior") {
			document.getElementById("setGrant").style.display = '';
			document.getElementById("pGrant").value = "senior";
		} else {
			document.getElementById("setGrant").style.display = '';
			document.getElementById("pGrant").value = "junior";
		}

		document.getElementById("pName").value = pName;
		document.getElementById("pRole").value = pRole;
		document.getElementById("pId").value = pId;
	}

	function chgPlayerInfo() {
		let sPrjCode = "<%=rDTO.getPrjCode()%>";
		let sUserId = document.getElementById("pId").value;
		let sUserName = document.getElementById("pName").value;
		let sUserRole = document.getElementById("pRole").value;
		let sUserGrant = document.getElementById("pGrant").value;

		$.ajax({
			url: "chgPlayerInfo",
			type: 'get',
			data: {
				prjCode : sPrjCode,
				userId : sUserId,
				userName : sUserName,
				userGrant : sUserGrant,
				userRole : sUserRole
			},
			contentType: "application/json; charset=utf-8",
			dataType: "text",
			success: function(data) {
				if(data == 1) {
					location.reload();
				} else {
					location.href = "/";
				}

			},
			error: function(error) {
			}

		});
	}

	$("#prjTitleForDelete").on("propertychange change paste input", function() {
		if(document.getElementById("prjTitleForDelete").value == "<%= rDTO.getPrjTitle()%>") {
			document.getElementById("deletePrjButton").disabled = false;
		} else {
			document.getElementById("deletePrjButton").disabled = true;
		}
	});

	function deletePrj(event) {
		event.preventDefault;
		let sPrjCode = "<%=rDTO.getPrjCode()%>";
		let sPrjTitle = "<%= rDTO.getPrjTitle()%>";
		$.ajax({
			url: "deletePrj",
			type: 'get',
			data: {
				"prjCode": sPrjCode,
				"prjTitle": sPrjTitle
			},
			contentType: "application/json; charset=utf-8",
			dataType: "text",
			success: function(data) {
				if(data != 1) {
					alert("??????");
				}
				location.href = '/';
			},
			error: function(error) {
				location.href = '/';
			}

		});
	}

	function deletePlayer(userId) {
		if(confirm("?????? ????????? ?????????????????? ?????????????????????????")) {
			let sPrjCode = "<%=rDTO.getPrjCode()%>";

			$.ajax({
				url: "deletePlayer",
				type: 'get',
				data: {
					"userId": userId,
					"prjCode": sPrjCode
				},
				contentType: "application/json; charset=utf-8",
				dataType: "text",
				success: function(data) {
					if(data != 1) {
						alert("??????");
					}
					location.reload();
				},
				error: function(error) {
					location.href = '/';
				}

			});
		}
	}

	function copyPrjCode() {
		let tempElem = document.createElement('textarea');
		tempElem.value = '<%= rDTO.getPrjCode()%>';
		document.body.appendChild(tempElem);
		tempElem.select();
		document.execCommand("copy");
		document.body.removeChild(tempElem);
		alert("?????? ?????? ??????");
	}

	function getoutPrj() {
		if(confirm("??????????????? ??????????????????????")) {
			let sPrjCode = "<%=rDTO.getPrjCode()%>";
			let prjTitle = "<%=rDTO.getPrjTitle()%>";

			$.ajax({
				url: "getoutPlayer",
				type: 'get',
				data: {
					"prjCode": sPrjCode,
					"prjTitle": prjTitle
				},
				contentType: "application/json; charset=utf-8",
				dataType: "text",
				success: function(data) {
					if(data != 1) {
						alert("??????");
					}
					location.href = '/';
				},
				error: function(error) {
					location.href = '/';
				}

			});
		}
	}

	window.onload = function() {
		let userExist = "<%= userGrant%>";
		if(userExist == "") {
			alert("?????????????????? ?????????????????????.");
			let sPrjCode = "<%=rDTO.getPrjCode()%>";
			let prjTitle = "<%=rDTO.getPrjTitle()%>";

			$.ajax({
				url: "getoutPlayer",
				type: 'get',
				data: {
					"prjCode": sPrjCode,
					"prjTitle": prjTitle
				},
				contentType: "application/json; charset=utf-8",
				dataType: "text",
				success: function(data) {
					if(data != 1) {
						alert("??????");
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
	window.onload = function () {
		document.getElementById("outChat").style.display = 'none';
		document.getElementById("msg").disabled = 'true';
		let chatDiv = document.getElementById("chatDiv");
		chatDiv.scrollTop = chatDiv.scrollHeight;
	}

		const userId = "<%= pDTO.getUserId()%>";
		const username = "<%= pDTO.getUserName()%>";
		const prjCode = "<%= rDTO.getPrjCode()%>";

		$("#button-send").on("click", (e) => {
			send();
		});

		let sockJs;
		let stomp;
		//??????????????? ????????? ???
		function onClose() {
			document.getElementById("inChat").style.display = '';
			document.getElementById("outChat").style.display = 'none';
			$("#msg").attr("disabled", true);
			$("#button-send").attr("disabled", true);
			stomp.send('/pub/chat/exit', {}, JSON.stringify({"prjCode": prjCode, "userId": userId, "userName": username}));
			stomp.disconnect();
		}

		// ???????????? ???????????? ???
		function onOpen() {
			document.getElementById("inChat").style.display = 'none';
			document.getElementById("outChat").style.display = '';
			$("#msg").attr("disabled", false);
			$("#button-send").attr("disabled", false);

			$.ajax({
				url: "getChatLogInRedis",
				type: 'get',
				data: {
					"prjCode": prjCode
				},
				contentType: "application/json; charset=utf-8",
				dataType: "JSON",
				success: function(data) {
					for(let i = 0; i < data.length; i++) {
						let chatOne = data[i];
						let chatUserId = chatOne.userId;
						let writer = chatOne.userName;
						let message = chatOne.message;
						let time = chatOne.sendTime;
						if (chatUserId === userId) {
							$("#msgArea").append($(
									`
						<div>\${time}</div>
						<div class="input-group mb-3">
							<div type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3">\${message}</div>
							<div class="input-group-text" id="basic-addon3">\${writer}</div>
						</div>
						`
							));
						} else {
							$("#msgArea").append($(
									`
						<div>\${time}</div>
						<div class="input-group mb-3">
							<div class="input-group-text" id="basic-addon3">\${writer}</div>
							<div type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3">\${message}</div>
						</div>
						`
							));
						}
					}
					let chatDiv = document.getElementById("chatDiv");
					chatDiv.scrollTop = chatDiv.scrollHeight;
				},
				error: function(error) {

				}
			});

			sockJs = new SockJS("/stomp/chat");

			//1. SockJS??? ????????? ???????????? stomp??? ?????????
			stomp = Stomp.over(sockJs);

			//2. connection??? ???????????? ??????
			stomp.connect({}, function () {
				console.log("STOMP Connection")
				stomp.send('/pub/chat/enter', {}, JSON.stringify({"prjCode": prjCode, "userId": userId, "userName": username}));
				//4. subscribe(path, callback)?????? ???????????? ?????? ??? ??????
				stomp.subscribe("/sub/chat/room/" + prjCode, function (chat) {
					let content = JSON.parse(chat.body);
					let chatUserId = content.userId;
					let writer = content.userName;
					let message = content.message;
					let time = content.sendTime;
					if (chatUserId === userId) {
						$("#msgArea").append($(
								`
						<div>\${time}</div>
						<div class="input-group mb-3">
							<div type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3">\${message}</div>
							<div class="input-group-text" id="basic-addon3">\${writer}</div>
						</div>
						`
						));
					} else {
						$("#msgArea").append($(
								`
						<div>\${time}</div>
						<div class="input-group mb-3">
							<div class="input-group-text" id="basic-addon3">\${writer}</div>
							<div type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3">\${message}</div>
						</div>
						`
						));
					}
				});
			});
		}

	function send(){
		if(document.getElementById("msg").value != '') {
			let msg = document.getElementById("msg");
			//3. send(path, header, message)??? ???????????? ?????? ??? ??????
			stomp.send('/pub/chat/message', {}, JSON.stringify({"prjCode": prjCode, "userId": userId, "userName": username, "message": msg.value}));

			msg.value = '';
		}
	}
	$(window).scroll(function(){
		let position = $(document).scrollTop();
		$("#scroll").css('top',  position + 10);
	});

</script>

</body>

</html>