<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function (){
			$(window).keydown(function (e) {
				if (e.keycode == 13){
					$('#loginBtn').click();
				}
			})


			$('#loginBtn').click(function (e) {
				e.preventDefault();
				//获取表单数据
				var loginAct = $.trim($('#loginAct').val());
				console.log(loginAct);
				var loginPwd = $.trim($('#loginPwd').val());
				console.log(loginAct);
				var isRemPwd = $('#isRemPwd').prop('checked');

				//发送ajax请求
				$.ajax({
					url:'settings/qx/user/login.do',
					contentType:'application/json',
					data:JSON.stringify({
						loginAct:loginAct,
						loginPwd:loginPwd,
						isRemPwd:isRemPwd
					}),
					dataType:'json',
					type:'post',
					success:function (data) {
						if (data.state == 'SUCCESS'){
							window.location.href="workbench/index.do";
						}else {
							$("#msg").text(data.message);
						}
					},
					beforeSend:function (){
						//表单验证
						if (loginAct == "" || loginPwd == ""){
							e.preventDefault();
							$("#msg").text("用户名密码不能为空");
							return false;
						}
						return true;

					}
				})
			})
		})
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;吃个桃桃</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="/ggfg" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control" type="text" value="${cookie.loginAct.value}" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control" type="password" value="${cookie.loginPwd.value}" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
								<input type="checkbox" id="isRemPwd" checked> 记住密码
							</c:if>
							<c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
								<input type="checkbox" id="isRemPwd"> 记住密码
							</c:if>
						</label>
						&nbsp;&nbsp;
						<span id="msg"></span>
					</div>
					<button type="submit" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>