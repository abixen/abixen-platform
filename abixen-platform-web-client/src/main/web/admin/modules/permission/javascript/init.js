var platformPermissionModule = angular.module('platformPermissionModule', ['permissionControllers', 'permissionServices', 'ui.router']);

platformPermissionModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.permissions', {
                url: '/permissions',
                templateUrl: '/admin/modules/permission/html/index.html'
            })
            .state('application.permissions.list', {
                url: '/list',
                templateUrl: '/admin/modules/permission/html/list.html',
                controller: 'PermissionListController'
            });
    }
]);