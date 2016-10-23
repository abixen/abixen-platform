var databaseDataSourceModule = angular.module('databaseDataSourceModule', ['databaseDataSourceControllers', 'databaseDataSourceServices', 'databaseConnectionModule']);

databaseDataSourceModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.databaseDataSource', {
                url: '/data-source',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-data-source/html/index.html'
            })
            .state('application.multiVisualization.modules.databaseDataSource.list', {
                url: '/list',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-data-source/html/list.html',
                controller: 'DatabaseDataSourceListController'
            })
            .state('application.multiVisualization.modules.databaseDataSource.add', {
                url: '/add',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-data-source/html/edit.html',
                controller: 'DatabaseDataSourceDetailController'
            })
            .state('application.multiVisualization.modules.databaseDataSource.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/database-data-source/html/edit.html',
                controller: 'DatabaseDataSourceDetailController'
            });
    }
]);