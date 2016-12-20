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

        $scope.get($stateParams.id);
    }
})();