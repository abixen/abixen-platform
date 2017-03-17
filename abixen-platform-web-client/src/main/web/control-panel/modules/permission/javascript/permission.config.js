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
        .config(platformPermissionModuleConfig);

    platformPermissionModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformPermissionModuleConfig($stateProvider) {

        $stateProvider
            .state('application.permissions', {
                url: '/permissions',
                templateUrl: 'control-panel/modules/permission/html/index.html'
            })
            .state('application.permissions.list', {
                url: '/list',
                templateUrl: 'control-panel/modules/permission/html/list.html',
                controller: 'PermissionListController',
                controllerAs: 'permissionList'
            });
    }
})();