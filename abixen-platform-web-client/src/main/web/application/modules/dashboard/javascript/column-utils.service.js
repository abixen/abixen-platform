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
        .service('columnUtils', columnUtils);

    columnUtils.$inject = ['$log'];

    function columnUtils($log) {
        this.applySortable = applySortable;


        var moveCounter = 0;
        var addModuleToColumnFunction;
        var removeModuleFromColumnFunction;

        function applySortable($scope, $element, model, column) {
            var el = $element[0].childNodes[1];
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

            $element.on('$destroy', function () {
                sortable.destroy();
            });
        }

        function moveModuleInColumn($scope, column, evt) {
            var modules = column.modules;
            $scope.$apply(function () {
                modules.splice(evt.newIndex, 0, modules.splice(evt.oldIndex, 1)[0]);
                $scope.$emit(platformParameters.events.DASHBOARD_MODULE_MOVED_EVENT);
            });
        }

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

        function getId(el) {
            var id = el.getAttribute('adf-id');
            return id ? parseInt(id) : -1;
        }

        function addModuleToColumn($scope, model, targetColumn, evt) {
            // find source column
            var cid = getId(evt.from);
            var sourceColumn = findColumn(model, cid);

            if (sourceColumn) {
                var wid = getId(evt.item);
                var module = findModule(sourceColumn, wid);

                if (module) {
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

        function removeModuleFromColumn($scope, column, evt) {
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
                        $scope.$emit(platformParameters.events.DASHBOARD_MODULE_MOVED_EVENT);
                    }
                });
                $scope.$apply(function () {
                    addModuleToColumnFunction.func.apply(null, addModuleToColumnFunction.params);
                    if (++saveCounter === 2) {
                        $scope.$emit(platformParameters.events.DASHBOARD_MODULE_MOVED_EVENT);
                    }
                });
            }
        }

        function removeModuleFromColumnHandler(modules, oldIndex) {
            modules.splice(oldIndex, 1);
        }
    }
})();