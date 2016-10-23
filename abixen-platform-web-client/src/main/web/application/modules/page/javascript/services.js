var pageServices = angular.module('pageServices', ['ngResource']);

pageServices.factory('Page', ['$resource',
    function ($resource) {
        return $resource('api/application/pages/:id', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'},
            delete: {method: 'DELETE'}
        });
    }]);

pageServices.factory('PageModel', ['$resource',
    function ($resource) {
        return $resource('api/pages/:id/model', {}, {
            query: {method: 'GET', isArray: false},
            update: {method: 'PUT'}
        });
    }]);

pageServices.service('PageModelParser', function () {

    this.createModel = function (pageModelDto) {
        var model = JSON.parse(pageModelDto.page.layout.content);
        model.description = pageModelDto.page.description;
        model.title = pageModelDto.page.title;
        model.structure = pageModelDto.page.layout.title;

        //initialize widgets
        for (var i = 0; i < model.rows.length; i++) {
            var row = model.rows[i];
            for (var j = 0; j < row.columns.length; j++) {
                var column = row.columns[j];
                column.widgets = [];
            }
        }

        //fill widgets
        for (var l = 0; l < pageModelDto.dashboardModuleDtos.length; l++) {
            var dashboardModuleDto = pageModelDto.dashboardModuleDtos[l];
            dashboardModuleDto.config = {};
            model.rows[dashboardModuleDto.rowIndex].columns[dashboardModuleDto.columnIndex].widgets[dashboardModuleDto.orderIndex] = dashboardModuleDto;
        }

        return model;
    };

    this.createPageModelDto = function (page, model) {
        var rowIndex = 0;
        var columnIndex = 0;
        var orderIndex = 0;
        var dashboardModuleDtos = [];

        for (var i = 0; i < model.rows.length; i++) {

            var row = model.rows[i];
            columnIndex = 0;

            for (var j = 0; j < row.columns.length; j++) {

                var column = row.columns[j];
                orderIndex = 0;

                for (var k = 0; k < column.widgets.length; k++) {

                    var module = column.widgets[k];
                    module.rowIndex = rowIndex;
                    module.columnIndex = columnIndex;
                    module.orderIndex = orderIndex;
                    module.frontendId = module.wid;
                    dashboardModuleDtos.push(module);
                    orderIndex++;
                }
                columnIndex++;
            }
            rowIndex++;
        }

        var pageModelDto = {page: page, dashboardModuleDtos: dashboardModuleDtos};
        pageModelDto.page.title = model.title;

        return pageModelDto;
    };

    this.findModuleId = function (frontendId, dashboardModuleDtos) {
        for (var i = 0; i < dashboardModuleDtos.length; i++) {
            if (dashboardModuleDtos[i].frontendId == frontendId) {
                return dashboardModuleDtos[i].id;
            }
        }
        return null;
    };

    this.updateModelModulesNullIds = function (model, dashboardModuleDtos) {

        for (var r = 0; r < model.rows.length; r++) {
            for (var c = 0; c < model.rows[r].columns.length; c++) {
                for (var w = 0; w < model.rows[r].columns[c].widgets.length; w++) {
                    if (model.rows[r].columns[c].widgets[w].id == null) {
                        model.rows[r].columns[c].widgets[w].id = this.findModuleId(model.rows[r].columns[c].widgets[w].wid, dashboardModuleDtos);
                    }
                }
            }
        }

        return model;
    };

});
