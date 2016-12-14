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
                templateUrl: '/admin/modules/layout/html/index.html'
            })
            .state('application.layouts.list', {
                url: '/list',
                templateUrl: '/admin/modules/layout/html/list.html',
                controller: 'LayoutListController',
                controllerAs: 'layoutList'
            })
            .state('application.layouts.add', {
                url: '/add',
                templateUrl: '/admin/modules/layout/html/edit.html',
                controller: 'LayoutDetailsController'
            })
            .state('application.layouts.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/layout/html/edit.html',
                controller: 'LayoutDetailsController'
            })
            .state('application.layouts.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/common/html/permissions.html',
                controller: 'LayoutPermissionsController'
            });
    }
})();