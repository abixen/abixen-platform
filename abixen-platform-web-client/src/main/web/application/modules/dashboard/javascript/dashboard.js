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

angular.module('platformDashboardModule')
    .directive('adfDashboard', ['$rootScope', '$log', 'dashboardData', '$aside', 'applicationNavigationItems', '$stateParams', 'Layout',
        function ($rootScope, $log, dashboardData, $aside, applicationNavigationItems, $stateParams, Layout) {
            'use strict';

            function copyModules(source, target) {
                if (source.modules && source.modules.length > 0) {
                    var w = source.modules.shift();
                    while (w) {
                        target.modules.push(w);
                        w = source.modules.shift();
                    }
                }
            }

            /**
             * Copy module from old columns to the new model
             * @param object root the model
             * @param array of columns
             * @param counter
             */
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

            function changeLayout(model, layout) {
                var columns = readColumns(model);
                var counter = 0;
                model.rows = angular.copy(JSON.parse(layout.content).rows);  // the parameter is JSON.parse(structure.contentAsJson).rows
                // because of the structure passed from DB and to allow updating layout in DB
                model.layoutTitle = layout.title;                              // assign new structure name

                while (counter < columns.length) {
                    counter = fillLayout(model, columns, counter);
                }
            }

            function createConfiguration(type) {
                var cfg = {};
                var config = dashboardData.getModules()[type].config;
                if (config) {
                    cfg = angular.copy(config);
                }
                return cfg;
            }

            /**
             * Find first module column in model.
             *
             * @param dashboard model
             */
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

            /**
             * Adds the module to first column of the model.
             *
             * @param dashboard model
             * @param module to add to model
             */
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

            return {
                replace: true,
                restrict: 'EA',
                transclude: false,
                scope: {
                    layoutTitle: '@',
                    name: '@',
                    model: '='
                },
                controller: function ($scope) {

                    console.log('===========structure=======>', $scope.layoutTitle);

                    var model = {};
                    // var structure = {};
                    var layoutTitle = {};
                    var name = $scope.name;

                    $scope.$on(platformParameters.events.ADF_WIDGET_DELETED_EVENT, function (event) {
                        $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                    });

                    $scope.$on(platformParameters.events.ADF_WIDGET_TITLE_CHANGED_EVENT, function (event) {
                        $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                    });

                    $scope.$on(platformParameters.events.ADF_WIDGET_MOVED_EVENT, function (event) {
                        $scope.$emit(platformParameters.events.ADF_DASHBOARD_CHANGED_EVENT, $scope.name, $scope.model);
                    });

                    $scope.$watch('model', function (oldVal, newVal) {

                        // has model changed or is the model attribute not set
                        if (newVal !== null || (oldVal === null && newVal === null)) {
                            model = $scope.model;
                            if (!model || !model.rows) {
                                layoutTitle = $scope.layoutTitle;
                                layoutTitle = dashboardData.getLayouts()[layoutTitle];
                                if (layoutTitle) {
                                    if (model) {
                                        model.rows = angular.copy(layoutTitle).rows;
                                    } else {
                                        model = angular.copy(layoutTitle);
                                    }
                                    model.layoutTitle = layoutTitle;
                                } else {
                                    $log.error('could not find layoutTitle ' + layoutTitle);
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

                    $scope.toggleEditMode = function () {
                        $scope.editMode = !$scope.editMode;
                        if ($scope.editMode) {
                            $scope.modelCopy = angular.copy($scope.model, {});
                        }
                    };

                    $scope.$on(platformParameters.events.ADF_TOGGLE_EDIT_MODE_EVENT, function (event) {
                        $scope.toggleEditMode();
                        $scope.$emit(platformParameters.events.ADF_TOGGLE_EDIT_MODE_RESPONSE_EVENT, $scope.editMode);
                    });

                    // edit dashboard settings
                    $scope.editDashboardDialog = function () {
                        var editDashboardScope = $scope.$new();
                        console.log('=======0=======>', editDashboardScope.model.layoutTitle);

                        editDashboardScope.pageDetails = {
                            entity: {
                                page: {
                                    title: model.title,
                                    description: model.description,
                                    layout: {
                                        id: 2
                                    }
                                }
                            },
                            layouts: dashboardData.getLayouts(),
                            saveForm: saveForm,
                            changeLayout: changeLayoutOnEditPage
                        };

                        Layout.query()
                            .$promise
                            .then(onQueryResult);

                        function onQueryResult(layouts) {
                            console.log('--layouts--->', layouts);
                            editDashboardScope.pageDetails.layouts = layouts;
                        }


                        //editDashboardScope.structures = dashboard.structures;
                        var instance = $aside.open({
                            scope: editDashboardScope,
                            placement: 'left',
                            templateUrl: '/application/modules/page/html/add.html',
                            size: 'md',
                            backdrop: 'static'
                        });

                        function changeLayoutOnEditPage(layout) {
                            $log.info('change layout to ', layout);
                            changeLayout(model, layout);
                            $rootScope.$broadcast(platformParameters.events.ADF_STRUCTURE_CHANGED_EVENT, layout);
                        }

                        function saveForm() {

                            //if (!editDashboardScope.copy.title) {
                            //   $log.log('Form is invalid and could not be saved.');
                            //   editDashboardScope.$broadcast('show-errors-check-validity');
                            //  return;
                            //}
                            // copy the new title back to the model
                            model.title = editDashboardScope.pageDetails.entity.page.title;
                            //FIXME - doesn't work
                            model.description = editDashboardScope.pageDetails.entity.page.description;

                            // close modal and destroy the scope
                            instance.dismiss();
                            editDashboardScope.$destroy();
                            applicationNavigationItems.editSidebarItem($stateParams.id, model.title);
                            $scope.$emit(platformParameters.events.PAGE_CHANGED_EVENT, name, model);
                        }

                        editDashboardScope.cancelDialog = function () {
                            // close modal and destroy the scope
                            instance.dismiss();
                            editDashboardScope.$destroy();
                        };
                    };

                    $scope.$on(platformParameters.events.ADF_EDIT_DASHBOARD_EVENT, function (event) {
                        $scope.editDashboardDialog();
                    });

                    // add module dialog
                    $scope.addModuleDialog = function () {
                        var addScope = $scope.$new();
                        var model = $scope.model;
                        var modules = {};

                        angular.forEach(dashboardData.getModules(), function (module, type) {
                            if (module.serviceId != null) {
                                modules[type] = module;
                            }
                        });

                        addScope.modules = modules;
                        var opts = {
                            scope: addScope,
                            placement: 'left',
                            templateUrl: 'application/modules/dashboard/html/module-add.html',
                            size: 'sx',
                            backdrop: 'static'
                        };
                        var instance = $aside.open(opts);
                        addScope.addModule = function (moduleType, module) {
                            var w = {
                                id: module.id,
                                type: moduleType,
                                title: module.title,
                                moduleType: module.moduleType,
                                config: createConfiguration(moduleType),
                                wid: dashboardData.id()
                            };
                            addNewModuleToModel(model, w);
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
                        $scope.addModuleDialog();
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
                },
                templateUrl: 'application/modules/dashboard/html/dashboard.html'
            };
        }]);