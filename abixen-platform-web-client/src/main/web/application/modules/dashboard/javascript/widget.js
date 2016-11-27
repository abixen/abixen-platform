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

'use strict';

angular.module('adf')
    .directive('adfWidget', ['$log', '$uibModal', '$rootScope', 'dashboard', 'adfTemplatePath', 'modalWindow', function ($log, $uibModal, $rootScope, dashboard, adfTemplatePath, modalWindow) {

        $log.log('adf widget !!!!!!!!!');


        function preLink($scope) {
            var definition = $scope.definition;

            $log.log('preLink adf widget !!!!!!!!!', definition);

            if (definition) {
                var w = dashboard.widgets[definition.type];
                if (w) {
                    // pass title
                    if (!definition.title) {
                        definition.title = w.title;
                    }

                    if (!definition.titleTemplateUrl) {
                        definition.titleTemplateUrl = adfTemplatePath + 'widget-title.html';
                    }

                    // set id for sortable
                    if (!definition.wid) {
                        definition.wid = dashboard.id();
                    }

                    // pass copy of widget to scope
                    $scope.widget = angular.copy(w);

                    // create config object
                    var config = definition.config;
                    if (config) {
                        if (angular.isString(config)) {
                            config = angular.fromJson(config);
                        }
                    } else {
                        config = {};
                    }

                    // pass config to scope
                    $scope.config = config;

                    // collapse exposed $scope.widgetState property
                    if (!$scope.widgetState) {
                        $scope.widgetState = {};
                        $scope.widgetState.isCollapsed = false;
                        $scope.widgetState.isLoading = false;
                        $scope.widgetState.permissionDenied = false;
                    }

                } else {
                    $log.warn('could not find widget ' + definition.type);
                }
            } else {
                $log.debug('definition not specified, widget was probably removed');
            }
        }

        function postLink($scope, $element) {
            if ($scope.definition) {

                var removeWidget = function () {
                    var column = $scope.col;
                    if (column) {
                        var index = column.widgets.indexOf($scope.definition);
                        if (index >= 0) {
                            column.widgets.splice(index, 1);
                        }
                    }
                    $element.remove();
                    $scope.$emit(platformParameters.events.ADF_WIDGET_DELETED_EVENT);
                };

                $scope.remove = function () {
                    modalWindow.openConfirmWindow('Delete module?', 'The module will be deleted permanently. Are you sure you want to perform this operation?', 'warning', removeWidget);
                };

                $scope.reload = function () {
                    $scope.$broadcast('widgetReload');
                };

                $scope.edit = function () {
                    $scope.$broadcast('CONFIGURATION_MODE', $scope.definition.id);
                };
            } else {
                $log.debug('widget not found');
            }
        }

        return {
            replace: true,
            restrict: 'EA',
            transclude: false,
            templateUrl: adfTemplatePath + 'widget.html',
            scope: {
                definition: '=',
                col: '=column',
                editMode: '=',
                options: '=',
                widgetState: '='
            },

            controller: function ($scope) {
                var loaderCounter = 0;

                $scope.$on("adfDashboardCollapseExapand", function (event, args) {
                    $scope.widgetState.isCollapsed = args.collapseExpandStatus;
                });

                $scope.$on(platformParameters.events.SHOW_LOADER, function () {
                    $log.log('SHOW_LOADER');
                    loaderCounter++;
                    $scope.widgetState.isLoading = true;
                });
                $scope.$on(platformParameters.events.HIDE_LOADER, function () {
                    $log.log('HIDE_LOADER');
                    if (--loaderCounter === 0) {
                        $scope.widgetState.isLoading = false;
                        $log.log('HIDE');
                    }
                });
                $scope.$on(platformParameters.events.SHOW_PERMISSION_DENIED_TO_MODULE, function () {
                    $log.log('SHOW_PERMISSION_DENIED_TO_MODULE');
                    $scope.widgetState.permissionDenied = true;
                });

                $scope.toggleFullScreenMode = function () {
                    $scope.widgetState.fullScreenMode = !$scope.widgetState.fullScreenMode;
                    $scope.$emit('FULL_SCREEN_MODE', $scope.definition.wid, $scope.widgetState.fullScreenMode);
                };
            },

            compile: function compile() {

                /**
                 * use pre link, because link of widget-content
                 * is executed before post link widget
                 */
                return {
                    pre: preLink,
                    post: postLink
                };
            }
        };

    }]);
