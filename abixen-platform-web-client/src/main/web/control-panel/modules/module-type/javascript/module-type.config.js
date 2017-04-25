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
                templateUrl: 'control-panel/modules/module-type/html/index.html'
            })
            .state('application.moduleTypes.list', {
                url: '/list',
                templateUrl: 'control-panel/modules/module-type/html/list.html',
                controller: 'ModuleTypeListController',
                controllerAs: 'moduleTypeList'
            })
            .state('application.moduleTypes.permissions', {
                url: '/permissions/:id',
                templateUrl: 'common/permission/permissions.html',
                controller: 'ModuleTypePermissionsController',
                controllerAs: 'permissions'
            })
            .state('application.moduleTypes.resources', {
                url: '/:id/resources/list',
                templateUrl: 'control-panel/modules/common/html/resources.html',
                controller: 'ModuleTypeResourcesListController',
                controllerAs : 'moduleTypeResourceList'
            });
    }
})();