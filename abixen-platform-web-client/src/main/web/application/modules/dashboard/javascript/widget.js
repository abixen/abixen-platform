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

        function preLink($scope) {
            var definition = $scope.definition;

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

                $scope.enableConfigureMode = function () {
                    $scope.$broadcast(platformParameters.events.CONFIGURATION_MODE, $scope.definition.id);
                };

                $scope.exitConfigurationMode = function () {
                    $scope.$broadcast(platformParameters.events.VIEW_MODE, $scope.definition.id);
                };

                $scope.sendModuleEvent = function (event) {
                    $scope.$broadcast(event);
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
                    loaderCounter++;
                    $scope.widgetState.isLoading = true;
                });
                $scope.$on(platformParameters.events.HIDE_LOADER, function () {
                    if (--loaderCounter === 0) {
                        $scope.widgetState.isLoading = false;
                    }
                });
                $scope.$on(platformParameters.events.SHOW_PERMISSION_DENIED_TO_MODULE, function () {
                    $scope.widgetState.permissionDenied = true;
                });
                $scope.$on(platformParameters.events.SHOW_EXIT_CONFIGURATION_MODE_ICON, function () {
                    $scope.widgetState.configurationMode = true;
                });
                $scope.$on(platformParameters.events.SHOW_CONFIGURATION_MODE_ICON, function () {
                    $scope.widgetState.configurationMode = false;
                });
                $scope.$on(platformParameters.events.UPDATE_MODULE_CONTROL_ICONS, function (event, icons) {
                    $scope.widgetState.moduleIcons = icons;
                });

                $scope.toggleFullScreenMode = function () {
                    $scope.widgetState.fullScreenMode = !$scope.widgetState.fullScreenMode;
                    $scope.$emit('FULL_SCREEN_MODE', $scope.definition.wid, $scope.widgetState.fullScreenMode);
                };

                $scope.onWidgetTitleChanged = function () {
                    $scope.$emit(platformParameters.events.ADF_WIDGET_TITLE_CHANGED_EVENT);
                };

                $scope.$on(platformParameters.events.MODULE_TEMPORARY_UNAVAILABLE, function (event, args) {
                    $scope.widgetState.notAvailable = true;
                });
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