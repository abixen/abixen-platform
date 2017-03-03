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

        userList.searchFields = createSearchFields();
        userList.searchFilter = {};

        angular.extend(userList, new AbstractListGridController(User,
            {
                getTableColumns: getTableColumns,
                filter: userList.searchFilter
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
                {field: 'username', name: 'Username', pinnedLeft: true, width: 200},
                {field: 'firstName', name: 'First Name', width: 200},
                {field: 'lastName', name: 'Last Name', width: 200},
                {field: 'state', name: 'State', width: 100},
                {field: 'registrationIp', name: 'Registration Ip', width: 200}
            ].concat(getAuditingTableColumns());
        }

        function createSearchFields() {
            return [
                {
                    name: 'username',
                    label: 'module.user.username.label',
                    type: 'input-text'
                },
                {
                    name: 'firstName',
                    label: 'module.user.firstName.label',
                    type: 'input-text'
                },
                {
                    name: 'lastName',
                    label: 'module.user.lastName.label',
                    type: 'input-text'
                },
                {
                    name: 'gender',
                    label: 'module.user.gender.label',
                    type: 'input-drop-down',
                    options: [{key: 'MALE'}, {key: 'FEMALE'}],
                    showEmptyValue: true,
                    emptyValueLabel: 'module.user.gender.select',
                    keyAsValue: 'true'
                },
                {
                    name: 'selectedLanguage',
                    label: 'module.user.language.label',
                    type: 'input-drop-down',
                    options: [
                        {key: 'ENGLISH'},
                        {key: 'POLISH'},
                        {key: 'RUSSIAN'},
                        {key: 'SPANISH'},
                        {key: 'UKRAINIAN'}
                    ],
                    showEmptyValue: true,
                    emptyValueLabel: 'module.user.language.select',
                    keyAsValue: 'true'
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