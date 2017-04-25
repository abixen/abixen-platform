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
        .module('platformFileDataModule')
        .controller('FileDataTableController', FileDataTableController);

    FileDataTableController.$inject = [
        '$scope',
        '$log'
    ];

    function FileDataTableController($scope, $log) {
        $log.log('FileDataTableController');

        var fileDataTable = this;
        fileDataTable.options = undefined;
        fileDataTable.data = undefined;
        fileDataTable.renderTable = false;
        fileDataTable.fileColumns = [];
        fileDataTable.gridData = [];

        $scope.$on('GridDataUpdated', function (event, data) {
            if (data !== undefined && data !== [] && data.length > 0) {
                fileDataTable.gridData = data;
                fileDataTable.fileColumns = [];
                Object.keys(data[0]).forEach(function (column) {
                    if (column !== undefined && column !== null && column !== '' && column !== '$$hashKey') {
                        fileDataTable.fileColumns.push({name: column, selected: true});
                    }
                });
                if (fileDataTable.renderTable !== false) {
                    fileDataTable.listGridConfig.setData(data);
                }else {
                    fileDataTable.renderTable = true;
                }
            } else {
                fileDataTable.fileColumns = [];
                if (fileDataTable.listGridConfig.tableReady) {
                    fileDataTable.listGridConfig.setData([]);
                }
            }
        });

        angular.extend(fileDataTable, new AbstractListGridController(null,
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
            fileDataTable.fileColumns.forEach(function (column) {
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
            fileDataTable.listGridConfig.setData(fileDataTable.gridData);
        }
    }
})();