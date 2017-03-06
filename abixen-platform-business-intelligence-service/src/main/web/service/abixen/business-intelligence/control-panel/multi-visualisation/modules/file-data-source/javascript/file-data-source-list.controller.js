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
        .controller('FileDataSourceListController', FileDataSourceListController);

    FileDataSourceListController.$inject = [
        '$log',
        'FileDataSource',
        'applicationNavigationItems',
        '$state'
    ];

    function FileDataSourceListController($log, FileDataSource, applicationNavigationItems, $state) {
        $log.log('FileDataSourceListController');

        var fileDataSourceList = this;

        angular.extend(fileDataSourceList, new AbstractListGridController(FileDataSource,
            {
                getTableColumns: getTableColumns
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'name', name: 'Name', pinnedLeft: true, width: 200}
            ].concat(getAuditingTableColumns());
        }

        function updateNavigation() {
            var newUserButton = {
                id: 1,
                styleClass: 'btn add-new-button',
                faIcon: 'fa fa-plus',
                title: 'New File Data Source',
                onClick: function () {
                    $state.go('application.multiVisualisation.modules.fileDataSource.add');
                },
                visible: true,
                disabled: false
            };

            applicationNavigationItems.setTopbarItem(newUserButton);
        }
    }
})();