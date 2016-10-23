var platformUserModule = angular.module('platformUserModule', ['userControllers', 'userServices', 'ui.router']);

platformUserModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.users', {
                url: '/users',
                templateUrl: '/admin/modules/user/html/index.html'
            })
            .state('application.users.list', {
                url: '/list',
                templateUrl: '/admin/modules/user/html/list.html',
                controller: 'UserListController'
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
]);