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
        permissionList.searchFields = createSearchFields();
        permissionList.searchFilter = {};

        angular.extend(permissionList, new AbstractListGridController(Permission,
            {
                getTableColumns: getTableColumns,
                filter: permissionList.searchFilter
            }
        ));

        updateNavigation();

        function getTableColumns() {
            return [
                {
                    field: 'id',
                    name: 'Id',
                    pinnedLeft: true,
                    enableColumnResizing: false,
                    enableFiltering: false,
                    width: 50
                },
                {field: 'title', name: 'Title', pinnedLeft: true, width: 200},
                {field: 'permissionGeneralCategory.title', name: 'General Category', pinnedLeft: true, width: 200},
                {field: 'permissionAclClassCategory.title', name: 'ACL Category', pinnedLeft: true, width: 200},
                {field: 'description', name: 'Description', enableSorting: false, width: 200},
                {field: 'permissionName', name: 'Permission Name', width: 200}
            ].concat(getAuditingTableColumns());
        }

        function updateNavigation() {
            applicationNavigationItems.clearTopbarItems();
        }

        function createSearchFields() {
            return [
                {
                    name: 'title',
                    label: 'module.permission.title.label',
                    type: 'input-text'
                },
                {
                    name: 'description',
                    label: 'module.permission.description.label',
                    type: 'input-text'
                }
            ];
        }
    }
})();