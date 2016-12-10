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
        .module('platformChartModule')
        .controller('ChartModuleInitController', ChartModuleInitController);

    ChartModuleInitController.$inject = [
        '$scope',
        '$http',
        '$log',
        'ChartModuleInit',
        'CharDataPreview'
    ];

    function ChartModuleInitController($scope, $http, $log, ChartModuleInit, CharDataPreview) {
        $log.log('ChartModuleInitController');

        $scope.moduleId = null;

        $scope.showConfigurationWizard = function () {
            $scope.subview = 'configuration';
        };

        $scope.showChart = function () {
            $scope.subview = 'chart';
        };

        $scope.$on(platformParameters.events.RELOAD_MODULE, function (event, id, viewMode) {
            $log.log('RELOAD MODULE EVENT', event, id, viewMode);

            $scope.moduleId = id;

            $scope.$emit(platformParameters.events.START_REQUEST);
            ChartModuleInit.get({id: id}, function (data) {
                $log.log('ChartModuleInit has been got: ', data);
                if (viewMode == 'view') {
                    $scope.subview = 'chart';
                } else if (viewMode == 'edit') {
                    $scope.subview = 'configuration';
                } else {
                    $scope.subview = 'chart';
                }

                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }, function (error) {
                $scope.$emit(platformParameters.events.STOP_REQUEST);
                if (error.status == 401) {
                    $scope.$emit(platformParameters.events.MODULE_UNAUTHENTICATED);
                } else if (error.status == 403) {
                    $scope.$emit(platformParameters.events.MODULE_FORBIDDEN);
                }
            });
        });

        $scope.$on('CONFIGURATION_MODE', function (event, id) {
            $log.log('CONFIGURATION_MODE EVENT', event, id)
            $scope.subview = 'configuration';
            $scope.$emit(platformParameters.events.CONFIGURATION_MODE_READY);
        });

        $scope.$on('VIEW_MODE', function (event, id) {
            $log.log('VIEW_MODE EVENT', event, id)
            $scope.subview = 'chart';
            $scope.$emit(platformParameters.events.VIEW_MODE_READY);
        });

        $scope.$emit(platformParameters.events.MODULE_READY);
    }
})();