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
        .module('platformListGridModule')
        .directive('platformListGrid', platformListGridDirective);

    platformListGridDirective.$inject = [];

    function platformListGridDirective() {
        return {
            restrict: 'EA',
            scope: {
                listGridConfig: '=',
                name: '@',
                loadAllRecords: '=',
                title: '@'
            },
            templateUrl: '/common/list-grid/html/list-grid.template.html',
            link: link,
            controller: ListGridController,
            controllerAs: 'listGrid',
            bindToController: true
        };

        function link(scope) {
        }
    }

    ListGridController.$inject = ['$scope'];

    function ListGridController(scope) {

        var listGrid = this;
        var MAX_INT = 2147483647;

        listGrid.loadMore = loadMore;
        listGrid.existsAndNotEmpty = existsAndNotEmpty;

        if (listGrid.loadAllRecords) {
            listGrid.listGridConfig.pageSize = MAX_INT;
        }

        listGrid.listGrid = {
            data: [],
            enableSorting: true,
            exporterMenuCsv: true,
            enableGridMenu: true,
            enableFiltering: true,
            useExternalSorting: true,
            excessColumns: 50,

            onRegisterApi: function (gridApi) {
                scope.gridApi = gridApi;

                scope.gridApi.grid.options.multiSelect = listGrid.listGridConfig.selectType === 'multi';

                scope.gridApi.grid.options.enableFiltering =
                    !angular.isUndefined(
                        listGrid.listGridConfig.enableFiltering) ? listGrid.listGridConfig.enableFiltering : false;

                scope.gridApi.core.on.renderingComplete(scope, function (grid) {
                    listGrid.listGridConfig.tableReady = true;
                    if (listGrid.listGridConfig.onTableReady !== undefined) {
                        listGrid.listGridConfig.onTableReady();
                    }
                });

                scope.gridApi.core.on.sortChanged(scope, function (grid, sortColumns) {


                    if (listGrid.listGrid.data.length === 0) {
                        return;
                    }

                    listGrid.listGridConfig.selectedEntitys = [];
                    listGrid.listGridConfig.selectedEntity = null;

                    listGrid.listGridConfig.pageNumber = 0;

                    var sort = [];

                    angular.forEach(sortColumns, function (sortColumn) {
                        sort.push(sortColumn.name + ',' + sortColumn.sort.direction);
                    });

                    listGrid.listGridConfig.sort = sort;
                    loadMore(true);

                });

                scope.gridApi.selection.on.rowSelectionChanged(scope, function (row) {

                    if (scope.gridApi.grid.options.multiSelect) {

                        if (row.isSelected) {
                            listGrid.listGridConfig.selectedEntities.push(row.entity);
                            if (listGrid.listGridConfig.onRowSelected) {
                                listGrid.listGridConfig.onRowSelected(row);
                            }

                        } else {
                            var index = listGrid.listGridConfig.selectedEntities.indexOf(row.entity);

                            if (index > -1) {
                                listGrid.listGridConfig.selectedEntities.splice(index, 1);
                            }

                            if (listGrid.listGridConfig.onRowUnselected) {
                                listGrid.listGridConfig.onRowUnselected();
                            }
                        }

                    } else {
                        if (row.isSelected) {
                            listGrid.listGridConfig.selectedEntity = row.entity;
                            if (listGrid.listGridConfig.onRowSelected) {
                                listGrid.listGridConfig.onRowSelected(row);
                            }
                        } else {
                            listGrid.listGridConfig.selectedEntity = null;

                            if (listGrid.listGridConfig.onRowUnselected) {
                                listGrid.listGridConfig.onRowUnselected();
                            }
                        }
                    }
                });
                listGrid.listGrid.columnDefs = listGrid.listGridConfig.getTableColumns();

                if (listGrid.listGridConfig.loadOnStart) {
                    listGrid.loadMore(true);
                }
            }
        };

        listGrid.listGridConfig.getListGridData = getListGridData;
        listGrid.listGridConfig.getListGridColumns = getListGridColumns;
        listGrid.listGridConfig.getListGridSelectedRows = getListGridSelectedRows;
        listGrid.listGridConfig.clearSelectedRows = clearSelectedRows;
        listGrid.listGridConfig.refreshGrid = refreshGrid;

        listGrid.listGridConfig.selectRow = function (rowEntity) {
            scope.gridApi.selection.selectRow(rowEntity);
        };

        listGrid.listGridConfig.onDataChanged = function (data, reset) {

            if (reset) {
                listGrid.listGridConfig.clearSelectedRows();
                listGrid.listGrid.columnDefs = listGrid.listGridConfig.getTableColumns();
                listGrid.listGrid.data = data;
            } else {
                listGrid.listGrid.data = listGrid.listGrid.data.concat(data);
            }
            scope.gridApi.grid.modifyRows(listGrid.listGrid.data);
        };

        if (!listGrid.listGridConfig.getTableColumns) {
            throw new Error('Please define getTableColumns() function!');
        }

        function loadMore(reset) {
            listGrid.listGridConfig.getData(reset);
        }

        function getListGridData() {
            return listGrid.listGrid.data;
        }

        function getListGridColumns() {
            return listGrid.listGrid.columnDefs;
        }

        function getListGridSelectedRows() {
            if (scope.gridApi.grid.options.multiSelect) {
                return listGrid.listGridConfig.selectedEntities;
            }else {
                return listGrid.listGridConfig.selectedEntity;
            }
        }

        function clearSelectedRows() {
            if (scope.gridApi.grid.options.multiSelect) {
                return listGrid.listGridConfig.selectedEntities = [];
            }else {
                return listGrid.listGridConfig.selectedEntity = null;
            }
        }

        function refreshGrid() {
            listGrid.listGrid.columnDefs = listGrid.listGridConfig.getTableColumns();
            scope.gridApi.grid.refresh();
        }

        function existsAndNotEmpty(value) {
            return angular.isDefined(value) && value !== null && value !== '';
        }
    }
})();