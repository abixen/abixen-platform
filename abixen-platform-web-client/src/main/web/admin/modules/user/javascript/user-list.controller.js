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
        .module('platformUserModule')
        .controller('UserListController', UserListController);

    UserListController.$inject = [
        '$log',
        'User',
        'applicationNavigationItems',
        '$state'
    ];

    function UserListController($log, User, applicationNavigationItems, $state) {
        $log.log('UserListController');

        var userList = this;

        angular.extend(userList, new AbstractListGridController(User,
            {
                getTableColumns: getTableColumns
            }
        ));

        updateNavigation();


        function getTableColumns() {
            return [
                {field: 'id', pinnedLeft: true, enableColumnResizing: false, enableFiltering: false, width: 50},
                {field: 'username', pinnedLeft: true, width: 200},
                {field: 'firstName', pinnedLeft: true, width: 200},
                {field: 'lastName', pinnedLeft: true, width: 200},
                {field: 'state', pinnedLeft: true, width: 100},
                {field: 'registrationIp', width: 200},
                {field: 'createdBy.username', name: 'Created By', width: 200},
                {
                    field: 'createdDate',
                    width: 200,
                    cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
                },
                {field: 'lastModifiedBy.username', name: 'Last Modified By', width: 200},
                {
                    field: 'lastModifiedDate',
                    width: 200,
                    cellFilter: "date:'" + platformParameters.formats.DATE_TIME_FORMAT + "'"
                }
            ];
        }

        function updateNavigation() {
            var newUserButton = {
                id: 1,
                styleClass: 'btn add-new-object-button',
                faIcon: 'fa fa-plus',
                title: 'New User',
                onClick: function () {
                    $state.go('application.users.add');
                },
                visible: true,
                disabled: false
            };

            applicationNavigationItems.setTopbarItem(newUserButton);
        }
    }
})();