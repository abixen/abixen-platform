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
        .controller('ChartModulePreviewController', ChartModulePreviewController);

    ChartModulePreviewController.$inject = [
        '$scope',
        '$http',
        '$log',
        'dataChartAdapter',
        'CharData'
    ];

    function ChartModulePreviewController($scope, $http, $log, dataChartAdapter, CharData) {
        $log.log('ChartModulePreviewController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);
        $log.log('$scope.initWizardStep.idSelected: ' + $scope.initWizardStep.idSelected);

        var chartParams = null;

        $log.log('CharData.query started ');
        $scope.$emit(platformParameters.events.START_REQUEST);
        CharData.query({}, $scope.chartConfiguration, function (data) {
            $log.log('CharData.query: ', data);
            chartParams = dataChartAdapter.convertTo($scope.chartConfiguration, data);

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
    }
})();