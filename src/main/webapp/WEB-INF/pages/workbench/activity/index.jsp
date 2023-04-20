<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
<script type="text/javascript">

	$(function(){


		//显示页面
		queryActivityByConditionForPage(1,10);
		//查询按钮点击事件
		$('#queryActivityBtn').click(function (event){
			event.preventDefault();
			queryActivityByConditionForPage(1,$('#page').bs_pagination('getOption','rowsPerPage'));
		});

		//选择市场活动
		//全选按钮添加点击事件
		$('#checkAll').click(function (){
			//如果全选按钮选中，所有市场活动都选中
			$("#tBody input[type='checkbox']").prop("checked",this.checked);
		});

		$('#tBody').on("click","input[type='checkbox']",function () {
			//如果所有市场活动都选中，全选按钮也选中
			if ($("#tBody input[type='checkbox']").size()==$("#tBody input[type='checkbox']:checked").size()){
				$('#checkAll').prop('checked',true);
			}else{
				//有一个市场活动没选中，全选按钮也为false
				$('#checkAll').prop('checked',false);

			}
		});

		//控制模态窗口的显示
		$('#createBtn').click(function (){

			//重置表单
			$('#createActivityForm').get(0).reset();
			//显示模态窗口
			$('#createActivityModal').modal("show");

		});


		//保存创建活动
		$('#saveCreateActivityBtn').click(function () {
			var owner=$("#create-marketActivityOwner").val();
			var name = $.trim($('#create-marketActivityName').val());
			var startTime = $('#create-startTime').val();
			var endTime = $('#create-endTime').val();
			var cost = $.trim($('#create-cost').val());
			var description=$.trim($("#create-describe").val());
			//表单验证
			if (owner == ""){
				alert("所有者不能为空");
				return;
			}
			if (name == ""){
				alert("名称不能为空");
				return;
			}
			if (startTime != "" && endTime != ""){
				if (startTime > endTime){
					alert("输入日期不合法")
					return;
				}
			}
			var regx = /^(([1-9]\d*)|0)$/;
			if (!regx.test(cost)){
				alert("成本只能为非负整数");
				return;
			}

			$.ajax({
				url:'/workbench/activity/save.do',
				type:'post',
				data:JSON.stringify({
					owner:owner,
					name:name,
					startDate:startTime,
					endDate:endTime,
					cost:cost,
					description:description
				}),
				dataType:'json',
				contentType:'application/json',
				success:function (data){
					if (data.state == "SUCCESS"){
						//关闭模态窗口
						$('#createActivityModal').modal('hide');
						//刷新列表 返回列表第一页
						queryActivityByConditionForPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));

					}else {
						alert(data.message);
					}
				}

			})
		})

		//删除活动
		$('#deleteBtn').click(function () {
			//获取所有被选中的活动
			var activities = $("#tBody input[type='checkbox']:checked");
			if (activities.size() == 0){
				alert("请选择要删除的市场活动");
				return;
			}

			if (window.confirm("确认删除")){
				//获取参数 数组
				var ids = "";
				$.each(activities,function (index,obj){
					ids += obj.value+',';
				});
				ids = ids.substring(0,ids.length-1);

				$.ajax({
					url:'workbench/activity/delete.do',
					type:'post',
					data:{
						ids:ids
					},
					dataType:'json',
					success:function (data){
						if (data.state == 'SUCCESS'){
							//刷新页面
							queryActivityByConditionForPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));

							if ($('#checkAll').prop('checked')){
								$('#checkAll').prop('checked',false);
							}
						}else {
							alert(data.message);
						}
					}
				});
			}
		});

		//修改活动
		$('#modifyBtn').click(function () {
			var activity =  $("tBody input[type=checkbox]:checked")
			if (activity.size() > 1){
				alert("每次只能修改一个活动");
				return;
			}
			if (activity.size() == 0){
				alert("请选择要修改的活动");
				return;
			}
			var id = activity[0].value;
			$.ajax({
				url:'workbench/activity/select.do',
				type:'post',
				data:{
					id:id
				},
				dataType:'json',
				success:function (data) {
					$("#edit-id").val(data.id);
					$('#edit-marketActivityOwner').val(data.owner);
					$("#edit-marketActivityName").val(data.name);
					$("#edit-startTime").val(data.startDate);
					$("#edit-endTime").val(data.endDate);
					$("#edit-cost").val(data.cost);
					$("#edit-describe").val(data.description);
					//弹出模态窗口
					$("#editActivityModal").modal("show");
				}
			});
		});

		//点击更新按钮
		$('#editActivityBtn').click(function (){
			//获取参数
			var id=$("#edit-id").val();
			var owner=$("#edit-marketActivityOwner").val();
			var name=$.trim($("#edit-marketActivityName").val());
			var startDate=$("#edit-startTime").val();
			var endDate=$("#edit-endTime").val();
			var cost=$.trim($("#edit-cost").val());
			var description=$.trim($("#edit-describe").val());
			//表单验证
			if (owner == ""){
				alert("所有者不能为空");
				return;
			}
			if (name == ""){
				alert("名称不能为空");
				return;
			}
			if (startDate != "" && endDate != ""){
				if (startDate > endDate){
					alert("输入日期不合法");
					return;
				}
			}
			var regx = /^(([1-9]\d*)|0)$/;
			if (!regx.test(cost)){
				alert("成本只能为非负整数");
				return;
			}

			$.ajax({
				url:'workbench/activity/modify.do',
				type:'post',
				data:{
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				dataType:'json',
				success:function (data) {
					if (data.state == 'SUCCESS'){

						//关闭模态窗口
						$("#editActivityModal").modal("hide");
						//刷新列表
						queryActivityByConditionForPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));

					}else {
						alert(data.message);

						$("#editActivityModal").modal("show");
					}
				}
			})

		});

		//导出市场活动
		//批量导出点击事件
		$('#exportActivityAllBtn').click(function () {
			window.location.href = 'workbench/activity/export.do';
		});
		//选择导点击事件
		$('#exportActivityXzBtn').click(function (){
			var activities = $("#tBody input[type='checkbox']:checked");
			if (activities.size() == 0){
				alert('请选中市场活动');
				return;
			}
			var ids = "";
			$.each(activities,function (index,activity){
				ids += "ids="+activity.value+"&";
			});
			ids = ids.substring(0,ids.length-1);

			window.location.href = 'workbench/activity/exportP.do?'+ids;
		});

		//导入市场活动
		//importActivityBtn单机事件
		$('#importActivityBtn').click(function () {
			//获取参数
			var activityFileName=$("#activityFile").val();
			var suffix=activityFileName.substr(activityFileName.lastIndexOf(".")+1).toLocaleLowerCase();//xls,XLS,Xls,xLs,....
			if(suffix!="xls"){
				alert("只支持xls文件");
				return;
			}
			var activityFile=$("#activityFile")[0].files[0];
			if(activityFile.size>5*1024*1024){
				alert("文件大小不超过5MB");
				return;
			}
			var formData = new FormData();
			formData.append('multipartFile',activityFile);
			$.ajax({
				url:'workbench/activity/import.do',
				type:'post',
				processData:false,//设置ajax向后台提交参数之前，是否把参数统一转换成字符串：true--是,false--不是,默认是true
				contentType:false,//设置ajax向后台提交参数之前，是否把所有的参数统一按urlencoded编码：true--是,false--不是，默认是true
				data:formData,
				dataType:'json',
				success:function (data) {
					if (data.state == 'SUCCESS'){
						//插入成功
						alert("成功导入"+data.importRet+"条记录");
						//关闭模态窗口
						$("#importActivityModal").modal("hide");
						//刷新市场活动列表,显示第一页数据,保持每页显示条数不变
						queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
					}
				}
			})
		});


		//显示日历
		$('.mydate').datetimepicker({
			language:'zh-CN', //语言
			format:'yyyy-mm-dd', //日历格式
			minView:'month', //最小视图
			initialDate:new Date(), //初始日期
			autoclose:true, //自动关闭 默认false
			todayBtn:true, //显示今天日期
			clearBtn:true, //清空日历
		})

		//分页显示活动函数定义
		function queryActivityByConditionForPage(pageNo,pageSize){
			//获取参数
			var name=$("#query-name").val();
			var owner=$("#query-owner").val();
			var startDate=$("#query-startDate").val();
			var endDate=$("#query-endDate").val();
			var beginNo = (pageNo - 1)*pageSize;
			$.ajax({
				url:'/workbench/activity/show.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify({
					name:name,
					owner,owner,
					startDate,startDate,
					endDate,endDate,
					beginNo,beginNo,
					pageSize,pageSize
				}),
				success:function (data) {
					//遍历后端返回的活动列表
					var htmlStr = "";
					$.each(data.activityList,function (index,obj) {
						htmlStr+="<tr class=\"active\">";
						htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
						htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.id+"'\">"+obj.name+"</a></td>";
						htmlStr+="<td>"+obj.owner+"</td>";
						htmlStr+="<td>"+obj.startDate+"</td>";
						htmlStr+="<td>"+obj.endDate+"</td>";
						htmlStr+="</tr>";
					});
					$('#tBody').html(htmlStr);

					var totalPages = 1;
					if (data.totalRows % pageSize == 0){
						totalPages = data.totalRows/pageSize;
					}else {
						totalPages = parseInt(data.totalRows/pageSize) + 1;
					}
					$('#page').bs_pagination({
						currentPage:pageNo,//当前页号,相当于pageNo

						rowsPerPage:pageSize,//每页显示条数,相当于pageSize
						totalRows:data.totalRows,//总条数
						totalPages: totalPages,  //总页数,必填参数.

						visiblePageLinks:5,//最多可以显示的卡片数

						showGoToPage:true,//是否显示"跳转到"部分,默认true--显示
						showRowsPerPage:true,//是否显示"每页显示条数"部分。默认true--显示
						showRowsInfo:true,//是否显示记录的信息，默认true--显示
						
						onChangePage:function (event,page) {
							queryActivityByConditionForPage(page.currentPage,page.rowsPerPage);
						}
					})
				}
			})

		}

		
		
	});
	
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="createActivityForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${users}" var="user">
									  <option value="${user.id}">${user.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" name="mydate" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" name="mydate" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveCreateActivityBtn" type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${users}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="edit-startTime" value="2020-10-10" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="edit-endTime" value="2020-10-20" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="editActivityBtn" type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="query-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="query-endDate">
				    </div>
				  </div>
				  
				  <button type="submit" class="btn btn-default" id="queryActivityBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="modifyBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">
<%--						<tr class="active">--%>
<%--							<td><input type="checkbox" /></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--							<td>2020-10-10</td>--%>
<%--							<td>2020-10-20</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>2020-10-10</td>--%>
<%--                            <td>2020-10-20</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
			</div>
			<div id="page"></div>
			
<%--			<div style="height: 50px; position: relative;top: 30px;" >--%>
<%--				<div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>--%>
<%--				</div>--%>
<%--				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>--%>
<%--					<div class="btn-group">--%>
<%--						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
<%--							10--%>
<%--							<span class="caret"></span>--%>
<%--						</button>--%>
<%--						<ul class="dropdown-menu" role="menu">--%>
<%--							<li><a href="#">20</a></li>--%>
<%--							<li><a href="#">30</a></li>--%>
<%--						</ul>--%>
<%--					</div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>--%>
<%--				</div>--%>
<%--				<div style="position: relative;top: -88px; left: 285px;">--%>
<%--					<nav>--%>
<%--						<ul class="pagination">--%>
<%--							<li class="disabled"><a href="#">首页</a></li>--%>
<%--							<li class="disabled"><a href="#">上一页</a></li>--%>
<%--							<li class="active"><a href="#">1</a></li>--%>
<%--							<li><a href="#">2</a></li>--%>
<%--							<li><a href="#">3</a></li>--%>
<%--							<li><a href="#">4</a></li>--%>
<%--							<li><a href="#">5</a></li>--%>
<%--							<li><a href="#">下一页</a></li>--%>
<%--							<li class="disabled"><a href="#">末页</a></li>--%>
<%--						</ul>--%>
<%--					</nav>--%>
<%--				</div>--%>
<%--			</div>--%>
			
		</div>
		
	</div>
</body>
</html>