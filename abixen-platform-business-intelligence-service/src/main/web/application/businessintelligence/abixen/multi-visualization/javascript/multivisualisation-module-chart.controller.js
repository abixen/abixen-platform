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
        .controller('MultivisualisationModuleChartController', MultivisualisationModuleChartController);

    MultivisualisationModuleChartController.$inject = [
        '$scope',
        '$log',
        'ChartModuleConfiguration',
        'CharData',
        'dataChartAdapter',
        'moduleResponseErrorHandler'
    ];

    function MultivisualisationModuleChartController($scope, $log, ChartModuleConfiguration, CharData, dataChartAdapter, moduleResponseErrorHandler) {
        $log.log('MultivisualisationModuleController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        var multivisualisationModuleChart = this;
        multivisualisationModuleChart.options = undefined;
        multivisualisationModuleChart.data = undefined;

        var SHOW_SUBVIEW_TABLE_EVENT = 'SHOW_SUBVIEW_TABLE_EVENT';

        if ($scope.moduleId) {
            $scope.$emit(platformParameters.events.START_REQUEST);

            ChartModuleConfiguration.get({id: $scope.moduleId})
                .$promise
                .then(onGetResult, onGetError);
        }

        function onGetResult(moduleConfiguration) {
            CharData.query({}, moduleConfiguration)
                .$promise
                .then(onQueryResult, onQueryError);

            registerSubviewChartIcons(moduleConfiguration.chartType);

            function onQueryResult(data) {
                var chartParams = dataChartAdapter.convertTo(moduleConfiguration, data);

                if (chartParams != null) {
                    multivisualisationModuleChart.options = chartParams.options;
                    multivisualisationModuleChart.data = chartParams.data;
                }
                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }

            function onQueryError(error) {
                moduleResponseErrorHandler.handle(error, $scope);
            }
        }

        function onGetError(error) {
            moduleResponseErrorHandler.handle(error, $scope);
        }

        function registerSubviewChartIcons(chartType) {
            if (isTableViewAvailable(chartType)) {
                var icons = [
                    {
                        iconClass: 'fa fa-table',
                        event: SHOW_SUBVIEW_TABLE_EVENT,
                        title: 'Show table view'
                    }
                ];
                registerSubviewChartIconsHelper(icons);
            } else {
                registerSubviewChartIconsHelper([]);
            }
        }

        function registerSubviewChartIconsHelper(icons) {
            $scope.$emit(platformParameters.events.REGISTER_MODULE_CONTROL_ICONS, icons);
        }

        function isTableViewAvailable(chartType) {
            switch (chartType) {
                case 'LINE':
                    return true;
                case 'PIE':
                    return true;
                case 'MULTI_BAR':
                    return true;
                case 'MULTI_COLUMN':
                    return true;
                case 'STACKED_AREA':
                    return true;
                case 'DONUT':
                    return true;
                case 'DISCRETE_COLUMN':
                    return true;
                case 'HISTORICAL_COLUMN':
                    return true;
                case 'CUMULATIVE_LINE':
                    return true;
                default:
                    return false;
            }
        }
    }
})();