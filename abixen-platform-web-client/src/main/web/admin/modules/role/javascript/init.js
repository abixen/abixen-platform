var platformRoleModule = angular.module('platformRoleModule', ['roleControllers', 'roleServices', 'ui.router']);

platformRoleModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.roles', {
                url: '/roles',
                templateUrl: '/admin/modules/role/html/index.html'
            })
            .state('application.roles.list', {
                url: '/list',
                templateUrl: '/admin/modules/role/html/list.html',
                controller: 'RoleListController'
            })
            .state('application.roles.add', {
                url: '/add',
                templateUrl: '/admin/modules/role/html/edit.html',
                controller: 'RoleDetailController'
            })
            .state('application.roles.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/role/html/edit.html',
                controller: 'RoleDetailController'
            })
            .state('application.roles.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/role/html/permissions.html',
                controller: 'RoleDefinePermissionsController'
            });
    }
]);