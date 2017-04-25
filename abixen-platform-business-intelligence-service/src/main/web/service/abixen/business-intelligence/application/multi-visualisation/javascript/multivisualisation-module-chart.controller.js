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
        'moduleResponseErrorHandler',
        'multivisualisationDisplayingDataRules'
    ];

    function MultivisualisationModuleChartController($scope, $log, ChartModuleConfiguration, CharData, dataChartAdapter, moduleResponseErrorHandler, multivisualisationDisplayingDataRules) {
        $log.log('MultivisualisationModuleController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        var multivisualisationModuleChart = this;
        multivisualisationModuleChart.options = undefined;
        multivisualisationModuleChart.data = undefined;

        var SHOW_SUBVIEW_TABLE_EVENT = 'SHOW_SUBVIEW_TABLE_EVENT';
        var SHOW_SUBVIEW_CHART_EVENT = 'SHOW_SUBVIEW_CHART_EVENT';

        $scope.$on(platformParameters.events.REDRAW_MODULE, onRedrawModule);

        if ($scope.moduleId) {
            $scope.$emit(platformParameters.events.START_REQUEST);

            ChartModuleConfiguration.get({id: $scope.moduleId})
                .$promise
                .then(onGetResult, onGetError);
        }

        function onGetResult(moduleConfiguration) {
            if(!moduleConfiguration.id){
                $scope.$emit(platformParameters.events.STOP_REQUEST);
                $scope.$emit(platformParameters.events.MODULE_CONFIGURATION_MISSING);
                return;
            }

            if (moduleConfiguration.chartType === 'TABLE') {
                $scope.$emit(SHOW_SUBVIEW_TABLE_EVENT);
            } else {
                CharData.query({}, moduleConfiguration)
                    .$promise
                    .then(onQueryResult, onQueryError);

                registerSubviewChartIcons(moduleConfiguration.chartType);
            }
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

        function onRedrawModule() {
            multivisualisationModuleChart.api.update();
        }

        function registerSubviewChartIcons(chartType) {
            if (multivisualisationDisplayingDataRules.isTableViewAvailable(chartType)) {
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

    }
})();