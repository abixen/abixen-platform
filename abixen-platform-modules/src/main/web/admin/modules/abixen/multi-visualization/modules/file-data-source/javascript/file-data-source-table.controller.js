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
        .module('platformFileDataSourceModule')
        .controller('FileDataSourceTableController', FileDataSourceTableController);

    FileDataSourceTableController.$inject = [
        '$scope',
        '$log'
    ];

    function FileDataSourceTableController($scope, $log) {
        $log.log('FileDataSourceTableController');

        $log.log('$scope.moduleId: ' + $scope.moduleId);

        var fileDataSoruceTable = this;
        fileDataSoruceTable.options = undefined;
        fileDataSoruceTable.data = undefined;
        fileDataSoruceTable.renderTable = false;

        $scope.$watch('gridData', function () {
            if ($scope.gridData !== undefined && $scope.gridData !== [] && $scope.gridData.length > 0) {
                if (fileDataSoruceTable.renderTable === false) {
                    fileDataSoruceTable.renderTable = true;
                }else {
                    fileDataSoruceTable.listGridConfig.setData($scope.gridData);
                }
            }
        });

        angular.extend(fileDataSoruceTable, new AbstractListGridController(null,
            {
                getTableColumns: getTableColumns,
                dataProviderType: 'list',
                loadOnStart: false,
                selectType: 'multi',
                onTableReady: onTableReady

            }
        ));

        function getTableColumns() {
            var columns = [];
            Object.keys($scope.gridData[0]).forEach(function (column) {
                $log.debug("column:", column);
                columns.push({
                    field: column,
                    name: column,
                    cellClass: 'cell-align-right',
                    enableSorting: false
                });
            });
            return columns;
        }

        function onTableReady() {
            fileDataSoruceTable.listGridConfig.setData($scope.gridData);
        }
    }
})();