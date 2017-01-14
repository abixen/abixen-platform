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
        .module('platformModuleTypeModule')
        .config(platformModuleTypeModuleConfig);

    platformModuleTypeModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformModuleTypeModuleConfig($stateProvider) {

        $stateProvider
            .state('application.moduleTypes', {
                url: '/module-types',
                templateUrl: '/admin/modules/module-type/html/index.html'
            })
            .state('application.moduleTypes.list', {
                url: '/list',
                templateUrl: '/admin/modules/module-type/html/list.html',
                controller: 'ModuleTypeListController',
                controllerAs: 'moduleTypeList'
            })
            .state('application.moduleTypes.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/common/html/permissions.html',
                controller: 'ModuleTypePermissionsController'
            })
            .state('application.moduleTypes.resources', {
                url: '/:id/resources/list',
                templateUrl: '/admin/modules/common/html/resources.html',
                controller: 'ModuleTypeResourcesListController',
                controllerAs : 'moduleTypeResourceList'
            });
    }
})();