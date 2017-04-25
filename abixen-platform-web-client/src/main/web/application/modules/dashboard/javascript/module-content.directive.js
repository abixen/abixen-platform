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
        .module('platformDashboardModule')
        .directive('moduleContent', moduleContent);

    moduleContent.$inject = ['moduleContentUtils'];

    function moduleContent(moduleContentUtils) {
        return {
            replace: true,
            restrict: 'EA',
            transclude: false,
            scope: {
                model: '=',
                content: '='
            },
            link: link
        };

        function link($scope, $element) {
            var currentScope = moduleContentUtils.compileModule($scope, $element, null);
            $scope.$on('moduleConfigChanged', function () {
                currentScope = moduleContentUtils.compileModule($scope, $element, currentScope);
            });
            $scope.$on('moduleReload', function () {
                currentScope = moduleContentUtils.compileModule($scope, $element, currentScope);
            });

            $scope.$on(platformParameters.events.MODULE_READY, function () {
                if ($scope.model.id == null) {
                    return;
                }
                $scope.$broadcast(platformParameters.events.RELOAD_MODULE, $scope.model.id);
            });

            $scope.$on(platformParameters.events.MODULE_FORBIDDEN, function () {
                $scope.$emit(platformParameters.events.SHOW_PERMISSION_DENIED_TO_MODULE);
            });

            $scope.$on(platformParameters.events.MODULE_CONFIGURATION_MISSING, function () {
                $scope.$emit(platformParameters.events.SHOW_MODULE_CONFIGURATION_MISSING);
            });

            $scope.$on(platformParameters.events.START_REQUEST, function () {
                $scope.$emit(platformParameters.events.SHOW_LOADER);
            });

            $scope.$on(platformParameters.events.STOP_REQUEST, function () {
                $scope.$emit(platformParameters.events.HIDE_LOADER);
            });

            $scope.$on(platformParameters.events.CONFIGURATION_MODE_READY, function () {
                $scope.$emit(platformParameters.events.SHOW_EXIT_CONFIGURATION_MODE_ICON);
            });

            $scope.$on(platformParameters.events.VIEW_MODE_READY, function () {
                $scope.$emit(platformParameters.events.SHOW_CONFIGURATION_MODE_ICON);
            });

            $scope.$on(platformParameters.events.REGISTER_MODULE_CONTROL_ICONS, function (event, icons) {
                $scope.$emit(platformParameters.events.UPDATE_MODULE_CONTROL_ICONS, icons);
            });

            $scope.$watch('model.id', function (newId, oldId) {
                if (oldId == null && newId != null) {
                    $scope.$broadcast(platformParameters.events.RELOAD_MODULE, newId);
                }
            }, true);
        }
    }

})();