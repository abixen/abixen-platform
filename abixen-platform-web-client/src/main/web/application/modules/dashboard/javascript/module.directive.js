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
        .directive('platformModule', platformModule);

    platformModule.$inject = [
        '$log',
        'dashboardData',
        'modalWindow'
    ];

    function platformModule($log, dashboardData, modalWindow) {
        function preLink($scope) {

            if ($scope.definition) {
                var module = dashboardData.getModules()[$scope.definition.type];
                if (module) {
                    if (!$scope.definition.title) {
                        $scope.definition.title = module.title;
                    }

                    if (!$scope.definition.titleTemplateUrl) {
                        $scope.definition.titleTemplateUrl = 'application/modules/dashboard/html/module-title.html';
                    }

                    if (!$scope.definition.wid) {
                        $scope.definition.wid = dashboardData.generateId();
                    }

                    $scope.module = angular.copy(module);

                    var config = $scope.definition.config;
                    if (config) {
                        if (angular.isString(config)) {
                            config = angular.fromJson(config);
                        }
                    } else {
                        config = {};
                    }

                    $scope.config = config;

                    if (!$scope.moduleState) {
                        $scope.moduleState = {};
                        $scope.moduleState.isCollapsed = false;
                        $scope.moduleState.isLoading = false;
                        $scope.moduleState.permissionDenied = false;
                        $scope.moduleState.configurationMissing = false;
                        $scope.moduleState.chatShowing = false;
                    }
                } else {
                    $log.warn('Could not find module ' + $scope.definition.type);
                }
            } else {
                $log.debug('Definition not specified, module was probably removed');
            }
        }

        function postLink($scope, $element) {
            if ($scope.definition) {

                var removeModule = function () {
                    var column = $scope.col;
                    if (column) {
                        var index = column.modules.indexOf($scope.definition);
                        if (index >= 0) {
                            column.modules.splice(index, 1);
                        }
                    }
                    $element.remove();
                    $scope.$emit(platformParameters.events.DASHBOARD_MODULE_DELETED_EVENT);
                };

                $scope.remove = function () {
                    modalWindow.openConfirmWindow('Delete module?', 'The module will be deleted permanently. Are you sure you want to perform this operation?', 'warning', removeModule);
                };

                $scope.reload = function () {
                    $scope.$broadcast('moduleReload');
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
                $log.debug('Module not found');
            }
        }

        return {
            replace: true,
            restrict: 'EA',
            transclude: false,
            templateUrl: 'application/modules/dashboard/html/module.html',
            scope: {
                definition: '=',
                col: '=column',
                editMode: '=',
                moduleState: '='
            },

            controller: function ($scope) {
                var loaderCounter = 0;

                $scope.$on('adfDashboardCollapseExapand', function (event, args) {
                    $scope.moduleState.isCollapsed = args.collapseExpandStatus;
                });

                $scope.$on(platformParameters.events.SHOW_LOADER, function () {
                    loaderCounter++;
                    $scope.moduleState.isLoading = true;
                });
                $scope.$on(platformParameters.events.HIDE_LOADER, function () {
                    if (--loaderCounter === 0) {
                        $scope.moduleState.isLoading = false;
                    }
                });
                $scope.$on(platformParameters.events.SHOW_PERMISSION_DENIED_TO_MODULE, function () {
                    $scope.moduleState.permissionDenied = true;
                });
                $scope.$on(platformParameters.events.SHOW_MODULE_CONFIGURATION_MISSING, function () {
                    $scope.moduleState.configurationMissing = true;
                });
                $scope.$on(platformParameters.events.SHOW_EXIT_CONFIGURATION_MODE_ICON, function () {
                    $scope.moduleState.configurationMode = true;
                    $scope.moduleState.configurationMissing = false;
                });
                $scope.$on(platformParameters.events.SHOW_CONFIGURATION_MODE_ICON, function () {
                    $scope.moduleState.configurationMode = false;
                });
                $scope.$on(platformParameters.events.UPDATE_MODULE_CONTROL_ICONS, function (event, icons) {
                    $scope.moduleState.moduleIcons = icons;
                });
                $scope.toggleFullScreenMode = function () {
                    $scope.moduleState.fullScreenMode = !$scope.moduleState.fullScreenMode;

                    function onModeChanged(){
                        $scope.$broadcast(platformParameters.events.REDRAW_MODULE);
                    }

                    $scope.$emit('FULL_SCREEN_MODE', $scope.definition.wid, $scope.moduleState.fullScreenMode, onModeChanged);
                };

                $scope.toggleChat = function () {
                    $scope.moduleState.chatShowing = !$scope.moduleState.chatShowing;
                };

                $scope.onModuleTitleChanged = function () {
                    $scope.$emit(platformParameters.events.DASHBOARD_MODULE_TITLE_CHANGED_EVENT);
                };

                $scope.$on(platformParameters.events.MODULE_TEMPORARY_UNAVAILABLE, function (event, args) {
                    $scope.moduleState.notAvailable = true;
                });
            },

            compile: function compile() {
                return {
                    pre: preLink,
                    post: postLink
                };
            }
        };
    }

})();