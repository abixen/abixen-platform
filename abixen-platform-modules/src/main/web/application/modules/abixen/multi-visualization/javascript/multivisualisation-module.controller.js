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
        .controller('ChartModuleController', ChartModuleController);

    ChartModuleController.$inject = [
        '$scope',
        '$http',
        '$log',
        'ChartModuleConfiguration',
        'CharData',
        'dataChartAdapter'
    ];

    function ChartModuleController($scope, $http, $log, ChartModuleConfiguration, CharData, dataChartAdapter) {
        $log.log('ChartModuleController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        $scope.moduleConfiguration = {};

        var chartParams = null;

        $scope.$emit(platformParameters.events.START_REQUEST);
        if ($scope.moduleId) {
            ChartModuleConfiguration.get({id: $scope.moduleId}, function (data) {
                $scope.moduleConfiguration = data;
                $log.log('ChartModuleConfiguration has been got: ', $scope.moduleConfiguration);

                CharData.query({}, $scope.moduleConfiguration, function (data) {
                    $log.log('CharData.query: ', data);
                    chartParams = dataChartAdapter.convertTo($scope.moduleConfiguration, data);

                    if (chartParams != null) {
                        $scope.options = chartParams.options;
                        $scope.data = chartParams.data;
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
            }, function (error) {
                $scope.$emit(platformParameters.events.STOP_REQUEST);
                if (error.status == 401) {
                    $scope.$emit(platformParameters.events.MODULE_UNAUTHENTICATED);
                } else if (error.status == 403) {
                    $scope.$emit(platformParameters.events.MODULE_FORBIDDEN);
                }
            });
        }

    }
})();