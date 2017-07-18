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
        clickToSelect: true,
        queryParamsType: "limit",
        queryParams: function (params) {
            params.id = $("#searchbox").val();
            params.radmon = Math.random();
            params.sort = "id,desc";
            return params;
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
function showChildrenMenu(row, $element) {

    var id = row.id;
        $.ajax({
            url: "/menu/" + id,
            type:"get",
            success: function (data, status) {
                if (status != "success") {
                    toastr.error("保存失败，请检查网络和服务器运行情况！");
                    return;
                }
                if (data.code != 200) {
                    toastr.error(data.msg);
                    return;
                }
                toastr.success(data.msg);
                $("#table_children_menu").bootstrapTable({
                    pagination: "true",
                    pageList: "[10, 20, 30, ALL]",
                    sidePagination: "server",
                    toolbar: "#tableToolbar",
                    pageSize: "10",
                    clickToSelect: true,
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
                        }
                    ]
                });
            },
            error: function (data, status) {
                toastr.error("加载失败，请检查网络和服务器运行情况！");
            }
        })
}


function loadUserInfo() {

}