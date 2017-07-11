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



<!-- jQuery 2.1.4 -->
<script src="/framework/jquery/jquery.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="/framework/jquery/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.5 -->
<script src="/framework/bootstrap/js/bootstrap.min.js"></script>
<!-- Morris.js charts -->
<script src="/framework/plugins/table/bootstrap-table-niow.js"></script>
<script src="/framework/plugins/table/bootstrap-table-zh-CN.js"></script>
<!-- AdminLTE App -->
<script src="/framework/adminlte/js/app.min.js"></script>