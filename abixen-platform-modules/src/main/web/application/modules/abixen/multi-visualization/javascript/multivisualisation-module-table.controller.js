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

            function getTableColumns() {
                var columns = [
                    {
                        field: moduleConfiguration.dataSetChart.domainXSeriesColumn.dataSourceColumn.name + '.value',
                        name: 'Domain',
                        pinnedLeft: false,
                        width: 200
                    }
                ];

                for (var i = 0; i < moduleConfiguration.dataSetChart.dataSetSeries.length; i++) {
                    var column = {
                        field: moduleConfiguration.dataSetChart.dataSetSeries[i].valueSeriesColumn.dataSourceColumn.name + '.value',
                        name: moduleConfiguration.dataSetChart.dataSetSeries[i].name,
                        pinnedLeft: false,
                        width: 200
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
    }
})();