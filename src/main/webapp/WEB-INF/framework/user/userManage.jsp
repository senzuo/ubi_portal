<%--
  Created by IntelliJ IDEA.
  User: langqi.ni
  Date: 2016/12/12
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <jsp:include page="../main/commonJs.jsp"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../main/mainHeader.jsp"/>
    <jsp:include page="../main/mainLeft.jsp"/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                用户管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">用户管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">用户列表</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="table_user" class="table table-bordered table-hover">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <div class="modal" id="form_detail">
            <div class="modal-dialog">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">用户详情</h3>
                    </div><!-- /.box-header -->
                    <!-- form start -->
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="user_name" class="col-sm-2 control-label">留言学员</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="user_name" disabled="true" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="post_creationTime" class="col-sm-2 control-label">留言时间</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="post_creationTime" disabled="true" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="post_content" class="col-sm-2 control-label">留言内容</label>
                                <div class="col-sm-10">
                                    <textarea class="form-control" id="post_content"  disabled="true" rows="3"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">回复老师</label>
                                <div class="col-md-4">
                                    <select  id='reply_teacher' class='select-3 form-control chosen'>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="reply_content" class="col-sm-2 control-label">留言回复</label>
                                <div class="col-sm-10">
                                    <textarea  class="form-control" id="reply_content"  rows="3"></textarea>
                                </div>
                            </div>
                        </div><!-- /.box-body -->
                        <input type="hidden" id="post_id" value=""/>
                        <input type="hidden" id="reply_id" value=""/>
                        <div class="box-footer">
                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                            <button type="button" onclick="saveReply()" class="btn btn-primary pull-right">
                                保存
                            </button>
                        </div>
                    </form>
                </div><!-- /.box -->
            </div>
        </div>

    </div>
    <jsp:include page="../main/mainBottom.jsp"/>
    <script src="/framework/module/user.js" ></script>
    <script type="text/javascript">
        $(document).ready(function () {
            initTable();
        });
    </script>
</div>
</body>
<jsp:include page="../main/commonStyle.jsp"/>
