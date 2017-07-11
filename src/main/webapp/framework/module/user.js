/**
 * Created by niow on 16/4/5.
 */
function initTable() {
    var $table = $('#table_user');

    $table.bootstrapTable({
        pagination: "true",
        pageList: "[10, 20, 30, ALL]",
        sidePagination: "server",
        url: "/user/page",
        toolbar: "#tableToolbar",
        dataField: "data.datas",
        pageSize: "10",
        clickToSelect: true,
        queryParamsType: "limit",
        queryParams: function (params) {
            params.radmon = Math.random();
            params.sort = "id,desc";
            return params;
        },
        columns: [
            {
                checkbox: true,
            },
            {
                field: 'id',
                title: '用户ID',
                align: 'center',
                class: 'col-md-1',
                valign: 'middle'
            }, {
                field: 'name',
                title: '用户昵称',
                align: 'center',
                class: 'col-md-2',
                valign: 'middle'
            }, {
                field: 'mobile',
                title: '手机号',
                align: 'center',
                class: 'col-md-3',
                valign: 'middle'
            }, {
                field: 'creationTime',
                title: '注册时间',
                align: 'center',
                class: 'col-md-2',

                formatter: function (value, row, index) {
                    return value;
                    // return value == null ? "-" : dateFormat(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                },
                valign: 'middle'
            }, {
                field: 'option',
                title: '操作',
                align: 'center',
                class: 'col-md-4',
                events: {
                    'click .like': function (e, value, row, index) {
                       $("#form_detail").modal();
                    }
                },
                formatter: function (value, row, index) {
                    var rs = "";
                    rs += '<a class="like" href="javascript:void(0)" title="详情编辑">' +
                        '<span class="fa fa-user" aria-hidden="true" /></a>';
                    rs += '&nbsp;&nbsp;&nbsp;<a class="remove" href="javascript:void(0)" title="删除">' +
                        '<i class="glyphicon glyphicon-remove"></i></a>';
                    return rs;

                }
            }]
    });
    return $table;
}

function loadUserInfo() {

}