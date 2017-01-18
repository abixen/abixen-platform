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
        '$log',
        'dataChartAdapter',
        'CharData',
        'moduleResponseErrorHandler'
    ];

    function ChartModulePreviewController($scope, $log, dataChartAdapter, CharData, moduleResponseErrorHandler) {
        $log.log('ChartModulePreviewController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);
        $log.log('$scope.initWizardStep.idSelected: ' + $scope.initWizardStep.idSelected);
        $log.log('$scope.initWizardStep: ', $scope.initWizardStep);

        var chartModulePreview = this;
        chartModulePreview.options = undefined;
        chartModulePreview.data = undefined;
        chartModulePreview.previewType = $scope.initWizardStep.isChart()? 'CHART' : 'TABLE';
        chartModulePreview.setPreviewType = setPreviewType;

        $scope.$emit(platformParameters.events.START_REQUEST);
        CharData.query({}, $scope.chartConfiguration)
            .$promise
            .then(onQueryResult, onQueryError);

        function onQueryResult(data) {
            $log.log('CharData.query: ', data);
            var chartParams = dataChartAdapter.convertTo($scope.chartConfiguration, data);

            if (chartParams != null) {
                chartModulePreview.options = chartParams.options;
                chartModulePreview.data = chartParams.data;
            }
            $scope.$emit(platformParameters.events.STOP_REQUEST);
        }

        function onQueryError(error) {
            moduleResponseErrorHandler.handle(error, $scope);
        }

        function setPreviewType(type) {
            $log.log('setPreviewType', type);
           chartModulePreview.previewType = type;
        }
    }
})();