/**
 * Created by niow on 16/4/5.
 */
function initTable() {
    var $table = $('#table_menu');
    $table.bootstrapTable({
        pagination: "true",
        pageList: "[10, 20, 30, ALL]",
        sidePagination: "server",
        url: "/menu/page",
        toolbar: "#tableToolbar",
        dataField: "data.datas",
        pageSize: "10",
        onClickRow: showChildrenMenu,
        queryParamsType: "limit",
        queryParams: function (params) {
            params.id = $("#searchbox").val();
            params.radmon = Math.random();
            params.sort = "id,desc";
            return params;
        },
        onLoadSuccess:function (data) {
            var firstMenu = data.data.datas[0];
            initChildrenMenu(firstMenu);
        },
        columns: [
            {
                field: 'id',
                title: '菜单ID',
                align: 'center',
                class: 'col-md-3',
                valign: 'middle'
            }, {
                field: 'title',
                title: '菜单名称',
                align: 'center',
                class: 'col-md-3',
                valign: 'middle'
            },{
                field: 'option',
                title: '操作',
                align: 'center',
                class: 'col-md-4',
                events: {
                    'click .like': function (e, value, row, index) {
                        $("#form_edit").modal();
                    }
                },
                formatter: function (value, row, index) {
                    var rs = "";
                    rs += '<a class="like" href="javascript:void(0)" title="菜单编辑">' +
                        '<span class="fa fa-user" aria-hidden="true" /></a>';
                    return rs;

                }
            }
        ]
    });
    return $table;
}
function showAddMenuForm() {
    $('#form_menu_add').modal().css({
        "margin-top": function () {
            return ($(this).height() / 12);
        }
    });
}

function initChildrenMenu(row, $element){
    var id = row.id;
    $("#table_children_menu").bootstrapTable({
        pagination: "true",
        pageList: "[10, 20, 30, ALL]",
        sidePagination: "server",
        toolbar: "#tableToolbar",
        pageSize: "10",
        url:"/menu/" + id,
        clickToSelect: true,
        dataField: "data.datas",
        columns:[
            {
                field: 'title',
                title: '菜单名称',
                align: 'center',
                class: 'col-md-3',
                valign: 'middle'
            },
            {
                field: 'url',
                title: 'url',
                align: 'center',
                class: 'col-md-3',
                valign: 'middle'
            },{
                field: 'icon',
                title: 'icon',
                align: 'center',
                class: 'col-md-3',
                valign: 'middle'
            },{
                field: 'option',
                title: '操作',
                align: 'center',
                class: 'col-md-4',
                events: {
                    'click .remove': function (e, value, row, index) {
                        deleteChildrenMenu(row.id);
                    }
                },
                formatter: function (value, row, index) {
                    var rs = "";
                    rs += '<a class="remove" href="javascript:void(0)" title="删除子菜单">' +
                        '<span class="fa fa-user" aria-hidden="true" /></a>';
                    return rs;

                }
            }
        ]
    });
}
function showChildrenMenu(row, $element) {
    var id = row.id;
    $("#table_children_menu").bootstrapTable("refresh",{url:"/menu/"+id});

}


function hideAddUserForm() {
    $('#form_menu_add').modal('hide');
    $("#txt_menu_name_add").val("");
    $("#txt_menu_icon_add").val("");
    $("#txt_menu_order_index_add").val("");
}

function deleteChildrenMenu(menuId) {

    swal({
        title: "确定删除?",
        text: "用户如果删除数据将不能找回",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定删除",
        closeOnConfirm: true
    }, function () {
        $.ajax({
            url: "/menu/" + menuId,
            type: "delete",
            datatype:json,
            success: function (data, status) {
                if (status != "success") {
                    toastr.error("删除失败，请检查网络和服务器运行情况！");
                    return;
                }
                if (data.code != 200) {
                    toastr.error(data.msg);
                    return;
                }
                toastr.success(data.msg);
                $("#tableUser").bootstrapTable('remove', {
                    field: 'id',
                    values: !isNaN(menuId) ? [menuId] : menuId
                });
            },
            error: function (data, status) {
                toastr.error("删除失败，请检查网络和服务器运行情况！");
            }
        });

    });
}

function addMenu() {

    $.post(
        "/menu",
        {
            name: $("#txt_menu_name_add").val(),
            idCardNo: $("#txt_menu_icon_add").val(),
            mobile: $("#txt_menu_order_index_add").val()
        }
    )
    hideAddUserForm();
    $("#table_menu").bootstrapTable("refresh");
}