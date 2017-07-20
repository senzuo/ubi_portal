/**
 * Created by niow on 16/4/5.
 */
/*加载父菜单*/
function initTable() {
    var $table = $('#table_menu');
    $table.bootstrapTable({
        pagination: "true",
        pageList: "[5, 10, 20, ALL]",
        sidePagination: "server",
        url: "/menu/page",
        toolbar: "#tableToolbar",
        dataField: "data.entities",
        pageSize: "5",
        onClickRow: showChildrenMenu,
        onClickCell:function (field, value, row, $element) {
            $(".selected").removeClass("selected");
            $element.addClass("selected");
        },
        queryParamsType: "limit",
        queryParams: function (params) {
            params.id = $("#searchbox").val();
            params.radmon = Math.random();
            params.sort = "id,desc";
            return params;
        },
        onLoadSuccess:function (data) {
            var firstMenu = data.data.entities[0];
            initChildrenMenu(firstMenu);/*这里传入的是一条记录，对应于类就是一个对象，这个是从后台传到页面的json里拿的*/
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
                        showInformation(row,index);
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




/*显示子菜单数据*/
function initChildrenMenu(row, $element){
    var id = row.id;
    $("#table_children_menu").bootstrapTable({
        pagination: "true",
        pageList: "[5, 10, 20, ALL]",
        sidePagination: "server",
        toolbar: "#tableToolbar",
        pageSize: "5",
        url:"/menu/" + id,
        clickToSelect: true,
        dataField: "data.entities",
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




/*删除父菜单下的某个子菜单*/
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
        $.post(
            "/menu/delete",
            {
                id : menuId/*这里怎么用ajax实现*/
            }
        )
        $("#table_children_menu").bootstrapTable("refresh");
    });
}




/*添加父菜单*/
function addMenu() {
    $.post(
        "/menu",
        {
            name: $("#txt_menu_name_add").val(),
            icon: $("#txt_menu_icon_add").val(),
            order_index: $("#txt_menu_order_index_add").val()
        }
    )
    hideAddMenuForm();
    $("#table_menu").bootstrapTable("refresh",{url:"/menu/page"});
}
function hideAddMenuForm() {
    $('#form_menu_add').modal('hide');
    $("#txt_menu_name_add").val("");
    $("#txt_menu_icon_add").val("");
    $("#txt_menu_order_index_add").val("");
}
function showAddMenuForm() {
    $('#form_menu_add').modal().css({
        "margin-top": function () {
            return ($(this).height() / 12);
        }
    });
}


/*编辑父菜单*/
function showInformation(row,index) {

    $("#txt_menu_id_edit").val(row.id);
    $("#txt_menu_name_edit").val(row.title);
    $("#txt_menu_order_index_edit").val(row.orderIndex);
    $("#txt_menu_icon_edit").val(row.icon);

}

function hideUpdateMenuForm() {
    $('#form_edit').modal('hide');
    $("#txt_menu_name_edit").val("");
    $("#txt_menu_order_index_edit").val("");
    $("#txt_menu_icon_edit").val("");
}

function updateMenu() {
    $.ajax({
        url:"/menu/update",
        type:"put",
        datatype:"json",
        data:{
            id:$("#txt_menu_id_edit").val(),
            title:$("#txt_menu_name_edit").val(),
            order_index:$("#txt_menu_order_index_edit").val(),
            icon:$("#txt_menu_icon_edit").val()
        },
        success:function (data,staus) {
           /* if(staus != success){
                toastr.error("更新失败");
                return;
            }
            if(data.code != 200){
                toastr.error(data.msg);
                return;
            }*/
            toastr.success(data,staus);
            $("#table_menu").bootstrapTable("refresh",{url:"/menu/page"})

            hideUpdateMenuForm();

        }




    })
}