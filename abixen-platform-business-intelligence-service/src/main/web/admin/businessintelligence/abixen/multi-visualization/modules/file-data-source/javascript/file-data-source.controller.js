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
        .controller('FileDataSourceController', FileDataSourceController);

    FileDataSourceController.$inject = [
        '$scope',
        '$http',
        '$log',
        'uiGridConstants',
        'FileDataSource',
        'gridFilter',
        'applicationNavigationItems',
        '$state'
    ];


       function FileDataSourceController($scope, $http, $log, uiGridConstants, FileDataSource, gridFilter, applicationNavigationItems, $state) {
            $log.log('FileDataSourceListController');

            angular.extend(this, new AbstractCrudListController($scope, $http, $log, uiGridConstants, FileDataSource, gridFilter));

            $scope.entityListGrid.columnDefs = [
                {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'name', pinnedLeft: true, width: 200},
                {field: 'createdBy.username', name: 'Created By', width: 200},
                {
                    field: 'createdDate',
                    width: 200,
                    cellFilter: 'date:\'' + platformParameters.formats.DATE_TIME_FORMAT + '\''
                },
                {field: 'lastModifiedBy.username', name: 'Last Modified By', width: 200},
                {
                    field: 'lastModifiedDate',
                    width: 200,
                    cellFilter: 'date:\'' + platformParameters.formats.DATE_TIME_FORMAT + '\''
                }
            ];

            $scope.query = {
                and: [
                    {
                        name: 'title',
                        operation: '=',
                        value: 'Page View'
                    }
                ]
            };

            $scope.filterCriteria = {
                page: 0,
                size: 20,
                sort: 'id,asc',
                gridFilterParameters: []
            };

            var newDataSourceButton = {
                id: 1,
                styleClass: 'btn add-new-button',
                faIcon: 'fa fa-plus',
                title: 'New File Data Source',
                onClick: function () {
                    $state.go('application.multiVisualization.modules.fileDataSource.add');
                },
                visible: true,
                disabled: false
            };

            applicationNavigationItems.setTopbarItem(newDataSourceButton);

            $scope.read();
        }
})();