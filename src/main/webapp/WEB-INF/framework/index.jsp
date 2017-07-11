<%--
  Created by IntelliJ IDEA.
  User: langqi.ni
  Date: 2016/12/12
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>index</title>
    <jsp:include page="main/commonJs.jsp"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="main/mainHeader.jsp"/>
    <jsp:include page="main/mainLeft.jsp"/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                首页
                <small>欢迎使用</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
            </ol>
        </section>
    </div>
    <jsp:include page="main/mainBottom.jsp"/>
</div>
</body>
<jsp:include page="main/commonStyle.jsp"/>
</html>
