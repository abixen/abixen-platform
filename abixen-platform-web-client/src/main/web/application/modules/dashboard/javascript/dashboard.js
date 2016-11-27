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

/**
 * @ngdoc directive
 * @name adf.directive:adfDashboard
 * @element div
 * @restrict EA
 * @scope
 * @description
 *
 * `adfDashboard` is a directive which renders the dashboard with all its
 * components. The directive requires a name attribute. The name of the
 * dashboard can be used to store the model.
 *
 * @param {string} name name of the dashboard. This attribute is required.
 * @param {boolean=} editable false to disable the editmode of the dashboard.
 * @param {boolean=} collapsible true to make widgets collapsible on the dashboard.
 * @param {boolean=} maximizable true to add a button for open widgets in a large modal panel.
 * @param {boolean=} enableConfirmDelete true to ask before remove an widget from the dashboard.
 * @param {string=} structure the default structure of the dashboard.
 * @param {object=} adfModel model object of the dashboard.
 * @param {function=} adfWidgetFilter function to filter widgets on the add dialog.
 * @param (string) description description of model page
 */

angular.module('adf')
    .directive('adfDashboard', ['$rootScope', '$log', 'dashboard', 'adfTemplatePath', '$aside', 'applicationNavigationItems', '$stateParams',
        function ($rootScope, $log, dashboard, adfTemplatePath, $aside, applicationNavigationItems, $stateParams) {
            'use strict';

            $log.log('DASHBOARD');

            function stringToBoolean(string) {
                switch (angular.isDefined(string) ? string.toLowerCase() : null) {
                    case 'true':
                    case 'yes':
                    case '1':
                        return true;
                    case 'false':
                    case 'no':
                    case '0':
                    case null:
                        return false;
                    default:
                        return Boolean(string);
                }
            }

            function copyWidgets(source, target) {
                if (source.widgets && source.widgets.length > 0) {
                    var w = source.widgets.shift();
                    while (w) {
                        target.widgets.push(w);
                        w = source.widgets.shift();
                    }
                }
            }

            /**
             * Copy widget from old columns to the new model
             * @param object root the model
             * @param array of columns
             * @param counter
             */
            function fillStructure(root, columns, counter) {
                counter = counter || 0;

                if (angular.isDefined(root.rows)) {
                    angular.forEach(root.rows, function (row) {
                        angular.forEach(row.columns, function (column) {
                            // if the widgets prop doesn't exist, create a new array for it.
                            // this allows ui.sortable to do it's thing without error
                            if (!column.widgets) {
                                column.widgets = [];
                            }

                            // if a column exist at the counter index, copy over the column
                            if (angular.isDefined(columns[counter])) {
                                // do not add widgets to a column, which uses nested rows
                                if (!angular.isDefined(column.rows)) {
                                    copyWidgets(columns[counter], column);
                                    counter++;
                                }
                            }

                            // run fillStructure again for any sub rows/columns
                            counter = fillStructure(column, columns, counter);
                        });
                    });
                }
                return counter;
            }

            /**
             * Read Columns: recursively searches an object for the 'columns' property
             * @param object model
             * @param array  an array of existing columns; used when recursion happens
             */
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

            function changeStructure(model, structure) {
                var columns = readColumns(model);
                var counter = 0;

                model.rows = angular.copy(JSON.parse(structure.content).rows);  // the parameter is JSON.parse(structure.content).rows
                                                                                // because of the structure passed from DB and to allow updating layout in DB
                model.structure = structure.title;                              // assign new structure name

                while (counter < columns.length) {
                    counter = fillStructure(model, columns, counter);
                }
            }

            function createConfiguration(type) {
                var cfg = {};
                var config = dashboard.widgets[type].config;
                if (config) {
                    cfg = angular.copy(config);
                }
                return cfg;
            }

            /**
             * Find first widget column in model.
             *
             * @param dashboard model
             */
            function findFirstWidgetColumn(model) {
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

            /**
             * Adds the widget to first column of the model.
             *
             * @param dashboard model
             * @param widget to add to model
             */
            function addNewWidgetToModel(model, widget) {
                if (model) {
                    var column = findFirstWidgetColumn(model);
                    if (column) {
                        if (!column.widgets) {
                            column.widgets = [];
                        }
                        column.widgets.unshift(widget);
                    } else {
                        $log.error('could not find first widget column');
                    }
                } else {
                    $log.error('model is undefined');
                }
            }

            return {
                replace: true,
                restrict: 'EA',
                transclude: false,
                scope: {
                    structure: '@',
                    name: '@',
                    collapsible: '@',
                    editable: '@',
                    maximizable: '@',
                    adfModel: '=',
                    adfWidgetFilter: '='
                },
                controller: function ($scope) {
                    var model = {};
                    var structure = {};
                    var widgetFilter = null;
                    var structureName = {};
                    var name = $scope.name;

                    $scope.$on(platformParameters.events.ADF_WIDGET_DELETED_EVENT, function (event) {
                        $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.adfModel);
                    });

                    $scope.$on(platformParameters.events.ADF_WIDGET_TITLE_CHANGED_EVENT, function (event) {
                        $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.adfModel);
                    });

                    // Watching for changes on adfModel
                    $scope.$watch('adfModel', function (oldVal, newVal) {

                        $log.log('CHANGED!!!!!!!!!!!!!!!!!!!!!');

                        // has model changed or is the model attribute not set
                        if (newVal !== null || (oldVal === null && newVal === null)) {
                            model = $scope.adfModel;
                            widgetFilter = $scope.adfWidgetFilter;
                            if (!model || !model.rows) {
                                structureName = $scope.structure;
                                structure = dashboard.structures[structureName];
                                if (structure) {
                                    if (model) {
                                        model.rows = angular.copy(structure).rows;
                                    } else {
                                        model = angular.copy(structure);
                                    }
                                    model.structure = structureName;
                                } else {
                                    $log.error('could not find structure ' + structureName);
                                }
                            }

                            if (model) {
                                if (!model.title) {
                                    model.title = 'Dashboard';
                                }
                                if (!model.description) {
                                    model.description = '';
                                }
                                $scope.model = model;
                            } else {
                                $log.error('could not find or create model');
                            }
                        }
                    }, true);

                    // edit mode
                    $scope.editMode = false;
                    $scope.editClass = '';

                    $scope.toggleEditMode = function (preventSave) {
                        $scope.editMode = !$scope.editMode;
                        if ($scope.editMode) {
                            $scope.modelCopy = angular.copy($scope.adfModel, {});
                        }

                        if (!$scope.editMode && !preventSave) {
                            $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, name, model);
                        }
                    };

                    $scope.$on(platformParameters.events.ADF_TOGGLE_EDIT_MODE_EVENT, function (event, preventSave) {
                        $scope.toggleEditMode(preventSave);
                        $scope.$emit(platformParameters.events.ADF_TOGGLE_EDIT_MODE_RESPONSE_EVENT, $scope.editMode);
                    });

                    $scope.collapseAll = function (collapseExpandStatus) {
                        $rootScope.$broadcast('adfDashboardCollapseExapand', {collapseExpandStatus: collapseExpandStatus});
                    };

                    /*$scope.cancelEditMode = function(){
                     $scope.editMode = false;
                     $scope.modelCopy = angular.copy($scope.modelCopy, $scope.adfModel);
                     $rootScope.$broadcast('adfDashboardEditsCancelled');
                     };*/

                    // edit dashboard settings
                    $scope.editDashboardDialog = function () {
                        var editDashboardScope = $scope.$new();
                        // create a copy of the title, to avoid changing the title to
                        // "dashboard" if the field is empty
                        editDashboardScope.copy = {
                            title: model.title,
                            description: model.description
                        };
                        editDashboardScope.structures = dashboard.structures;
                        var instance = $aside.open({
                            scope: editDashboardScope,
                            placement: 'left',
                            templateUrl: adfTemplatePath + 'dashboard-edit.html',
                            size: 'md',
                            backdrop: 'static'
                        });
                        $scope.changeStructure = function (name, structure) {
                            $log.info('change structure to ' + name);
                            changeStructure(model, structure);
                            $rootScope.$broadcast(platformParameters.events.ADF_STRUCTURE_CHANGED_EVENT, structure);
                        };
                        editDashboardScope.closeDialog = function () {

                            if (!editDashboardScope.copy.title) {
                                $log.log('Form is invalid and could not be saved.');
                                editDashboardScope.$broadcast('show-errors-check-validity');
                                return;
                            }
                            // copy the new title back to the model
                            model.title = editDashboardScope.copy.title;
                            // close modal and destroy the scope
                            instance.dismiss();
                            editDashboardScope.$destroy();
                            applicationNavigationItems.editSidebarItem($stateParams.id, model.title);
                            $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, name, model);
                        };
                        editDashboardScope.cancelDialog = function () {
                            // close modal and destroy the scope
                            instance.dismiss();
                            editDashboardScope.$destroy();
                        };
                    };

                    $scope.$on(platformParameters.events.ADF_EDIT_DASHBOARD_EVENT, function (event) {
                        $scope.editDashboardDialog();
                    });

                    // add widget dialog
                    $scope.addWidgetDialog = function () {
                        var addScope = $scope.$new();
                        var model = $scope.model;
                        var widgets;
                        if (angular.isFunction(widgetFilter)) {
                            widgets = {};
                            angular.forEach(dashboard.widgets, function (widget, type) {
                                if (widgetFilter(widget, type, model)) {
                                    widgets[type] = widget;
                                }
                            });
                        } else {
                            widgets = dashboard.widgets;
                        }
                        addScope.widgets = widgets;
                        var opts = {
                            scope: addScope,
                            placement: 'left',
                            templateUrl: adfTemplatePath + 'widget-add.html',
                            size: 'sx',
                            backdrop: 'static'
                        };
                        var instance = $aside.open(opts);
                        addScope.addWidget = function (widgetType, widget) {
                            var w = {
                                id: widget.id,
                                type: widgetType,
                                title: widget.title,
                                moduleType: widget.moduleType,
                                config: createConfiguration(widgetType),
                                wid: dashboard.id()
                            };
                            addNewWidgetToModel(model, w);
                            $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, name, model);
                            // close and destroy
                            instance.dismiss();
                            addScope.$destroy();
                        };
                        addScope.closeDialog = function () {
                            // close and destroy
                            instance.dismiss();
                            addScope.$destroy();
                        };
                    };

                    $scope.$on(platformParameters.events.ADF_ADD_WIDGET_EVENT, function (event) {
                        $scope.addWidgetDialog();
                    });
                },
                link: function ($scope, $element, $attr) {

                    $scope.$on('FULL_SCREEN_MODE', function (event, moduleId, fullScreenMode) {
                        var dashboardSubContainer = angular.element(document.getElementById('dashboard-sub-container'));

                        if (fullScreenMode) {
                            dashboardSubContainer.addClass('hidden');
                            var dashboardContainer = angular.element(document.getElementById('dashboard-container'));
                            var moduleContent = angular.element(document.getElementById('module-content-' + moduleId));
                            var module = angular.element('<div class="container-fluid modules-wrapper custom-column-container ng-scope ng-isolate-scope" id="adf-dashboard-row-full-screen"><div class="column ng-pristine ng-untouched ng-valid ng-scope ng-isolate-scope col-md-12"><div id="module-full-screen" class="module"></div></div></div>');
                            dashboardContainer.append(module);
                            var moduleFullScreen = angular.element(document.getElementById('module-full-screen'));
                            moduleFullScreen.append(moduleContent);
                        } else {
                            dashboardSubContainer.removeClass('hidden');
                            var moduleContent = angular.element(document.getElementById('module-content-' + moduleId));
                            var module = angular.element(document.getElementById('module-' + moduleId));
                            module.append(moduleContent);
                            var moduleFullScreen = angular.element(document.getElementById('adf-dashboard-row-full-screen'));
                            moduleFullScreen.remove();
                        }
                    });

                    // pass options to scope
                    var options = {
                        name: $attr.name,
                        editable: true,
                        enableConfirmDelete: stringToBoolean($attr.enableconfirmdelete),
                        maximizable: stringToBoolean($attr.maximizable),
                        collapsible: stringToBoolean($attr.collapsible)
                    };
                    if (angular.isDefined($attr.editable)) {
                        options.editable = stringToBoolean($attr.editable);
                    }
                    $scope.options = options;
                },
                templateUrl: adfTemplatePath + 'dashboard.html'
            };
        }]);