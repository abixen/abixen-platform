var fileDataSourceModule = angular.module('fileDataSourceModule', ['fileDataSourceControllers', 'fileDataSourceServices']);

fileDataSourceModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.fileDataSource', {
                url: '/data-source',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/file-data-source/html/index.html'
            })
            .state('application.multiVisualization.modules.fileDataSource.list', {
                url: '/list',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/file-data-source/html/list.html',
                controller: 'FileDataSourceListController'
            })
            .state('application.multiVisualization.modules.fileDataSource.add', {
                url: '/add',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/file-data-source/html/edit.html',
                controller: 'FileDataSourceDetailController'
            })
            .state('application.multiVisualization.modules.fileDataSource.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/file-data-source/html/edit.html',
                controller: 'FileDataSourceDetailController'
            });
    }
]);