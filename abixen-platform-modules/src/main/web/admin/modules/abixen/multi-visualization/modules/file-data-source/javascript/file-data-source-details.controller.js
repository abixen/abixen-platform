(function () {

    'use strict';

    angular
        .module('platformFileDataSourceModule')
        .controller('FileDataSourceDetailController', FileDataSourceDetailController);

    FileDataSourceDetailController.$inject = [
        '$scope',
        '$http',
        '$state',
        '$stateParams',
        '$log',
        'FileDataSource',
        '$parse'
    ];

    function FileDataSourceDetailController($scope, $http, $state, $stateParams, $log, FileDataSource, $parse) {
        $log.log('FileDataSourceDetailController');

        angular.extend(this, new AbstractCrudDetailController($scope, $http, $state, $stateParams, $log, FileDataSource, $parse, 'application.multiVisualization.modules.fileDataSource'));

        $scope.gridData = [];
        $scope.fileColumns = [];

        $scope.$watch('gridData', function () {
            $log.debug('$scope.gridData.length in FileDataSourceDetailController: ', $scope.gridData.length);
            if ($scope.gridData !== undefined && $scope.gridData !== [] && $scope.gridData.length > 0) {
                $scope.fileColumns = [];
                Object.keys($scope.gridData[0]).forEach(function (column) {
                    if (column !== undefined && column !== null && column !== '' && column !== '$$hashKey') {
                        $scope.fileColumns.push({name: column, selected: false});
                    }
                });
            }
        });
        $scope.get($stateParams.id);
    }
})();