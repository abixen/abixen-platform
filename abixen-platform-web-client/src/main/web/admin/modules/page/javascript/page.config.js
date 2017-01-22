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
        .module('platformPageModule')
        .config(platformPageModuleConfig);

    platformPageModuleConfig.$inject = [
        '$stateProvider'
    ];

    function platformPageModuleConfig($stateProvider) {

        $stateProvider
            .state('application.pages', {
                url: '/pages',
                templateUrl: '/admin/modules/page/html/index.html'
            })
            .state('application.pages.list', {
                url: '/list',
                templateUrl: '/admin/modules/page/html/list.html',
                controller: 'PageListController',
                controllerAs: 'pageList'
            })
            .state('application.pages.add', {
                url: '/add',
                templateUrl: '/admin/modules/page/html/edit.html',
                controller: 'PageDetailController',
                controllerAs: 'pageDetails'
            })
            .state('application.pages.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/page/html/edit.html',
                controller: 'PageDetailController'
            })
            .state('application.pages.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/common/html/permissions.html',
                controller: 'PagePermissionsController'
            });
    }
})();