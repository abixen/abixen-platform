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
        .module('platformDatabaseDataSourceModule')
        .controller('DataBaseDataSourceTableController', DataBaseDataSourceTableController);

    DataBaseDataSourceTableController.$inject = [
        '$scope',
        '$log'
    ];

    function DataBaseDataSourceTableController($scope, $log) {
        $log.log('DataBaseDataSourceTableController');

        var dataBaseDataSourceTable = this;
        dataBaseDataSourceTable.options = undefined;
        dataBaseDataSourceTable.data = undefined;
        dataBaseDataSourceTable.renderTable = false;
        dataBaseDataSourceTable.fileColumns = [];
        dataBaseDataSourceTable.gridData = [];

        $scope.$on('DatabaseDataSourceDataUpdated', function (event, data) {
            $log.debug('DatabaseDataSourceDataUpdated - data: ', data.length);
            if (data !== undefined && data !== [] && data.length > 0) {
                dataBaseDataSourceTable.gridData = data;
                dataBaseDataSourceTable.fileColumns = [];
                Object.keys(data[0]).forEach(function (column) {
                    if (column !== undefined && column !== null && column !== '' && column !== '$$hashKey') {
                        dataBaseDataSourceTable.fileColumns.push({name: column, selected: true});
                    }
                });
                if (dataBaseDataSourceTable.renderTable !== false) {
                    dataBaseDataSourceTable.listGridConfig.setData(data);
                }else {
                    dataBaseDataSourceTable.renderTable = true;
                }
            } else {
                if (dataBaseDataSourceTable.renderTable !== false) {
                    dataBaseDataSourceTable.listGridConfig.setData([]);
                }
            }
        });

        angular.extend(dataBaseDataSourceTable, new AbstractListGridController(null,
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
            dataBaseDataSourceTable.fileColumns.forEach(function (column) {
                columns.push({
                    field: column.name + ".value",
                    name: column.name,
                    cellClass: 'cell-align-right',
                    enableSorting: false,
                    visible: column.selected
                });
            });
            return columns;
        }

        function onTableReady() {
            dataBaseDataSourceTable.listGridConfig.setData(dataBaseDataSourceTable.gridData);
        }
    }
})();