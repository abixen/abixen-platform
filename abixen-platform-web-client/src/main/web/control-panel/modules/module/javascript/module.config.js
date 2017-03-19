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
        .module('platformModuleModule')
        .config(platformModuleModuleConfig);

    platformModuleModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformModuleModuleConfig($stateProvider) {

        $stateProvider
            .state('application.modules', {
                url: '/modules',
                templateUrl: 'control-panel/modules/module/html/index.html'
            })
            .state('application.modules.list', {
                url: '/list',
                templateUrl: 'control-panel/modules/module/html/list.html',
                controller: 'ModuleListController',
                controllerAs: 'moduleList'
            })
            .state('application.modules.add', {
                url: '/add',
                templateUrl: 'control-panel/modules/module/html/edit.html',
                controller: 'ModuleDetailsController',
                controllerAs: 'moduleDetails'
            })
            .state('application.modules.edit', {
                url: '/edit/:id',
                templateUrl: 'control-panel/modules/module/html/edit.html',
                controller: 'ModuleDetailsController',
                controllerAs: 'moduleDetails'
            })
            .state('application.modules.permissions', {
                url: '/permissions/:id',
                templateUrl: 'common/permission/permissions.html',
                controller: 'ModulePermissionsController',
                controllerAs: 'permissions'
            });
    }
})();