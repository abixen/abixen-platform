var platformModuleModule = angular.module('platformModuleModule', ['moduleControllers', 'moduleServices', 'ui.router']);

platformModuleModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.modules', {
                url: '/modules',
                templateUrl: '/admin/modules/module/html/index.html'
            })
            .state('application.modules.list', {
                url: '/list',
                templateUrl: '/admin/modules/module/html/list.html',
                controller: 'ModuleListController'
            })
            .state('application.modules.add', {
                url: '/add',
                templateUrl: '/admin/modules/module/html/edit.html',
                controller: 'ModuleDetailController'
            })
            .state('application.modules.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/module/html/edit.html',
                controller: 'ModuleDetailController'
            })
            .state('application.modules.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/common/html/permissions.html',
                controller: 'ModulePermissionsController'
            });
    }
]);
