var databaseConnectionModule = angular.module('databaseConnectionModule', ['databaseConnectionControllers', 'databaseConnectionServices']);

databaseConnectionModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.multiVisualization.modules.databaseConnection', {
                url: '/database-connection',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/database-connection/html/index.html'
            })
            .state('application.multiVisualization.modules.databaseConnection.list', {
                url: '/list',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/database-connection/html/list.html',
                controller: 'DatabaseConnectionListController'
            })
            .state('application.multiVisualization.modules.databaseConnection.add', {
                url: '/add',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/database-connection/html/edit.html',
                controller: 'DatabaseConnectionDetailController'
            })
            .state('application.multiVisualization.modules.databaseConnection.edit', {
                url: '/edit/:id',
                templateUrl: '/admin/businessintelligence/abixen/multi-visualization/modules/database-connection/html/edit.html',
                controller: 'DatabaseConnectionDetailController'
            });
    }
]);