var platformLayoutModule = angular.module('platformLayoutModule', ['layoutControllers', 'layoutServices', 'ui.router']);

platformLayoutModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.layouts', {
                url: '/layouts',
                templateUrl: '/admin/modules/layout/html/index.html'
            })
            .state('application.layouts.list', {
                url: '/list',
                templateUrl: '/admin/modules/layout/html/list.html',
                controller: 'LayoutListController'
            })
            .state('application.layouts.add', {
                url: '/add',
                templateUrl: '/admin/modules/layout/html/edit.html',
                controller: 'LayoutDetailController'
            })
            .state('application.layouts.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/layout/html/edit.html',
                controller: 'LayoutDetailController'
            })
            .state('application.layouts.permissions', {
                url: '/permissions/:id',
                templateUrl: '/admin/modules/common/html/permissions.html',
                controller: 'LayoutPermissionsController'
            });
    }
]);