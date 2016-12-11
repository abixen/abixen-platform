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
        .controller('MultivisualisationModuleTableController', MultivisualisationModuleTableController);

    MultivisualisationModuleTableController.$inject = [
        '$scope',
        '$log',
        'ChartModuleConfiguration',
        'CharData',
        'dataChartAdapter',
        'moduleResponseErrorHandler'
    ];

    function MultivisualisationModuleTableController($scope, $log, ChartModuleConfiguration, CharData, dataChartAdapter, moduleResponseErrorHandler) {
        $log.log('MultivisualisationModuleController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        var multivisualisationModuleTable = this;
        multivisualisationModuleTable.options = undefined;
        multivisualisationModuleTable.data = undefined;
        multivisualisationModuleTable.renderTable = false;

        var SHOW_SUBVIEW_CHART_EVENT = 'SHOW_SUBVIEW_CHART_EVENT';

        if ($scope.moduleId) {
            $scope.$emit(platformParameters.events.START_REQUEST);

            ChartModuleConfiguration.get({id: $scope.moduleId})
                .$promise
                .then(onGetResult, onGetError);
        }

        function onGetResult(moduleConfiguration) {
            multivisualisationModuleTable.renderTable = true;

            angular.extend(multivisualisationModuleTable, new AbstractListGridController(CharData,
                {
                    getTableColumns: getTableColumns,
                    dataProviderType: 'list',
                    payloadFilter: moduleConfiguration,
                    onGetDataResult: onGetDataResult,
                    onGetDataError: onGetDataError
                }
            ));

            registerSubviewTableIcons(moduleConfiguration.chartType);

            function getTableColumns() {
                var columns = [
                    {
                        field: moduleConfiguration.dataSetChart.domainXSeriesColumn.dataSourceColumn.name + '.value',
                        name: 'Domain',
                        cellClass: 'cell-align-right',
                        enableSorting: false
                    }
                ];

                for (var i = 0; i < moduleConfiguration.dataSetChart.dataSetSeries.length; i++) {
                    var column = {
                        field: moduleConfiguration.dataSetChart.dataSetSeries[i].valueSeriesColumn.dataSourceColumn.name + '.value',
                        name: moduleConfiguration.dataSetChart.dataSetSeries[i].name,
                        cellClass: 'cell-align-right',
                        enableSorting: false
                    };
                    columns.push(column);
                }
                return columns;
            }

            function onGetDataResult(data) {
                $scope.$emit(platformParameters.events.STOP_REQUEST);
            }

            function onGetDataError(error) {
                moduleResponseErrorHandler.handle(error, $scope);
            }
        }

        function onGetError(error) {
            moduleResponseErrorHandler.handle(error, $scope);
        }

        function registerSubviewTableIcons(chartType) {
            if (isChartViewAvailable(chartType)) {
                var icons = [
                    {
                        iconClass: 'fa fa-line-chart',
                        event: SHOW_SUBVIEW_CHART_EVENT,
                        title: 'Show chart view'
                    }
                ];
                registerSubviewTableIconsHelper(icons);
            } else {
                registerSubviewTableIconsHelper([]);
            }
        }

        function registerSubviewTableIconsHelper(icons) {
            $scope.$emit(platformParameters.events.REGISTER_MODULE_CONTROL_ICONS, icons);
        }

        function isChartViewAvailable(chartType) {
            switch (chartType) {
                case 'LINE_TABLE':
                    return true;
                case 'PIE_TABLE':
                    return true;
                case 'MULTI_BAR_TABLE':
                    return true;
                case 'MULTI_COLUMN_TABLE':
                    return true;
                case 'STACKED_AREA_TABLE':
                    return true;
                case 'DONUT_TABLE':
                    return true;
                case 'DISCRETE_COLUMN_TABLE':
                    return true;
                case 'HISTORICAL_COLUMN_TABLE':
                    return true;
                case 'CUMULATIVE_LINE_TABLE':
                    return true;
                default:
                    return false;
            }
        }
    }
})();