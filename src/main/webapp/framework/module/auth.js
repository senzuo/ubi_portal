/**
 * Created by niow on 16/4/5.
 */


var authModules = [];
function initAuthModuleTable() {
    var $authModuleTable = $('#table_auth_module');
    $authModuleTable.bootstrapTable({
        //pagination: "false",
        url: "/auth/module/all",
        // data:authModule,
        // toolbar: "#tableToolbar",
        dataField: "data.datas",
        // queryParamsType: "limit",
        // queryParams: function (params) {
        //     params.radmon = Math.random();
        //     params.sort = "id,desc";
        //     return params;
        // },
        onClickRow:function (row, $element) {
            var m = authModules[row.moduleCode];
            $('#table_auth').bootstrapTable("load", m.authorityList);
            // initAuthTable(authModules[m]);
        },
        columns: [
            {
                field: 'description',
                align: 'center',
                class: 'col-md-1',
                valign: 'middle'

            }
            ]
        ,onLoadSuccess:function (data) {
            for (var i = 0; i < data.data.length; i++) {
                authModules[data.data[i].moduleCode] = data.data[i];
            }

            initAuthTable(authModules[data.data[0].moduleCode]);
        }
    });
    return $authModuleTable;
}

function initAuthTable(authModule) {
    var $authTable = $('#table_auth');
    $authTable.bootstrapTable(
        {
            data:authModule.authorityList,
            columns: [
                {
                    field: 'description',
                    title: '权限名称',
                    align: 'center',
                    class: 'col-md-1',
                    valign: 'middle'
                },{
                    field: 'url',
                    title: 'URL',
                    align: 'center',
                    class: 'col-md-1',
                    valign: 'middle'
                },{
                    field: 'authCode',
                    title: 'AuthCode',
                    align: 'center',
                    class: 'col-md-1',
                    valign: 'middle'
                }
            ]
        }
    );

    return $authTable;
}