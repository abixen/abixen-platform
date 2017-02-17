/*
 * The MIT License
 *
 * Copyright (c) 2015, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/* global angular */
angular.module('platformDashboardModule')
    .directive('adfDashboardColumn', ['$log', 'dashboardData', function ($log, dashboardData) {
        'use strict';

        var moveCounter = 0;
        var addModuleToColumnFunction;
        var removeModuleFromColumnFunction;

        /**
         * moves a module in between a column
         */
        function moveModuleInColumn($scope, column, evt) {
            var modules = column.modules;
            // move module and apply to scope
            $scope.$apply(function () {
                modules.splice(evt.newIndex, 0, modules.splice(evt.oldIndex, 1)[0]);
                $scope.$emit(platformParameters.events.ADF_WIDGET_MOVED_EVENT);
            });
        }

        /**
         * finds a module by its id in the column
         */
        function findModule(column, index) {
            var module = null;
            for (var i = 0; i < column.modules.length; i++) {
                var w = column.modules[i];
                if (w.wid === index) {
                    module = w;
                    break;
                }
            }
            return module;
        }

        /**
         * finds a column by its id in the model
         */
        function findColumn(model, index) {
            var column = null;

            for (var i = 0; i < model.rows.length; i++) {
                var r = model.rows[i];
                for (var j = 0; j < r.columns.length; j++) {
                    var c = r.columns[j];
                    if (c.cid === index) {
                        column = c;
                        break;
                    } else if (c.rows) {
                        column = findColumn(c, index);
                    }
                }
                if (column) {
                    break;
                }
            }
            return column;
        }

        /**
         * get the adf id from an html element
         */
        function getId(el) {
            var id = el.getAttribute('adf-id');
            return id ? parseInt(id) : -1;
        }

        /**
         * adds a module to a column
         */
        function addModuleToColumn($scope, model, targetColumn, evt) {
            // find source column
            var cid = getId(evt.from);
            var sourceColumn = findColumn(model, cid);

            if (sourceColumn) {
                // find moved module
                var wid = getId(evt.item);
                var module = findModule(sourceColumn, wid);

                if (module) {
                    // add new item and apply to scope
                    addModuleToColumnFunction = {
                        func: addModuleToColumnHandler,
                        params: [targetColumn.modules, evt.newIndex, module]
                    };
                    moduleMovedNotify($scope);

                } else {
                    $log.warn('could not find module with id ' + wid);
                }
            } else {
                $log.warn('could not find column with id ' + cid);
            }
        }

        function addModuleToColumnHandler(modules, newIndex, module) {
            if (!modules) {
                modules = [];
            }

            modules.splice(newIndex, 0, module);
        }

        /**
         * removes a module from a column
         */
        function removeModuleFromColumn($scope, column, evt) {
            // remove old item and apply to scope

            removeModuleFromColumnFunction = {
                func: removeModuleFromColumnHandler,
                params: [column.modules, evt.oldIndex]
            };
            moduleMovedNotify($scope);
        }

        function moduleMovedNotify($scope) {
            if (++moveCounter === 2) {
                moveCounter = 0;
                var saveCounter = 0;
                $scope.$apply(function () {
                    removeModuleFromColumnFunction.func.apply(null, removeModuleFromColumnFunction.params);
                    if (++saveCounter === 2) {
                        $scope.$emit(platformParameters.events.ADF_WIDGET_MOVED_EVENT);
                    }
                });
                $scope.$apply(function () {
                    addModuleToColumnFunction.func.apply(null, addModuleToColumnFunction.params);
                    if (++saveCounter === 2) {
                        $scope.$emit(platformParameters.events.ADF_WIDGET_MOVED_EVENT);
                    }
                });
            }
        }

        function removeModuleFromColumnHandler(modules, oldIndex) {
            modules.splice(oldIndex, 1);
        }

        /**
         * enable sortable
         */
        function applySortable($scope, $element, model, column) {
            // enable drag and drop
            var el = $element[0].childNodes[0];
            var sortable = Sortable.create(el, {
                group: 'modules',
                handle: '.adf-move',
                ghostClass: 'placeholder',
                animation: 150,
                onAdd: function (evt) {
                    addModuleToColumn($scope, model, column, evt);
                },
                onRemove: function (evt) {
                    removeModuleFromColumn($scope, column, evt);
                },
                onUpdate: function (evt) {
                    moveModuleInColumn($scope, column, evt);
                }
            });

            // destroy sortable on column destroy event
            $element.on('$destroy', function () {
                sortable.destroy();
            });
        }

        return {
            restrict: 'E',
            replace: true,
            scope: {
                column: '=',
                editMode: '=',
                model: '='
            },
            templateUrl: 'application/modules/dashboard/html/dashboard-column.html',
            link: function ($scope, $element) {
                // set id
                var col = $scope.column;
                if (!col.cid) {
                    col.cid = dashboardData.id();
                }

                applySortable($scope, $element, $scope.model, col);
            }
        };
    }]);