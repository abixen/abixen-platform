/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * Copyright (c) 2015, Sebastian Sdorra
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
        .module('platformDashboardModule')
        .service('dashboardUtils', dashboardUtils);

    dashboardUtils.$inject = ['$log', 'dashboardData'];

    function dashboardUtils($log, dashboardData) {
        this.createConfiguration = createConfiguration;
        this.changeLayout = changeLayout;
        this.addNewModuleToModel = addNewModuleToModel;


        function createConfiguration(type) {
            var cfg = {};
            var config = dashboardData.getModules()[type].config;
            if (config) {
                cfg = angular.copy(config);
            }
            return cfg;
        }

        function changeLayout(model, layout) {
            var columns = readColumns(model);
            var counter = 0;
            model.rows = angular.copy(JSON.parse(layout.content).rows);  // the parameter is JSON.parse(structure.contentAsJson).rows
            // because of the structure passed from DB and to allow updating layout in DB

            while (counter < columns.length) {
                counter = fillLayout(model, columns, counter);
            }
        }

        function addNewModuleToModel(model, module) {
            if (model) {
                var column = findFirstModuleColumn(model);
                if (column) {
                    if (!column.modules) {
                        column.modules = [];
                    }
                    column.modules.unshift(module);
                } else {
                    $log.error('could not find first module column');
                }
            } else {
                $log.error('model is undefined');
            }
        }

        function copyModules(source, target) {
            if (source.modules && source.modules.length > 0) {
                var w = source.modules.shift();
                while (w) {
                    target.modules.push(w);
                    w = source.modules.shift();
                }
            }
        }

        function readColumns(root, columns) {
            columns = columns || [];

            if (angular.isDefined(root.rows)) {
                angular.forEach(root.rows, function (row) {
                    angular.forEach(row.columns, function (col) {
                        columns.push(col);
                        // keep reading columns until we can't any more
                        readColumns(col, columns);
                    });
                });
            }

            return columns;
        }

        function fillLayout(root, columns, counter) {
            counter = counter || 0;

            if (angular.isDefined(root.rows)) {
                angular.forEach(root.rows, function (row) {
                    angular.forEach(row.columns, function (column) {
                        // if the modules prop doesn't exist, create a new array for it.
                        // this allows ui.sortable to do it's thing without error
                        if (!column.modules) {
                            column.modules = [];
                        }

                        // if a column exist at the counter index, copy over the column
                        if (angular.isDefined(columns[counter])) {
                            // do not add modules to a column, which uses nested rows
                            if (!angular.isDefined(column.rows)) {
                                copyModules(columns[counter], column);
                                counter++;
                            }
                        }

                        counter = fillLayout(column, columns, counter);
                    });
                });
            }
            return counter;
        }

        function findFirstModuleColumn(model) {
            var column = null;
            if (!angular.isArray(model.rows)) {
                $log.error('model does not have any rows');
                return null;
            }
            for (var i = 0; i < model.rows.length; i++) {
                var row = model.rows[i];
                if (angular.isArray(row.columns)) {
                    for (var j = 0; j < row.columns.length; j++) {
                        var col = row.columns[j];
                        if (!col.rows) {
                            column = col;
                            break;
                        }
                    }
                }
                if (column) {
                    break;
                }
            }
            return column;
        }
    }
})();