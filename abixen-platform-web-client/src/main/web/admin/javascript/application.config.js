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
        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('application', {
                abstract: true,
                controller: 'ApplicationController',
                templateUrl: '/admin/html/application.html'
            })
            .state('application.dashboard', {
                url: '/',
                templateUrl: '/admin/html/dashboard.html'
            })
            .state('application.search', {
                url: '/search?query',
                controller: 'SearchController',
                templateUrl: '/admin/html/search.html'
            });


        applicationNavigationItemsProvider
            .addSidebarItem({
                title: 'Dashboard',
                state: 'application.dashboard',
                orderIndex: 0,
                id: 0,
                iconClass: 'fa fa-th'
            })
            .addSidebarItem({
                title: 'Users',
                state: 'application.users.list',
                orderIndex: 1,
                id: 1,
                iconClass: 'fa fa-user'
            })
            .addSidebarItem({
                title: 'Roles',
                state: 'application.roles.list',
                orderIndex: 2,
                id: 2,
                iconClass: 'fa fa-user-secret'
            })
            .addSidebarItem({
                title: 'Permissions',
                state: 'application.permissions.list',
                orderIndex: 3,
                id: 3,
                iconClass: 'fa fa-lock'
            })
            .addSidebarItem({
                title: 'Pages',
                state: 'application.pages.list',
                orderIndex: 4,
                id: 4,
                iconClass: 'fa fa-file-text-o'
            })
            .addSidebarItem({
                title: 'Modules',
                state: 'application.modules.list',
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
            })
            .addSidebarItem({
                title: 'Data Sources',
                state: 'application.multiVisualization.modules.databaseDataSource.list',
                orderIndex: 7,
                id: 7,
                iconClass: 'fa fa-database'
            });
    }
})();