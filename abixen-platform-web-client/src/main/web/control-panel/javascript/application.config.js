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
        .module('platformAdminApplication')
        .config(platformAdminApplicationConfig);

    platformAdminApplicationConfig.$inject = [
        '$stateProvider',
        '$translateProvider',
        '$urlRouterProvider',
        'showErrorsConfigProvider',
        '$httpProvider',
        'applicationNavigationItemsProvider'
    ];

    function platformAdminApplicationConfig($stateProvider, $translateProvider, $urlRouterProvider, showErrorsConfigProvider, $httpProvider, applicationNavigationItemsProvider) {

        $httpProvider.interceptors.push('platformHttpInterceptor');

        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        showErrorsConfigProvider.showSuccess(true);
        $urlRouterProvider.otherwise('/users/list');

        $translateProvider.preferredLanguage('ENGLISH');

        $stateProvider
            .state('application', {
                abstract: true,
                controller: 'ApplicationController',
                templateUrl: 'control-panel/html/application.html',
                resolve: {
                    platformSecurityResolver: platformSecurityResolver
                }
            });


        applicationNavigationItemsProvider

            .addSidebarItem({
                title: 'Users',
                state: 'application.users.list',
                orderIndex: 0,
                id: 0,
                iconClass: 'fa fa-user'
            })
            .addSidebarItem({
                title: 'Roles',
                state: 'application.roles.list',
                orderIndex: 1,
                id: 1,
                iconClass: 'fa fa-user-secret'
            })
            .addSidebarItem({
                title: 'Permissions',
                state: 'application.permissions.list',
                orderIndex: 2,
                id: 2,
                iconClass: 'fa fa-lock'
            })
            .addSidebarItem({
                title: 'Pages',
                state: 'application.pages.list',
                orderIndex: 3,
                id: 3,
                iconClass: 'fa fa-file-text-o'
            })
            .addSidebarItem({
                title: 'Instances of modules',
                state: 'application.modules.list',
                orderIndex: 4,
                id: 4,
                iconClass: 'fa fa-list-alt'
            })
            .addSidebarItem({
                title: 'Types of modules',
                state: 'application.moduleTypes.list',
                orderIndex: 5,
                id: 5,
                iconClass: 'fa fa-list-alt'
            })
            .addSidebarItem({
                title: 'Layouts',
                state: 'application.layouts.list',
                orderIndex: 6,
                id: 6,
                iconClass: 'fa fa-columns'
            });

        var nextId = 7;

        for (var i = 0; i < externalAdminSidebarItems.length; i++) {
            externalAdminSidebarItems[i].id = nextId++;
            applicationNavigationItemsProvider.addSidebarItem(externalAdminSidebarItems[i]);
        }

        platformSecurityResolver.$inject = ['platformSecurity'];

        function platformSecurityResolver(platformSecurity) {
            platformSecurity.reloadPlatformUser();
        }
    }
})();