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
        'responseHandler'
    ];

    function FileDataSourceDetailController($scope, $http, $state, $stateParams, $log, FileDataSource, responseHandler) {
        $log.log('FileDataSourceDetailController');
        var fileDataSourceDetails = this;

        new AbstractDetailsController(fileDataSourceDetails, FileDataSource, responseHandler, $scope,
            {
                entityId: $stateParams.id,
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        fileDataSourceDetails.gridData = [];
        fileDataSourceDetails.fileColumns = [];

        $scope.$watch('fileDataSourceDetails.fileColumns', function () {
            $scope.$broadcast('FileColumnUpdated', fileDataSourceDetails.fileColumns);
        }, true);

        $scope.$watch('fileDataSourceDetails.gridData', function () {
            if (fileDataSourceDetails.gridData !== undefined && fileDataSourceDetails.gridData !== [] && fileDataSourceDetails.gridData.length > 0) {
                $scope.$broadcast('GridDataUpdated', fileDataSourceDetails.gridData);
                fileDataSourceDetails.fileColumns = [];
                Object.keys(fileDataSourceDetails.gridData[0]).forEach(function (column) {
                    if (column !== undefined && column !== null && column !== '' && column !== '$$hashKey') {
                        fileDataSourceDetails.fileColumns.push({name: column, selected: false});
                    }
                });
            }
        }, true);

        function onSuccessSaveForm() {
            $state.go('application.multiVisualization.modules.fileDataSource.list');
        }

        function getValidators() {
            var validators = [];

            validators['name'] =
                [
                    new NotNull(),
                    new Length(6, 40)
                ];

            validators['description'] =
                [
                    new Length(0, 40)
                ];
            return validators;
        }
    }
})();