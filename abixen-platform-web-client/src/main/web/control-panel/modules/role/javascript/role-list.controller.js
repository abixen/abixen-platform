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
        .module('platformRoleModule')
        .controller('RoleListController', RoleListController);

    RoleListController.$inject = [
        '$log',
        'Role',
        'applicationNavigationItems',
        '$state'
    ];

    function RoleListController($log, Role, applicationNavigationItems, $state) {
        $log.log('RoleListController');

        var roleList = this;

        roleList.searchFields = createSearchFields();
        roleList.searchFilter = {};

        angular.extend(roleList, new AbstractListGridController(Role,
            {
                getTableColumns: getTableColumns,
                filter: roleList.searchFilter
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', name: 'Id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'name', name: 'Name', pinnedLeft: true, width: 200},
                {field: 'roleType', name: 'Role Type', pinnedLeft: true, width: 200}
            ].concat(getAuditingTableColumns());
        }

        function updateNavigation() {
            var newRoleButton = {
                id: 1,
                styleClass: 'btn add-new-object-button',
                faIcon: 'fa fa-plus',
                title: 'New Role',
                onClick: function () {
                    $state.go('application.roles.add');
                },
                visible: true,
                disabled: false
            };

            applicationNavigationItems.setTopbarItem(newRoleButton);
        }

        function createSearchFields() {
            return [
                {
                    name: 'name',
                    label: 'module.role.name.label',
                    type: 'input-text'
                },
                {
                    name: 'roleType',
                    label: 'module.role.type.label',
                    type: 'input-drop-down',
                    options: [
                        {key: 'ROLE_ADMIN'},
                        {key: 'ROLE_USER'}
                    ],
                    showEmptyValue: true,
                    emptyValueLabel: 'module.role.type.select',
                    keyAsValue: 'true'
                }
            ];
        }
    }
})();