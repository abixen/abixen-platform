var platformPageModule = angular.module('platformPageModule', ['pageControllers', 'pageServices', 'ui.router']);

platformPageModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.pages', {
                url: '/pages',
                templateUrl: '/admin/modules/page/html/index.html'
            })
            .state('application.pages.list', {
                url: '/list',
                templateUrl: '/admin/modules/page/html/list.html',
                controller: 'PageListController'
            })
            .state('application.pages.add', {
                url: '/add',
                templateUrl: '/admin/modules/page/html/edit.html',
                controller: 'PageDetailController'
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
]);
