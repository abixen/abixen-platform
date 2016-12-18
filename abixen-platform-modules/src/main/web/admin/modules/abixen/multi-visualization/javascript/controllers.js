var multiVisualizationControllers = angular.module('multiVisualizationControllers', []);

multiVisualizationControllers.controller('MultiVisualizationController', ['$scope', '$log', '$state', function ($scope, $log, $state) {

    $log.log('$state.$current.name: ' + $state.$current.name);

    if ($state.$current.name === 'application.multiVisualization.modules.databaseDataSource.list' ||
        $state.$current.name === 'application.multiVisualization.modules.databaseDataSource.edit' ||
        $state.$current.name === 'application.multiVisualization.modules.databaseDataSource.add') {
        $scope.selectedModule = 'databaseDataSource';
    } else if ($state.$current.name === 'application.multiVisualization.modules.databaseConnection.list' ||
        $state.$current.name === 'application.multiVisualization.modules.databaseConnection.edit' ||
        $state.$current.name === 'application.multiVisualization.modules.databaseConnection.add') {
        $scope.selectedModule = 'databaseConnection';
    } else if ($state.$current.name === 'application.multiVisualization.modules.fileDataSource.list' ||
        $state.$current.name === 'application.multiVisualization.modules.fileDataSource.edit' ||
        $state.$current.name === 'application.multiVisualization.modules.fileDataSource.add') {
        $scope.selectedModule = 'fileDataSource';
    }
    $scope.selectDatabaseDataSources = function () {
        $scope.selectedModule = 'databaseDataSource'
    };
    $scope.selectDatabaseConnections = function () {
        $scope.selectedModule = 'databaseConnection';
    };
    $scope.selectFileDataSources = function () {
        $scope.selectedModule = 'fileDataSource'
    };

}]);