<%--
  Created by IntelliJ IDEA.
  User: mzj18
  Date: 2023/3/27
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link type="text/css" rel="stylesheet" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript">
        $(function (){
            $("input[name='mydate']").datetimepicker({
                language:'zh-CN', //语言
                format:'yyyy-mm-dd', //日历格式
                minView:'month', //最小视图
                initialDate:new Date(), //初始日期
                autoclose:true, //自动关闭 默认false
                todayBtn:true, //显示今天日期
                clearBtn:true, //清空日历
            })
        })
    </script>
</head>
<body>
    <input name="mydate" class="mydate" type="text" readonly>
</body>
</html>
