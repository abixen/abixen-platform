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
        .module('platformLayoutModule')
        .config(platformLayoutModuleConfig);

    platformLayoutModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformLayoutModuleConfig($stateProvider) {

        $stateProvider
            .state('application.layouts', {
                url: '/layouts',
                templateUrl: '/control-panel/modules/layout/html/index.html'
            })
            .state('application.layouts.list', {
                url: '/list',
                templateUrl: '/control-panel/modules/layout/html/list.html',
                controller: 'LayoutListController',
                controllerAs: 'layoutList'
            })
            .state('application.layouts.add', {
                url: '/add',
                templateUrl: '/control-panel/modules/layout/html/edit/details.html',
                controller: 'LayoutDetailsController',
                controllerAs: 'layoutDetails'
            })
            .state('application.layouts.edit', {
                url: '/edit/:id',
                templateUrl: '/control-panel/modules/layout/html/edit.html',
                abstract:true
            })
            .state('application.layouts.edit.details', {
                url: '',
                templateUrl: '/control-panel/modules/layout/html/edit/details.html',
                controller: 'LayoutDetailsController',
                controllerAs: 'layoutDetails'
            })
            .state('application.layouts.edit.icon', {
                url: '/icon',
                templateUrl: '/control-panel/modules/layout/html/edit/icon.html',
                controller: 'LayoutIconChangeController',
                controllerAs: 'layoutIconChange',
                params: {
                    isNew: false
                }
            })
            .state('application.layouts.permissions', {
                url: '/permissions/:id',
                templateUrl: '/common/permission/permissions.html',
                controller: 'LayoutPermissionsController',
                controllerAs: 'permissions'
            });
    }
})();