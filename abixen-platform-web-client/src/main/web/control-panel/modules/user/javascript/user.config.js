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
        .config(platformUserModuleConfig);

    platformUserModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformUserModuleConfig($stateProvider) {

        $stateProvider
            .state('application.users', {
                url: '/users',
                templateUrl: 'control-panel/modules/user/html/index.html'
            })
            .state('application.users.list', {
                url: '/list',
                templateUrl: 'control-panel/modules/user/html/list.html',
                controller: 'UserListController',
                controllerAs: 'userList'
            })
            .state('application.users.add', {
                url: '/add',
                templateUrl: 'control-panel/modules/user/html/edit/details.html',
                controller: 'UserDetailsController',
                controllerAs: 'userDetails'
            })
            .state('application.users.edit', {
                url: '/edit/:id',
                abstract: true,
                templateUrl: 'control-panel/modules/user/html/edit.html'
            })
            .state('application.users.edit.details', {
                url: '',
                templateUrl: 'control-panel/modules/user/html/edit/details.html',
                controller: 'UserDetailsController',
                controllerAs: 'userDetails'
            })
            .state('application.users.edit.avatar', {
                url: '/avatar',
                templateUrl: 'control-panel/modules/user/html/edit/avatar.html',
                controller: 'UserAvatarChangeController',
                controllerAs: 'userAvatarChange',
                params: {
                    isNewUser: false
                }
            })
            .state('application.users.roles', {
                url: '/roles/:id',
                templateUrl: 'control-panel/modules/user/html/roles.html',
                controller: 'UserAssignRolesController'
            });
    }
})();