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

'use strict';


function MultivisualisationModuleAbstractTableController(extendedController, $log, CharData, moduleResponseErrorHandler, $scope, config) {
    $log.log('MultivisualisationModuleController');

    var multivisualisationModuleTable = extendedController;
    multivisualisationModuleTable.chartConfiguration = angular.isDefined(config) ? angular.isDefined(config.chartConfiguration) ? config.chartConfiguration : null : null;

    if (multivisualisationModuleTable.chartConfiguration !== null) {
        angular.extend(multivisualisationModuleTable, new AbstractListGridController(CharData,
            {
                getTableColumns: getTableColumns,
                dataProviderType: 'list',
                payloadFilter: multivisualisationModuleTable.chartConfiguration,
                onGetDataResult: onGetDataResult,
                onGetDataError: onGetDataError
            }
        ));
    }

    function getTableColumns() {
        var columns = [
            {
                field: multivisualisationModuleTable.chartConfiguration.dataSet.domainXSeriesColumn.dataSourceColumn.name + '.value',
                name: 'Domain',
                cellClass: 'cell-align-right',
                enableSorting: false
            }
        ];

        for (var i = 0; i < multivisualisationModuleTable.chartConfiguration.dataSet.dataSetSeries.length; i++) {
            var column = {
                field: multivisualisationModuleTable.chartConfiguration.dataSet.dataSetSeries[i].valueSeriesColumn.dataSourceColumn.name + '.value',
                name: multivisualisationModuleTable.chartConfiguration.dataSet.dataSetSeries[i].name,
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
