var multiVisualizationModule = angular.module('multiVisualizationModule', ['multiVisualizationControllers', 'multiVisualizationDirectives', 'databaseDataSourceModule', 'databaseConnectionModule', 'fileDataSourceModule', 'platformDataSourceModule']);

multiVisualizationModule.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('application.multiVisualization', {
                url: '/multi-visualization',
                templateUrl: '/admin/modules/abixen/multi-visualization/html/index.html',
                controller: 'MultiVisualizationController'
            })
            .state('application.multiVisualization.modules', {
                url: '/modules',
                templateUrl: '/admin/modules/abixen/multi-visualization/modules/html/index.html'
            });
    }
]);