<%--
  Created by IntelliJ IDEA.
  User: langqi.ni
  Date: 2016/12/19
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String ctx = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + ctx + "/";
%>

<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet" href="/framework/bootstrap/css/bootstrap.min.css"/>
<!-- Font Awesome -->
<link rel="stylesheet" href="/framework/bootstrap/font-awesome-4.5.0/css/font-awesome.min.css"/>
<!-- Ionicons -->
<link rel="stylesheet" href="/framework/bootstrap/font-awesome-4.5.0/css/ionicons.min.css"/>
<!-- Theme style -->
<link rel="stylesheet" href="/framework/adminlte/css/AdminLTE.min.css"/>
<!-- AdminLTE Skins. Choose a skin from the css/skins
folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="/framework/adminlte/css/skins/_all-skins.min.css"/>
