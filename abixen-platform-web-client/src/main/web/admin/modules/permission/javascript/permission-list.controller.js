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
        .module('platformPermissionModule')
        .controller('PermissionListController', PermissionListController);

    PermissionListController.$inject = [
        '$log',
        'Permission',
        'applicationNavigationItems',
        '$state'
    ];

    function PermissionListController($log, Permission, applicationNavigationItems, $state) {
        $log.log('PermissionListController');

        var permissionList = this;

        angular.extend(permissionList, new AbstractListGridController(Permission,
            {
                getTableColumns: getTableColumns
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'title', name: 'Title', pinnedLeft: true, width: 200 /*filter: {term: 'cat|dog&!pink'}*/},
                {field: 'description', name: 'Description', enableSorting: false, width: 200/*, filter: {term: '(x1|x2)&(z1|z2)'}*/},
                {field: 'permissionName', name: 'Permission Name', width: 200},
                {field: 'permissionCategory', name: 'Permission Category', width: 200},
                {field: 'createdBy.username', name: 'Created By', width: 200},
                {
                    field: 'createdDate',
                    name: 'Created Date',
                    width: 200,
                    cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
                },
                {field: 'lastModifiedBy.username', name: 'Last Modified By', width: 200},
                {
                    field: 'lastModifiedDate',
                    name: 'Last Modified Date',
                    width: 200,
                    cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
                }
            ];
        }

        function updateNavigation() {
            applicationNavigationItems.clearTopbarItems();
        }
    }
})();