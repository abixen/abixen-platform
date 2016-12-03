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
                templateUrl: '/admin/modules/user/html/index.html'
            })
            .state('application.users.list', {
                url: '/list',
                templateUrl: '/admin/modules/user/html/list.html',
                controller: 'UserListController',
                controllerAs: 'userList'
            })
            .state('application.users.add', {
                url: '/add',
                templateUrl: '/admin/modules/user/html/edit.html',
                controller: 'UserDetailController'
            })
            .state('application.users.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/user/html/edit.html',
                controller: 'UserDetailController'
            })
            .state('application.users.roles', {
                url: '/roles/:id',
                templateUrl: '/admin/modules/user/html/roles.html',
                controller: 'UserAssignRolesController'
            });
    }
})();