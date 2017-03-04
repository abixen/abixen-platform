/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function () {
    'use strict';

    angular
        .module('platformPageModule')
        .service('PageModelParser', PageModelParser);

    function PageModelParser() {
        this.createModel = createModel;
        this.createPageModelDto = createPageModelDto;
        this.findModuleId = findModuleId;
        this.updateModelModulesNullIds = updateModelModulesNullIds;


        function createModel(pageModelDto) {
            var model = JSON.parse(pageModelDto.page.layout.contentAsJson);

            model.description = pageModelDto.page.description;
            model.title = pageModelDto.page.title;
            model.icon = pageModelDto.page.icon;
            model.structure = pageModelDto.page.layout.title;

            //initialize modules
            for (var i = 0; i < model.rows.length; i++) {
                var row = model.rows[i];
                for (var j = 0; j < row.columns.length; j++) {
                    var column = row.columns[j];
                    column.modules = [];
                }
            }

            //fill modules
            for (var l = 0; l < pageModelDto.dashboardModuleDtos.length; l++) {
                var dashboardModuleDto = pageModelDto.dashboardModuleDtos[l];
                dashboardModuleDto.config = {};
                model.rows[dashboardModuleDto.rowIndex].columns[dashboardModuleDto.columnIndex].modules[dashboardModuleDto.orderIndex] = dashboardModuleDto;
            }

            return model;
        }

        function createPageModelDto(page, model) {
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

                    for (var k = 0; k < column.modules.length; k++) {

                        var module = column.modules[k];
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
            pageModelDto.page.description = model.description;
            pageModelDto.page.icon = model.icon;

            return pageModelDto;
        }

        function findModuleId(frontendId, dashboardModuleDtos) {
            for (var i = 0; i < dashboardModuleDtos.length; i++) {
                if (dashboardModuleDtos[i].frontendId == frontendId) {
                    return dashboardModuleDtos[i].id;
                }
            }
            return null;
        }

        function updateModelModulesNullIds(model, dashboardModuleDtos) {

            for (var r = 0; r < model.rows.length; r++) {
                for (var c = 0; c < model.rows[r].columns.length; c++) {
                    for (var w = 0; w < model.rows[r].columns[c].modules.length; w++) {
                        if (model.rows[r].columns[c].modules[w].id == null) {
                            model.rows[r].columns[c].modules[w].id = this.findModuleId(model.rows[r].columns[c].modules[w].wid, dashboardModuleDtos);
                        }
                    }
                }
            }

            return model;
        }
    }
})();