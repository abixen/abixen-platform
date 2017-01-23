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

        var fileDataSourceTable = this;
        fileDataSourceTable.options = undefined;
        fileDataSourceTable.data = undefined;
        fileDataSourceTable.renderTable = false;
        fileDataSourceTable.fileColumns = [];
        fileDataSourceTable.gridData = [];

        $scope.$on('GridDataUpdated', function (event, data) {
            $log.debug('GridDataUpdated - data: ', data.length);
            if (data !== undefined && data !== [] && data.length > 0) {
                fileDataSourceTable.gridData = data;
                Object.keys(data[0]).forEach(function (column) {
                    if (column !== undefined && column !== null && column !== '' && column !== '$$hashKey') {
                        fileDataSourceTable.fileColumns.push({name: column, selected: true});
                    }
                });
                if (fileDataSourceTable.renderTable !== false) {
                    fileDataSourceTable.listGridConfig.setData(data);
                }else {
                    fileDataSourceTable.renderTable = true;
                }
            }
        });

        angular.extend(fileDataSourceTable, new AbstractListGridController(null,
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
            fileDataSourceTable.fileColumns.forEach(function (column) {
                columns.push({
                    field: column.name,
                    name: column.name,
                    cellClass: 'cell-align-right',
                    enableSorting: false,
                    visible: column.selected
                });
            });
            return columns;
        }

        function onTableReady() {
            fileDataSourceTable.listGridConfig.setData(fileDataSourceTable.gridData);
        }
    }
})();